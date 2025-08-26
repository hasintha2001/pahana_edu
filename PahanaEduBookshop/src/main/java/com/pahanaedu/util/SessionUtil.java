package com.pahanaedu.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.pahanaedu.model.User;

public class SessionUtil {
    
    public static final String USER_ATTRIBUTE = "user";
    public static final String USERNAME_ATTRIBUTE = "username";
    public static final String ROLE_ATTRIBUTE = "role";
    public static final int SESSION_TIMEOUT = 30 * 60; // 30 minutes
    
    public static void createUserSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(SESSION_TIMEOUT);
        session.setAttribute(USER_ATTRIBUTE, user);
        session.setAttribute(USERNAME_ATTRIBUTE, user.getUsername());
        session.setAttribute(ROLE_ATTRIBUTE, user.getRole());
    }
    
    public static User getCurrentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (User) session.getAttribute(USER_ATTRIBUTE);
        }
        return null;
    }
    
    public static String getCurrentUsername(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute(USERNAME_ATTRIBUTE);
        }
        return null;
    }
    
    public static String getCurrentUserRole(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return (String) session.getAttribute(ROLE_ATTRIBUTE);
        }
        return null;
    }
    
    public static boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(USER_ATTRIBUTE) != null;
    }
    
    public static boolean isAdmin(HttpServletRequest request) {
        String role = getCurrentUserRole(request);
        return "ADMIN".equals(role);
    }
    
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(key, value);
    }
    
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }
    
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(key);
        }
    }
}