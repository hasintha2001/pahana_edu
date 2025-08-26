// src/com/pahanaedu/service/BillingService.java
package com.pahanaedu.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.pahanaedu.dao.BillDAO;
import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Bill;
import com.pahanaedu.model.Bill.BillItem;
import com.pahanaedu.model.Item;

public class BillingService {
    private BillDAO billDAO;
    private ItemDAO itemDAO;

    public BillingService() {
        this.billDAO = new BillDAO();
        this.itemDAO = new ItemDAO();
    }

    public String generateBillNumber() {
        return billDAO.generateBillNumber();
    }

    public boolean createBill(Bill bill) {
        return billDAO.createBill(bill);
    }

    public Bill getBillByNumber(String billNumber) {
        return billDAO.getBillByNumber(billNumber);
    }

    public List<Bill> getAllBills() {
        return billDAO.getAllBills();
    }

    public List<Bill> getBillsByDateRange(Date startDate, Date endDate) {
        return billDAO.getBillsByDateRange(startDate, endDate);
    }

    public BigDecimal getTotalSales(Date date) {
        return billDAO.getTotalSales(date);
    }

    public boolean validateBillItems(List<BillItem> items) {
        for (BillItem billItem : items) {
            Item item = itemDAO.getItemById(billItem.getItemId());
            if (item == null) {
                return false;
            }
            if (item.getQuantity() < billItem.getQuantity()) {
                return false; // Not enough stock
            }
        }
        return true;
    }
}