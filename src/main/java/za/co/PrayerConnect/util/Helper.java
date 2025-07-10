package za.co.PrayerConnect.util;

import za.co.PrayerConnect.domain.User;

import java.util.regex.Pattern;

public class Helper {

    public static String generateId() {
        return java.util.UUID.randomUUID().toString();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

//    public static boolean isAnonymousOrNot(boolean anonymous) {
//        return anonymous;
//    }

    public static boolean isValidDateTime(java.time.LocalDateTime createdAt) {
        return createdAt != null && !createdAt.isAfter(java.time.LocalDateTime.now());
    }

    public static boolean isAnsweredOrNot(boolean isAnswered) {
        return isAnswered;
    }

    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean isValidPassword(String password) {
        if (isNullOrEmpty(password)) return false;
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d).{6,}$";
        return Pattern.matches(passwordRegex, password);
    }
    public static boolean isValidAge(int age) {
        return age >= 0 && age <= 120; // Assuming a realistic age range
    }
    public static boolean isValidMessage(String message) {
        return message != null && message.length() >= 10;
    }





}
