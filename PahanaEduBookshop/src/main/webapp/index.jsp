<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Redirect to login if not authenticated
    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
    } else {
        response.sendRedirect("dashboard.jsp");
    }
%>