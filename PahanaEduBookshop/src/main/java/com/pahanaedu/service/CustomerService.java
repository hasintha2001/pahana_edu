package com.pahanaedu.service;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    public boolean addCustomer(Customer customer) {
        // Validate customer data
        if (!validateCustomer(customer)) {
            return false;
        }
        
        // Generate account number if not provided
        if (customer.getAccountNumber() == null || customer.getAccountNumber().isEmpty()) {
            customer.setAccountNumber(customerDAO.generateAccountNumber());
        }
        
        return customerDAO.addCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        if (!validateCustomer(customer)) {
            return false;
        }
        return customerDAO.updateCustomer(customer);
    }

    public boolean deleteCustomer(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return false;
        }
        return customerDAO.deleteCustomer(accountNumber);
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.isEmpty()) {
            return null;
        }
        return customerDAO.getCustomerByAccountNumber(accountNumber);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public List<Customer> searchCustomers(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllCustomers();
        }
        return customerDAO.searchCustomers(searchTerm);
    }

    private boolean validateCustomer(Customer customer) {
        if (customer == null) {
            return false;
        }
        
        // Validate required fields
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            return false;
        }
        
        // Validate phone number format (Sri Lankan)
        if (customer.getTelephone() != null && !customer.getTelephone().isEmpty()) {
            if (!customer.getTelephone().matches("^0[0-9]{9}$")) {
                return false;
            }
        }
        
        // Validate email format
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            if (!customer.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return false;
            }
        }
        
        return true;
    }

    public String generateAccountNumber() {
        return customerDAO.generateAccountNumber();
    }
}