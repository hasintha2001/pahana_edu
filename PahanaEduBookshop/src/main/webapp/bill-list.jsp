<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.pahanaedu.model.Bill" %>
<%@ page import="java.lang.SuppressWarnings" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
@SuppressWarnings("unchecked")
    List<Bill> bills = (List<Bill>) request.getAttribute("bills");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bill List - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .status-badge {
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-paid {
            background-color: #d4edda;
            color: #155724;
        }
        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <div class="page-header">
                <h1>All Bills</h1>
                <a href="billing?action=new" class="btn btn-success">Create New Bill</a>
            </div>
            
            <div class="table-container">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>Bill Number</th>
                            <th>Date</th>
                            <th>Customer</th>
                            <th>Total Amount</th>
                            <th>Discount</th>
                            <th>Net Amount</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(bills != null && !bills.isEmpty()) {
                            for(Bill bill : bills) { %>
                        <tr>
                            <td><%= bill.getBillNumber() %></td>
                            <td><%= bill.getBillDate() %></td>
                            <td><%= bill.getCustomer() != null ? bill.getCustomer().getName() : "Walk-in Customer" %></td>
                            <td>Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
                            <td>Rs. <%= String.format("%.2f", bill.getDiscount()) %></td>
                            <td>Rs. <%= String.format("%.2f", bill.getNetAmount()) %></td>
                            <td>
                                <span class="status-badge <%= "PAID".equals(bill.getPaymentStatus()) ? "status-paid" : "status-pending" %>">
                                    <%= bill.getPaymentStatus() %>
                                </span>
                            </td>
                            <td>
                                <a href="billing?action=view&billNumber=<%= bill.getBillNumber() %>" 
                                   class="btn btn-sm btn-primary">View</a>
                                <a href="billing?action=print&billNumber=<%= bill.getBillNumber() %>" 
                                   class="btn btn-sm btn-secondary" target="_blank">Print</a>
                            </td>
                        </tr>
                        <% }
                        } else { %>
                        <tr>
                            <td colspan="8" class="text-center">No bills found</td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>