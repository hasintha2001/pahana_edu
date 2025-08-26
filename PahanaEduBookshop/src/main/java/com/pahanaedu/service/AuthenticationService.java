package com.pahanaedu.service;

import com.pahanaedu.dao.UserDAO;
import com.pahanaedu.model.User;

public class AuthenticationService {
    private UserDAO userDAO;

    public AuthenticationService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }
        return userDAO.authenticateUser(username, password);
    }

    public boolean register(User user) {
        if (user == null || user.getUsername() == null || 
            user.getPassword() == null) {
            return false;
        }
        return userDAO.createUser(user);
    }
}