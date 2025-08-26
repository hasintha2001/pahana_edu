package com.pahanaedu.dao;

import com.pahanaedu.model.Bill;
import com.pahanaedu.model.Bill.BillItem;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class BillDAO {
    private Connection connection;
    private CustomerDAO customerDAO;
    private ItemDAO itemDAO;

    public BillDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.customerDAO = new CustomerDAO();
        this.itemDAO = new ItemDAO();
    }

    public String generateBillNumber() {
        String query = "SELECT MAX(bill_number) as max_bill FROM bills";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String maxBill = rs.getString("max_bill");
                if (maxBill != null) {
                    int num = Integer.parseInt(maxBill.substring(4)) + 1;
                    return String.format("BILL%05d", num);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BILL00001";
    }

    public boolean createBill(Bill bill) {
        String billQuery = "INSERT INTO bills (bill_number, account_number, bill_date, " +
                          "total_amount, discount, net_amount, payment_status, created_by) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        String itemQuery = "INSERT INTO bill_items (bill_id, item_id, quantity, " +
                          "unit_price, total_price) VALUES (?, ?, ?, ?, ?)";
        
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement billStmt = connection.prepareStatement(billQuery, Statement.RETURN_GENERATED_KEYS);
            billStmt.setString(1, bill.getBillNumber());
            billStmt.setString(2, bill.getAccountNumber());
            billStmt.setDate(3, bill.getBillDate());
            billStmt.setBigDecimal(4, bill.getTotalAmount());
            billStmt.setBigDecimal(5, bill.getDiscount());
            billStmt.setBigDecimal(6, bill.getNetAmount());
            billStmt.setString(7, bill.getPaymentStatus());
            billStmt.setInt(8, bill.getCreatedBy());
            
            int affectedRows = billStmt.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }
            
            ResultSet generatedKeys = billStmt.getGeneratedKeys();
            int billId = 0;
            if (generatedKeys.next()) {
                billId = generatedKeys.getInt(1);
            }
            
            PreparedStatement itemStmt = connection.prepareStatement(itemQuery);
            for (BillItem item : bill.getBillItems()) {
                itemStmt.setInt(1, billId);
                itemStmt.setInt(2, item.getItemId());
                itemStmt.setInt(3, item.getQuantity());
                itemStmt.setBigDecimal(4, item.getUnitPrice());
                itemStmt.setBigDecimal(5, item.getTotalPrice());
                itemStmt.addBatch();
                
                itemDAO.updateStock(item.getItemId(), item.getQuantity());
            }
            itemStmt.executeBatch();
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Bill getBillByNumber(String billNumber) {
        Bill bill = null;
        String billQuery = "SELECT * FROM bills WHERE bill_number = ?";
        String itemsQuery = "SELECT bi.*, i.* FROM bill_items bi " +
                           "JOIN items i ON bi.item_id = i.item_id " +
                           "WHERE bi.bill_id = ?";
        
        try (PreparedStatement billStmt = connection.prepareStatement(billQuery)) {
            billStmt.setString(1, billNumber);
            ResultSet billRs = billStmt.executeQuery();
            
            if (billRs.next()) {
                bill = extractBillFromResultSet(billRs);
                
                PreparedStatement itemsStmt = connection.prepareStatement(itemsQuery);
                itemsStmt.setInt(1, bill.getBillId());
                ResultSet itemsRs = itemsStmt.executeQuery();
                
                while (itemsRs.next()) {
                    BillItem billItem = new BillItem();
                    billItem.setBillItemId(itemsRs.getInt("bill_item_id"));
                    billItem.setBillId(itemsRs.getInt("bill_id"));
                    billItem.setItemId(itemsRs.getInt("item_id"));
                    billItem.setQuantity(itemsRs.getInt("quantity"));
                    billItem.setUnitPrice(itemsRs.getBigDecimal("unit_price"));
                    billItem.setTotalPrice(itemsRs.getBigDecimal("total_price"));
                    
                    Item item = new Item();
                    item.setItemId(itemsRs.getInt("item_id"));
                    item.setItemCode(itemsRs.getString("item_code"));
                    item.setItemName(itemsRs.getString("item_name"));
                    item.setCategory(itemsRs.getString("category"));
                    item.setAuthor(itemsRs.getString("author"));
                    billItem.setItem(item);
                    
                    bill.addBillItem(billItem);
                }
                
                if (bill.getAccountNumber() != null) {
                    bill.setCustomer(customerDAO.getCustomerByAccountNumber(bill.getAccountNumber()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT b.*, c.name as customer_name FROM bills b " +
                      "LEFT JOIN customers c ON b.account_number = c.account_number " +
                      "ORDER BY b.created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Bill bill = extractBillFromResultSet(rs);
                
                Customer customer = new Customer();
                customer.setAccountNumber(rs.getString("account_number"));
                customer.setName(rs.getString("customer_name"));
                bill.setCustomer(customer);
                
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public List<Bill> getBillsByDateRange(Date startDate, Date endDate) {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM bills WHERE bill_date BETWEEN ? AND ? ORDER BY bill_date DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, startDate);
            pstmt.setDate(2, endDate);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                bills.add(extractBillFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public BigDecimal getTotalSales(Date date) {
        String query = "SELECT SUM(net_amount) as total FROM bills WHERE bill_date = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setDate(1, date);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getBigDecimal("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal getTodaysSales() {
        String sql = "SELECT SUM(net_amount) FROM bills WHERE DATE(bill_date) = CURDATE()";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    private Bill extractBillFromResultSet(ResultSet rs) throws SQLException {
        Bill bill = new Bill();
        bill.setBillId(rs.getInt("bill_id"));
        bill.setBillNumber(rs.getString("bill_number"));
        bill.setAccountNumber(rs.getString("account_number"));
        bill.setBillDate(rs.getDate("bill_date"));
        bill.setTotalAmount(rs.getBigDecimal("total_amount"));
        bill.setDiscount(rs.getBigDecimal("discount"));
        bill.setNetAmount(rs.getBigDecimal("net_amount"));
        bill.setPaymentStatus(rs.getString("payment_status"));
        bill.setCreatedBy(rs.getInt("created_by"));
        bill.setCreatedAt(rs.getTimestamp("created_at"));
        return bill;
    }
}