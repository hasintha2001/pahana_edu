package com.pahanaedu.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Item {
    private int itemId;
    private String itemCode;
    private String itemName;
    private String category;
    private String author;
    private String publisher;
    private BigDecimal price;
    private int quantity;
    private int reorderLevel;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Item() {}

    public Item(String itemCode, String itemName, String category, 
                String author, String publisher, BigDecimal price, int quantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.category = category;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.quantity = quantity;
        this.reorderLevel = 10;
        this.status = "AVAILABLE";
    }

    // Getters and Setters
    public int getItemId() { return itemId; }
    public void setItemId(int itemId) { this.itemId = itemId; }
    
    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}