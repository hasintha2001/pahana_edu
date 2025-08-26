package com.pahanaedu.util;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    // Email validation pattern
    private static final String EMAIL_PATTERN = 
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
    
    // Sri Lankan phone number pattern
    private static final String PHONE_PATTERN = "^0[0-9]{9}$";
    
    // Name pattern (letters and spaces only)
    private static final String NAME_PATTERN = "^[A-Za-z ]+$";
    
    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private static final Pattern phonePattern = Pattern.compile(PHONE_PATTERN);
    private static final Pattern namePattern = Pattern.compile(NAME_PATTERN);
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return emailPattern.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return phonePattern.matcher(phone).matches();
    }
    
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return namePattern.matcher(name).matches();
    }
    
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    public static boolean isPositiveNumber(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            double value = Double.parseDouble(str);
            return value > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidQuantity(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        try {
            int value = Integer.parseInt(str);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        // Remove HTML tags and special characters
        return input.replaceAll("<[^>]*>", "")
                   .replaceAll("[<>\"']", "")
                   .trim();
    }
    
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        // At least 6 characters
        return password.length() >= 6;
    }
}