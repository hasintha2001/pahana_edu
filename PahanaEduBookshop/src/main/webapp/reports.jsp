<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.pahanaedu.dao.*" %>
<%@ page import="com.pahanaedu.model.*" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    // Get statistics for reports
    CustomerDAO customerDAO = new CustomerDAO();
    ItemDAO itemDAO = new ItemDAO();
    BillDAO billDAO = new BillDAO();
    
    List<Customer> allCustomers = customerDAO.getAllCustomers();
    List<Item> allItems = itemDAO.getAllItems();
    List<Item> lowStockItems = itemDAO.getLowStockItems();
    BigDecimal todaySales = billDAO.getTotalSales(new java.sql.Date(System.currentTimeMillis()));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reports - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
     <style>
        .report-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 30px 0;
        }
        .report-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .report-card h3 {
            color: #7f8c8d;
            font-size: 16px;
            margin-bottom: 10px;
        }
        .report-number {
            font-size: 36px;
            font-weight: bold;
            color: #2c3e50;
            margin: 10px 0;
        }
        .alert-number {
            color: #e74c3c;
        }
        .report-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            margin: 30px 0;
        }
        .report-actions {
            background: white;
            padding: 30px;
            border-radius: 10px;
            margin: 30px 0;
        }
        .action-buttons {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            margin-top: 20px;
        }
        .badge {
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 12px;
        }
        .badge-danger {
            background-color: #f8d7da;
            color: #721c24;
        }
        .badge-warning {
            background-color: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <h1>Reports &amp; Analytics</h1>
            
            <!-- Summary Cards -->
            <div class="report-cards">
                <div class="report-card">
                    <h3>Total Customers</h3>
                    <p class="report-number"><%= allCustomers.size() %></p>
                    <small>Active customers in system</small>
                </div>
                
                <div class="report-card">
                    <h3>Total Items</h3>
                    <p class="report-number"><%= allItems.size() %></p>
                    <small>Items in inventory</small>
                </div>
                
                <div class="report-card">
                    <h3>Low Stock Items</h3>
                    <p class="report-number alert-number"><%= lowStockItems.size() %></p>
                    <small>Items need reordering</small>
                </div>
                
                <div class="report-card">
                    <h3>Today's Sales</h3>
                    <p class="report-number">Rs. <%= todaySales != null ? String.format("%.2f", todaySales) : "0.00" %></p>
                    <small>Total sales for today</small>
                </div>
            </div>
            
            <!-- Detailed Reports -->
            <div class="report-section">
                <h2>Low Stock Alert</h2>
                <div class="table-container">
                    <table class="data-table">
                        <thead>
                            <tr>
                                <th>Item Code</th>
                                <th>Item Name</th>
                                <th>Current Stock</th>
                                <th>Reorder Level</th>
                                <th>Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if(lowStockItems != null && !lowStockItems.isEmpty()) {
                                for(Item item : lowStockItems) { %>
                            <tr>
                                <td><%= item.getItemCode() %></td>
                                <td><%= item.getItemName() %></td>
                                <td><%= item.getQuantity() %></td>
                                <td><%= item.getReorderLevel() %></td>
                                <td>
                                    <span class="badge <%= item.getQuantity() == 0 ? "badge-danger" : "badge-warning" %>">
                                        <%= item.getQuantity() == 0 ? "Out of Stock" : "Low Stock" %>
                                    </span>
                                </td>
                            </tr>
                            <% }
                            } else { %>
                            <tr>
                                <td colspan="5" class="text-center">All items are well stocked</td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- Report Actions -->
            <div class="report-actions">
                <h2>Generate Reports</h2>
                <div class="action-buttons">
                    <button class="btn btn-primary" onclick="generateReport('daily')">
                        Daily Sales Report
                    </button>
                    <button class="btn btn-primary" onclick="generateReport('monthly')">
                        Monthly Sales Report
                    </button>
                    <button class="btn btn-primary" onclick="generateReport('inventory')">
                        Inventory Report
                    </button>
                    <button class="btn btn-primary" onclick="generateReport('customer')">
                        Customer Report
                    </button>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function generateReport(type) {
            alert('Generating ' + type + ' report...\nThis feature will export the report as PDF/Excel.');
            // In real implementation, this would call a servlet to generate the report
        }
    </script>
</body>
</html>