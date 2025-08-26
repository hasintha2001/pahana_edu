<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.Item" %>
<%@ page import="java.lang.SuppressWarnings" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
@SuppressWarnings("unchecked")
    List<Item> items = (List<Item>) request.getAttribute("items");
    boolean lowStockView = request.getAttribute("lowStockView") != null;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Item Management - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .low-stock { background-color: #fff3cd; }
        .status-badge {
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }
        .in-stock { background-color: #d4edda; color: #155724; }
        .low-stock { background-color: #fff3cd; color: #856404; }
        .out-of-stock { background-color: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <div class="page-header">
                <h1><%= lowStockView ? "Low Stock Items" : "Item Management" %></h1>
                <div>
                    <% if (!lowStockView) { %>
                        <a href="items?action=lowstock" class="btn btn-warning">View Low Stock</a>
                    <% } else { %>
                        <a href="items?action=list" class="btn btn-primary">View All Items</a>
                    <% } %>
                    <a href="items?action=add" class="btn btn-success">Add New Item</a>
                </div>
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
                            <th>Item Code</th>
                            <th>Item Name</th>
                            <th>Category</th>
                            <th>Author</th>
                            <th>Publisher</th>
                            <th>Price (Rs.)</th>
                            <th>Quantity</th>
                            <th>Reorder Level</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(items != null && !items.isEmpty()) {
                            for(Item item : items) { %>
                        <tr class="<%= item.getQuantity() <= item.getReorderLevel() ? "low-stock" : "" %>">
                            <td><%= item.getItemCode() %></td>
                            <td><%= item.getItemName() %></td>
                            <td><%= item.getCategory() %></td>
                            <td><%= item.getAuthor() %></td>
                            <td><%= item.getPublisher() %></td>
                            <td><%= String.format("%.2f", item.getPrice()) %></td>
                            <td><%= item.getQuantity() %></td>
                            <td><%= item.getReorderLevel() %></td>
                            <td>
                                <span class="status-badge <%= item.getQuantity() <= 0 ? "out-of-stock" : 
                                    (item.getQuantity() <= item.getReorderLevel() ? "low-stock" : "in-stock") %>">
                                    <%= item.getQuantity() <= 0 ? "Out of Stock" : 
                                        (item.getQuantity() <= item.getReorderLevel() ? "Low Stock" : "In Stock") %>
                                </span>
                            </td>
                            <td>
                                <a href="items?action=edit&code=<%= item.getItemCode() %>" 
                                   class="btn btn-sm btn-primary">Edit</a>
                                <a href="items?action=delete&code=<%= item.getItemCode() %>" 
                                   class="btn btn-sm btn-danger" 
                                   onclick="return confirm('Are you sure you want to delete this item?')">Delete</a>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr>
                            <td colspan="10" class="text-center">No items found</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
</body>
</html>