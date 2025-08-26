package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection connection;

    public CustomerDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean addCustomer(Customer customer) {
        String query = "INSERT INTO customers (account_number, name, address, telephone, email, registration_date) " +
                      "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, customer.getAccountNumber());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getAddress());
            pstmt.setString(4, customer.getTelephone());
            pstmt.setString(5, customer.getEmail());
            pstmt.setDate(6, new Date(System.currentTimeMillis()));
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        String query = "UPDATE customers SET name=?, address=?, telephone=?, email=? " +
                      "WHERE account_number=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, customer.getName());
            pstmt.setString(2, customer.getAddress());
            pstmt.setString(3, customer.getTelephone());
            pstmt.setString(4, customer.getEmail());
            pstmt.setString(5, customer.getAccountNumber());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(String accountNumber) {
        String query = "UPDATE customers SET status='INACTIVE' WHERE account_number=?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, accountNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        Customer customer = null;
        String query = "SELECT * FROM customers WHERE account_number = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                customer = extractCustomerFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE status = 'ACTIVE' ORDER BY name";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setAccountNumber(rs.getString("account_number"));
        customer.setName(rs.getString("name"));
        customer.setAddress(rs.getString("address"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setEmail(rs.getString("email"));
        customer.setRegistrationDate(rs.getDate("registration_date"));
        customer.setStatus(rs.getString("status"));
        customer.setCreatedAt(rs.getTimestamp("created_at"));
        customer.setUpdatedAt(rs.getTimestamp("updated_at"));
        return customer;
    }

    public String generateAccountNumber() {
        String query = "SELECT MAX(account_number) as max_acc FROM customers";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                String maxAcc = rs.getString("max_acc");
                if (maxAcc != null) {
                    int num = Integer.parseInt(maxAcc.substring(3)) + 1;
                    return String.format("CUS%03d", num);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CUS001";
    }

    public List<Customer> searchCustomers(String searchTerm) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE (name LIKE ? OR account_number LIKE ? " +
                       "OR telephone LIKE ?) AND status = 'ACTIVE'";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public int getTotalCustomers() {
        String sql = "SELECT COUNT(*) FROM customers WHERE status = 'ACTIVE'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}