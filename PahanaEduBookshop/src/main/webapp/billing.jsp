<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.Customer" %>
<%@ page import="com.pahanaedu.model.Item" %>
<%@ page import="java.lang.SuppressWarnings" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String billNumber = (String) request.getAttribute("billNumber");
    @SuppressWarnings("unchecked")
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
    @SuppressWarnings("unchecked")
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Bill - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .billing-form { background: white; padding: 30px; border-radius: 10px; }
        .form-section { margin-bottom: 30px; }
        .form-section h3 { margin-bottom: 20px; color: #2c3e50; }
        .form-row { display: flex; gap: 15px; margin-bottom: 15px; }
        .col-md-2 { flex: 0 0 16.666667%; }
        .col-md-4 { flex: 0 0 33.333333%; }
        .col-md-6 { flex: 0 0 50%; }
        .form-actions { text-align: center; margin-top: 30px; }
        .bill-item { background: #f8f9fa; padding: 15px; margin-bottom: 10px; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <div class="page-header">
                <h1>Create New Bill</h1>
                <a href="billing?action=list" class="btn btn-secondary">View All Bills</a>
            </div>
            
            <div class="billing-form">
                <form action="billing" method="post" onsubmit="return validateBillForm()">
                    <input type="hidden" name="action" value="create">
                    
                    <div class="form-section">
                        <h3>Bill Information</h3>
                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label>Bill Number:</label>
                                <input type="text" name="billNumber" value="<%= billNumber %>" 
                                       class="form-control" readonly>
                            </div>
                            <div class="form-group col-md-6">
                                <label>Date:</label>
                                <input type="date" name="billDate" value="<%= new java.sql.Date(System.currentTimeMillis()) %>" 
                                       class="form-control" readonly>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-section">
                        <h3>Customer Information</h3>
                        <div class="form-group">
                            <label>Select Customer:</label>
                            <select name="accountNumber" class="form-control" required>
                                <option value="">-- Select Customer --</option>
                                <% if(customers != null) {
                                    for(Customer customer : customers) { %>
                                <option value="<%= customer.getAccountNumber() %>">
                                    <%= customer.getName() %> - <%= customer.getAccountNumber() %>
                                </option>
                                <% }
                                } %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-section">
                        <h3>Items</h3>
                        <div id="billItemsContainer">
                            <div class="bill-item">
                                <div class="form-row">
                                    <div class="form-group col-md-4">
                                        <label>Item</label>
                                        <select name="itemId" class="form-control item-select" 
                                                onchange="updatePrice(this)">
                                            <option value="">-- Select Item --</option>
                                            <% if(items != null) {
                                                for(Item item : items) { %>
                                            <option value="<%= item.getItemId() %>" 
                                                    data-price="<%= item.getPrice() %>"
                                                    data-stock="<%= item.getQuantity() %>">
                                                <%= item.getItemName() %> - Rs. <%= item.getPrice() %> 
                                                (Stock: <%= item.getQuantity() %>)
                                            </option>
                                            <% }
                                            } %>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Quantity</label>
                                        <input type="number" name="quantity" class="form-control quantity" 
                                               min="1" onchange="calculateTotal()">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Unit Price</label>
                                        <input type="number" name="price" class="form-control price" 
                                               step="0.01" readonly>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>Total</label>
                                        <input type="number" name="itemTotal" class="form-control item-total" 
                                               step="0.01" readonly>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label>&nbsp;</label>
                                        <button type="button" class="btn btn-danger" 
                                                onclick="removeBillItem(this)">Remove</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <button type="button" class="btn btn-secondary" onclick="addBillItem()">
                            Add Another Item
                        </button>
                    </div>
                    
                    <div class="form-section">
                        <h3>Summary</h3>
                        <div class="form-row">
                            <div class="form-group col-md-4">
                                <label>Total Amount:</label>
                                <input type="number" id="totalAmount" class="form-control" 
                                       step="0.01" readonly>
                            </div>
                            <div class="form-group col-md-4">
                                <label>Discount:</label>
                                <input type="number" name="discount" id="discount" class="form-control" 
                                       step="0.01" value="0" onchange="calculateTotal()">
                            </div>
                            <div class="form-group col-md-4">
                                <label>Net Amount:</label>
                                <input type="number" id="netAmount" class="form-control" 
                                       step="0.01" readonly>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Create Bill</button>
                        <button type="button" class="btn btn-secondary" 
                                onclick="window.location.href='dashboard.jsp'">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script src="js/validation.js"></script>
    
</body>
</html>