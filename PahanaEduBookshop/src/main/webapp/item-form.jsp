<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.Item" %>
<%@ page import="java.util.List" %>
<%@ page import="java.lang.SuppressWarnings" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Item item = (Item) request.getAttribute("item");
    String itemCode = (String) request.getAttribute("itemCode");
    @SuppressWarnings("unchecked")
    List<String> categories = (List<String>) request.getAttribute("categories");
    boolean isEdit = (item != null);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= isEdit ? "Edit" : "Add" %> Item - Pahana Edu Bookshop</title>
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
            <h1><%= isEdit ? "Edit Item" : "Add New Item" %></h1>
            
            <div class="form-container">
                <form action="items" method="post" onsubmit="return validateItemForm()">
                    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>">
                    
                    <div class="form-group">
                        <label for="itemCode">Item Code:</label>
                        <input type="text" id="itemCode" name="itemCode" 
                               value="<%= isEdit ? item.getItemCode() : itemCode %>" 
                               class="form-control" readonly>
                    </div>
                    
                    <div class="form-group">
                        <label for="itemName">Item Name: *</label>
                        <input type="text" id="itemName" name="itemName" 
                               value="<%= isEdit ? item.getItemName() : "" %>" 
                               class="form-control" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="category">Category: *</label>
                        <select id="category" name="category" class="form-control" required>
                            <option value="">-- Select Category --</option>
                            <option value="Programming" <%= isEdit && "Programming".equals(item.getCategory()) ? "selected" : "" %>>Programming</option>
                            <option value="Computer Science" <%= isEdit && "Computer Science".equals(item.getCategory()) ? "selected" : "" %>>Computer Science</option>
                            <option value="Mathematics" <%= isEdit && "Mathematics".equals(item.getCategory()) ? "selected" : "" %>>Mathematics</option>
                            <option value="Fiction" <%= isEdit && "Fiction".equals(item.getCategory()) ? "selected" : "" %>>Fiction</option>
                            <option value="Non-Fiction" <%= isEdit && "Non-Fiction".equals(item.getCategory()) ? "selected" : "" %>>Non-Fiction</option>
                            <option value="Educational" <%= isEdit && "Educational".equals(item.getCategory()) ? "selected" : "" %>>Educational</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="author">Author:</label>
                        <input type="text" id="author" name="author" 
                               value="<%= isEdit ? item.getAuthor() : "" %>" 
                               class="form-control">
                    </div>
                    
                    <div class="form-group">
                        <label for="publisher">Publisher:</label>
                        <input type="text" id="publisher" name="publisher" 
                               value="<%= isEdit ? item.getPublisher() : "" %>" 
                               class="form-control">
                    </div>
                    
                    <div class="form-group">
                        <label for="price">Price (Rs.): *</label>
                        <input type="number" id="price" name="price" 
                               value="<%= isEdit ? item.getPrice() : "" %>" 
                               class="form-control" step="0.01" min="0" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="quantity">Quantity: *</label>
                        <input type="number" id="quantity" name="quantity" 
                               value="<%= isEdit ? item.getQuantity() : "0" %>" 
                               class="form-control" min="0" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="reorderLevel">Reorder Level:</label>
                        <input type="number" id="reorderLevel" name="reorderLevel" 
                               value="<%= isEdit ? item.getReorderLevel() : "10" %>" 
                               class="form-control" min="0">
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <%= isEdit ? "Update Item" : "Add Item" %>
                        </button>
                        <a href="items" class="btn btn-secondary">Cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <script src="js/validation.js"></script>
    
</body>
</html>