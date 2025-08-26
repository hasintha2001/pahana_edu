package com.pahanaedu.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Bill {
    private int billId;
    private String billNumber;
    private String accountNumber;
    private Date billDate;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal netAmount;
    private String paymentStatus;
    private int createdBy;
    private Timestamp createdAt;
    private List<BillItem> billItems;
    private Customer customer;

    // Inner class for Bill Items
    public static class BillItem {
        private int billItemId;
        private int billId;
        private int itemId;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
        private Item item;

        // Constructors
        public BillItem() {}

        public BillItem(int itemId, int quantity, BigDecimal unitPrice) {
            this.itemId = itemId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }

        // Getters and Setters
        public int getBillItemId() { return billItemId; }
        public void setBillItemId(int billItemId) { this.billItemId = billItemId; }
        
        public int getBillId() { return billId; }
        public void setBillId(int billId) { this.billId = billId; }
        
        public int getItemId() { return itemId; }
        public void setItemId(int itemId) { this.itemId = itemId; }
        
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        
        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
        
        public Item getItem() { return item; }
        public void setItem(Item item) { this.item = item; }
    }

    // Constructors
    public Bill() {
        this.billItems = new ArrayList<>();
        this.discount = BigDecimal.ZERO;
        this.paymentStatus = "PENDING";
    }

    // Methods
    public void addBillItem(BillItem item) {
        this.billItems.add(item);
        calculateTotal();
    }

    public void calculateTotal() {
        this.totalAmount = BigDecimal.ZERO;
        for (BillItem item : billItems) {
            this.totalAmount = this.totalAmount.add(item.getTotalPrice());
        }
        this.netAmount = this.totalAmount.subtract(this.discount);
    }

    // Getters and Setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    
    public String getBillNumber() { return billNumber; }
    public void setBillNumber(String billNumber) { this.billNumber = billNumber; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public Date getBillDate() { return billDate; }
    public void setBillDate(Date billDate) { this.billDate = billDate; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { 
        this.discount = discount;
        calculateTotal();
    }
    
    public BigDecimal getNetAmount() { return netAmount; }
    public void setNetAmount(BigDecimal netAmount) { this.netAmount = netAmount; }
    
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public List<BillItem> getBillItems() { return billItems; }
    public void setBillItems(List<BillItem> billItems) { this.billItems = billItems; }
    
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
}