<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.User" %>
<%
    User navUser = (User) session.getAttribute("user");
    String currentPage = request.getServletPath();
%>
<nav class="navbar">
    <div class="nav-brand">
        <h2>Pahana Edu Bookshop</h2>
    </div>
    <ul class="nav-menu">
        <li><a href="dashboard.jsp" class="<%= currentPage.contains("dashboard") ? "active" : "" %>">Dashboard</a></li>
        <li><a href="customers" class="<%= currentPage.contains("customer") ? "active" : "" %>">Customers</a></li>
        <li><a href="items" class="<%= currentPage.contains("item") ? "active" : "" %>">Items</a></li>
        <li><a href="billing" class="<%= currentPage.contains("billing") ? "active" : "" %>">Billing</a></li>
        <li><a href="reports.jsp" class="<%= currentPage.contains("reports") ? "active" : "" %>">Reports</a></li>
        <li><a href="help.jsp" class="<%= currentPage.contains("help") ? "active" : "" %>">Help</a></li>
        <li class="nav-right">
            <span>Welcome, <%= navUser != null ? navUser.getFullName() : "User" %></span>
            <a href="logout" class="btn-logout">Logout</a>
        </li>
    </ul>
</nav>