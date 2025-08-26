<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Help - Pahana Edu Bookshop</title>
    <link rel="stylesheet" href="css/style.css">
    
    <style>
        .help-section { margin: 30px 0; }
        .help-section h2 { 
            color: #2c3e50; 
            margin-bottom: 20px; 
            padding-bottom: 10px; 
            border-bottom: 2px solid #e74c3c; 
        }
        .help-card { 
            background: white; 
            padding: 25px; 
            border-radius: 10px; 
            margin-bottom: 20px; 
            box-shadow: 0 2px 5px rgba(0,0,0,0.1); 
        }
        .help-card h3 { color: #34495e; margin-bottom: 15px; }
        .help-card ul, .help-card ol { margin-left: 20px; }
        .help-table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-top: 15px; 
        }
        .help-table th, .help-table td { 
            padding: 10px; 
            border: 1px solid #ddd; 
            text-align: left; 
        }
        .help-table th { background-color: #f8f9fa; }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="includes/navbar.jsp" />
        
        <div class="content">
            <h1>Help &amp; User Guide</h1>
            
            <div class="help-section">
                <h2>Getting Started</h2>
                <div class="help-card">
                    <h3>1. Login to the System</h3>
                    <p>Use your username and password to login. Default admin credentials:</p>
                    <ul>
                        <li>Username: admin</li>
                        <li>Password: admin123</li>
                    </ul>
                    <p>Please change your password after first login for security.</p>
                </div>
                
                <div class="help-card">
                    <h3>2. Dashboard Overview</h3>
                    <p>The dashboard provides quick access to all system features:</p>
                    <ul>
                        <li><strong>Customer Management:</strong> Add and manage customer accounts</li>
                        <li><strong>Item Management:</strong> Manage book inventory</li>
                        <li><strong>Billing:</strong> Create and print customer bills</li>
                        <li><strong>Reports:</strong> View sales and inventory reports</li>
                    </ul>
                </div>
            </div>
            
            <div class="help-section">
                <h2>Customer Management</h2>
                <div class="help-card">
                    <h3>Adding a New Customer</h3>
                    <ol>
                        <li>Click on "Customers" in the navigation menu</li>
                        <li>Click "Add New Customer" button</li>
                        <li>Fill in customer details</li>
                        <li>Account number is auto-generated</li>
                        <li>Click "Save" to add the customer</li>
                    </ol>
                </div>
            </div>
            
            <div class="help-section">
                <h2>Contact Support</h2>
                <div class="help-card">
                    <p>For additional help or technical support:</p>
                    <ul>
                        <li><strong>Email:</strong> support@pahanaedu.lk</li>
                        <li><strong>Phone:</strong> 011-2345678</li>
                        <li><strong>Hours:</strong> Monday-Friday, 9:00 AM - 5:00 PM</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    
</body>
</html>