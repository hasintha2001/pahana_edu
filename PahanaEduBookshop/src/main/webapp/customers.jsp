<!-- webapp/customers.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.Customer" %>
<%@ page import="java.lang.SuppressWarnings" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
@SuppressWarnings("unchecked")
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Management - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <%@ include file="includes/navbar.jsp" %>
        
        <div class="content">
            <div class="page-header">
                <h1>Customer Management</h1>
                <a href="customers?action=add" class="btn btn-success">Add New Customer</a>
            </div>
            
            <% if(request.getParameter("success") != null) { %>
                <div class="alert alert-success">
                    <%= request.getParameter("success") %>
                </div>
            <% } %>
            
            <% if(request.getParameter("error") != null) { %>
                <div class="alert alert-error">
                    <%= request.getParameter("error") %>
                </div>
            <% } %>
            
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Account Number</th>
                            <th>Name</th>
                            <th>Address</th>
                            <th>Telephone</th>
                            <th>Email</th>
                            <th>Registration Date</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(customers != null && !customers.isEmpty()) {
                            for(Customer customer : customers) { %>
                        <tr>
                            <td><%= customer.getAccountNumber() %></td>
                            <td><%= customer.getName() %></td>
                            <td><%= customer.getAddress() %></td>
                            <td><%= customer.getTelephone() %></td>
                            <td><%= customer.getEmail() %></td>
                            <td><%= customer.getRegistrationDate() %></td>
                            <td>
                                <a href="customers?action=edit&id=<%= customer.getAccountNumber() %>" 
                                   class="btn btn-sm btn-primary">Edit</a>
                                <a href="customers?action=delete&id=<%= customer.getAccountNumber() %>" 
                                   class="btn btn-sm btn-danger" 
                                   onclick="return confirm('Are you sure you want to delete this customer?')">Delete</a>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr>
                            <td colspan="7" class="text-center">No customers found</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>