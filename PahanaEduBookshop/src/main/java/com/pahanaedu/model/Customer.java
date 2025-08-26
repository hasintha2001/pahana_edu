package com.pahanaedu.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Customer {
    private String accountNumber;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private Date registrationDate;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // Constructors
    public Customer() {}

    public Customer(String accountNumber, String name, String address, 
                   String telephone, String email) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.registrationDate = new Date(System.currentTimeMillis());
        this.status = "ACTIVE";
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Date getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(Date registrationDate) { this.registrationDate = registrationDate; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}