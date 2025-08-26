<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.model.Bill" %>
<%@ page import="com.pahanaedu.model.Bill.BillItem" %>
<%
    Bill bill = (Bill) request.getAttribute("bill");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Print Bill - <%= bill.getBillNumber() %></title>
    <style>
        @media print {
            body { margin: 0; }
            .no-print { display: none; }
        }
        body {
            font-family: Arial, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .bill-header {
            text-align: center;
            border-bottom: 2px solid #333;
            padding-bottom: 20px;
            margin-bottom: 20px;
        }
        .bill-header h1 {
            margin: 0;
            color: #333;
        }
        .bill-info {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f8f9fa;
        }
        .text-right {
            text-align: right;
        }
        .total-row {
            font-weight: bold;
            font-size: 16px;
        }
        .bill-footer {
            text-align: center;
            margin-top: 50px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
        }
        .print-button {
            margin: 20px;
            text-align: center;
        }
        .btn {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="print-button no-print">
        <button class="btn" onclick="window.print()">Print This Bill</button>
        <button class="btn" onclick="window.close()">Close</button>
    </div>
    
    <div class="bill-header">
        <h1>PAHANA EDU BOOKSHOP</h1>
        <p>123 Main Street, Colombo 07<br>
        Tel: 011-2345678 | Email: info@pahanaedu.lk<br>
        Business Reg. No: PV 12345</p>
    </div>
    
    <div class="bill-info">
        <div>
            <strong>INVOICE</strong><br>
            Bill No: <%= bill.getBillNumber() %><br>
            Date: <%= bill.getBillDate() %>
        </div>
        <div style="text-align: right;">
            <% if(bill.getCustomer() != null) { %>
            <strong>Bill To:</strong><br>
            <%= bill.getCustomer().getName() %><br>
            <%= bill.getCustomer().getAddress() != null ? bill.getCustomer().getAddress() : "" %><br>
            Tel: <%= bill.getCustomer().getTelephone() %>
            <% } else { %>
            <strong>Walk-in Customer</strong>
            <% } %>
        </div>
    </div>
    
    <table>
        <thead>
            <tr>
                <th style="width: 50%;">Item Description</th>
                <th style="width: 15%;">Qty</th>
                <th style="width: 15%;">Unit Price</th>
                <th style="width: 20%;">Amount</th>
            </tr>
        </thead>
        <tbody>
            <% for(BillItem item : bill.getBillItems()) { %>
            <tr>
                <td><%= item.getItem().getItemName() %></td>
                <td><%= item.getQuantity() %></td>
                <td>Rs. <%= String.format("%.2f", item.getUnitPrice()) %></td>
                <td class="text-right">Rs. <%= String.format("%.2f", item.getTotalPrice()) %></td>
            </tr>
            <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="3" class="text-right"><strong>Subtotal:</strong></td>
                <td class="text-right">Rs. <%= String.format("%.2f", bill.getTotalAmount()) %></td>
            </tr>
            <% if(bill.getDiscount().doubleValue() > 0) { %>
            <tr>
                <td colspan="3" class="text-right"><strong>Discount:</strong></td>
                <td class="text-right">Rs. <%= String.format("%.2f", bill.getDiscount()) %></td>
            </tr>
            <% } %>
            <tr class="total-row">
                <td colspan="3" class="text-right"><strong>TOTAL AMOUNT:</strong></td>
                <td class="text-right">Rs. <%= String.format("%.2f", bill.getNetAmount()) %></td>
            </tr>
        </tfoot>
    </table>
    
    <div class="bill-footer">
        <p><strong>Thank you for your business!</strong></p>
        <p>Exchange possible within 7 days with receipt.<br>
        No refunds on sale items.</p>
        <br>
        <p>_____________________<br>
        Authorized Signature</p>
    </div>
    
    <script>
        // Auto-print when page loads
        window.onload = function() {
            // Uncomment to auto-print
            // window.print();
        }
    </script>
</body>
</html>