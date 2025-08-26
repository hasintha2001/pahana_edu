<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.Bill" %>
<%@ page import="com.pahanaedu.model.Bill.BillItem" %>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Bill - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .bill-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            margin-top: 20px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .bill-header {
            text-align: center;
            padding-bottom: 20px;
            border-bottom: 2px solid #333;
            margin-bottom: 20px;
        }
        .bill-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
        }
        .bill-items-table {
            width: 100%;
            border-collapse: collapse;
        }
        .bill-items-table th,
        .bill-items-table td {
            padding: 10px;
            border: 1px solid #ddd;
        }
        .bill-items-table th {
            background-color: #f8f9fa;
        }
        .text-right {
            text-align: right;
        }
        .total-row {
            background-color: #f8f9fa;
            font-size: 18px;
        }
        .bill-footer {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <div class="page-header">
                <h1>Bill Details</h1>
                <div>
                    <a href="billing?action=print&billNumber=<%= bill.getBillNumber() %>" 
                       class="btn btn-primary" target="_blank">Print Bill</a>
                    <a href="billing?action=new" class="btn btn-success">New Bill</a>
                    <a href="billing?action=list" class="btn btn-secondary">All Bills</a>
                </div>
            </div>
            
            <div class="bill-container">
                <div class="bill-header">
                    <h2>Pahana Edu Bookshop</h2>
                    <p>123 Main Street, Colombo 07<br>
                    Tel: 011-2345678 | Email: info@pahanaedu.lk</p>
                </div>
                
                <div class="bill-info">
                    <div class="bill-info-left">
                        <p><strong>Bill Number:</strong> <%= bill.getBillNumber() %></p>
                        <p><strong>Date:</strong> <%= bill.getBillDate() %></p>
                    </div>
                    <div class="bill-info-right">
                        <% if(bill.getCustomer() != null) { %>
                        <p><strong>Customer:</strong> <%= bill.getCustomer().getName() %></p>
                        <p><strong>Account:</strong> <%= bill.getCustomer().getAccountNumber() %></p>
                        <p><strong>Phone:</strong> <%= bill.getCustomer().getTelephone() %></p>
                        <% } %>
                    </div>
                </div>
                
                <table class="bill-items-table">
                    <thead>
                        <tr>
                            <th>Item</th>
                            <th>Quantity</th>
                            <th>Unit Price</th>
                            <th>Total</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for(BillItem item : bill.getBillItems()) { %>
                        <tr>
                            <td><%= item.getItem().getItemName() %></td>
                            <td><%= item.getQuantity() %></td>
                            <td>Rs. <%= String.format("%.2f", item.getUnitPrice()) %></td>
                            <td>Rs. <%= String.format("%.2f", item.getTotalPrice()) %></td>
                        </tr>
                        <% } %>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td colspan="3" class="text-right"><strong>Subtotal:</strong></td>
                            <td><strong>Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></strong></td>
                        </tr>
                        <tr>
                            <td colspan="3" class="text-right"><strong>Discount:</strong></td>
                            <td><strong>Rs. <%= String.format("%.2f", bill.getDiscount()) %></strong></td>
                        </tr>
                        <tr class="total-row">
                            <td colspan="3" class="text-right"><strong>Net Total:</strong></td>
                            <td><strong>Rs. <%= String.format("%.2f", bill.getNetAmount()) %></strong></td>
                        </tr>
                    </tfoot>
                </table>
                
                <div class="bill-footer">
                    <p>Thank you for your business!</p>
                </div>
            </div>
        </div>
    </div>
</body>
</html>