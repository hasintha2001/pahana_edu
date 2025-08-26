<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.User" %>
<%@ page import="com.pahanaedu.dao.CustomerDAO" %>
<%@ page import="com.pahanaedu.dao.ItemDAO" %>
<%@ page import="com.pahanaedu.dao.BillDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>

<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

   
    CustomerDAO customerDAO = new CustomerDAO();
    ItemDAO itemDAO = new ItemDAO();
    BillDAO billDAO = new BillDAO();

    int totalCustomers = customerDAO.getTotalCustomers();
    int totalItems = itemDAO.getTotalItems();
    BigDecimal todaysSales = billDAO.getTodaysSales();
    int lowStockItems = itemDAO.getLowStockItemCount();  
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Pahana Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <!-- Navigation Menu -->
        <nav class="navbar">
            <div class="nav-brand">
                <h2>Pahana Edu Bookshop</h2>
            </div>
            <ul class="nav-menu">
                <li><a href="dashboard.jsp" class="active">Dashboard</a></li>
                <li><a href="customers">Customers</a></li>
                <li><a href="items">Items</a></li>
                <li><a href="billing.jsp">Billing</a></li>
                <li><a href="reports.jsp">Reports</a></li>
                <li><a href="help.jsp">Help</a></li>
                <li class="nav-right">
                    <span>Welcome, <%= user.getFullName() %></span>
                    <a href="logout" class="btn-logout">Logout</a>
                </li>
            </ul>
        </nav>
        
        <!-- Main Content -->
        <div class="dashboard-content">
            <h1>Dashboard</h1>
            
            <div class="dashboard-cards">
                <div class="card">
                    <div class="card-icon">👥</div>
                    <h3>Customer Management</h3>
                    <p>Add, edit, and manage customer accounts</p>
                    <a href="customers" class="btn btn-primary">Manage Customers</a>
                </div>
                
                <div class="card">
                    <div class="card-icon">📚</div>
                    <h3>Item Management</h3>
                    <p>Manage book inventory and items</p>
                    <a href="items" class="btn btn-primary">Manage Items</a>
                </div>
                
                <div class="card">
                    <div class="card-icon">💰</div>
                    <h3>Billing</h3>
                    <p>Create and manage customer bills</p>
                    <a href="billing.jsp" class="btn btn-primary">Create Bill</a>
                </div>
                
                <div class="card">
                    <div class="card-icon">📊</div>
                    <h3>Reports</h3>
                    <p>View sales reports and analytics</p>
                    <a href="reports.jsp" class="btn btn-primary">View Reports</a>
                </div>
            </div>
            
            <!-- Quick Stats (Dynamic) -->
            <div class="stats-section">
                <h2>Quick Statistics</h2>
                <div class="stats-grid">
                    <div class="stat-box">
                        <h4>Total Customers</h4>
                        <p class="stat-number"><%= totalCustomers %></p>
                    </div>
                    <div class="stat-box">
                        <h4>Total Items</h4>
                        <p class="stat-number"><%= totalItems %></p>
                    </div>
                    <div class="stat-box">
                        <h4>Today's Sales</h4>
                        <p class="stat-number">Rs. <%= todaysSales != null ? todaysSales : "0.00" %></p>
                    </div>
                    <div class="stat-box">
                        <h4>Low Stock Items</h4>
                        <p class="stat-number"><%= lowStockItems %></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>