package org.example.constants;

public class PhoneNumberValidator {
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Remove all non-digit characters from the phone number
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");

        // Check if the resulting string contains only digits
        if (!digitsOnly.matches("\\d+")) {
            return false;
        }

        // Check if the phone number starts with a valid prefix
        // We'll consider a valid prefix to be either a '+', '(' or a digit
        char firstChar = phoneNumber.charAt(0);
        if (!(firstChar == '+' || firstChar == '(' || Character.isDigit(firstChar))) {
            return false;
        }

        return true;
    }
}