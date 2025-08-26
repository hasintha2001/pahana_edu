<!-- webapp/login.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Pahana Edu Bookshop - Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body class="login-page">
    <div class="login-container">
        <div class="login-box">
            <h1>Pahana Edu Bookshop</h1>
            <h2>Management System Login</h2>
            
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="alert alert-error">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <form action="login" method="post" onsubmit="return validateLogin()">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" id="username" name="username" required 
                           placeholder="Enter your username">
                </div>
                
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required 
                           placeholder="Enter your password">
                </div>
                
                <button type="submit" class="btn btn-primary">Login</button>
            </form>
            
            <div class="login-footer">
                <p>© 2024 Pahana Edu Bookshop. All rights reserved.</p>
            </div>
        </div>
    </div>
    
    <script src="js/validation.js"></script>
</body>
</html>