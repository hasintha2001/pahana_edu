<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.Customer" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Customer customer = (Customer) request.getAttribute("customer");
    String accountNumber = (String) request.getAttribute("accountNumber");
    boolean isEdit = (customer != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Edit" : "Add" %> Customer - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            max-width: 600px;
            margin: 30px auto;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-actions {
            margin-top: 30px;
            text-align: center;
        }
        .form-actions .btn {
            margin: 0 10px;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <h1><%= isEdit ? "Edit Customer" : "Add New Customer" %></h1>
            
            <div class="form-container">
                <form action="customers" method="post" onsubmit="return validateCustomerForm()">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    
                    <div class="form-group">
                        <label for="accountNumber">Account Number:</label>
                        <input type="text" id="accountNumber" name="accountNumber" 
                               value="<%= isEdit ? customer.getAccountNumber() : accountNumber %>" 
                               class="form-control" readonly>
                    </div>
                    
                    <div class="form-group">
                        <label for="name">Customer Name: *</label>
                        <input type="text" id="name" name="name" 
                               value="<%= isEdit ? customer.getName() : "" %>" 
                               class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <textarea id="address" name="address" class="form-control" rows="3"><%= isEdit ? customer.getAddress() : "" %></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="telephone">Telephone:</label>
                        <input type="tel" id="telephone" name="telephone" 
                               value="<%= isEdit ? customer.getTelephone() : "" %>" 
                               class="form-control" pattern="0[0-9]{9}" 
                               placeholder="0771234567">
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" 
                               value="<%= isEdit ? customer.getEmail() : "" %>" 
                               class="form-control">
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <%= isEdit ? "Update Customer" : "Add Customer" %>
                        </button>
                        <a href="customers" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script src="js/validation.js"></script>
    
</body>
</html>