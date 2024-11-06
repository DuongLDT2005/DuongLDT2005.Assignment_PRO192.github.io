package com.example.assignment.controller;

import java.util.HashMap;

import com.example.assignment.model.user.User;
import com.example.assignment.model.user.service.UserService;

public class Validation {

    private final UserService userService;

    public Validation(UserService userService) {
        this.userService = userService;
    }

    public static boolean isCostHigherThanPrice(double cost, double price) {
        return cost > price;
    }

    public static boolean isStringNull(String input) {
        return input == null || input.trim().isEmpty();
    }

    public static boolean isEmailFormatCorrect(String email) {
        String regex = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    public static boolean isPhoneNumberCorrect(String phone) {
        return phone.matches("^\\d{10,}$");
    }

    public HashMap<String, String> getLoginError(
            String name,
            String shopName,
            String email,
            String phoneNumber,
            String role,
            String userName,
            String password,
            String confirmPassword
    ) {
        String SHOP_NAME_PATTERN = "^[a-zA-Z0-9]+@[a-zA-Z]+$";
        HashMap<String, String> returnError = new HashMap<>();
        if (isStringNull(name)
                || isStringNull(shopName)
                || isStringNull(email)
                || isStringNull(phoneNumber)
                || isStringNull(role)
                || isStringNull(userName)
                || isStringNull(password)
                || isStringNull(confirmPassword)) {
            returnError.put("notEnoughFieldsError", "Please fill in all exist field!");
            return returnError;
        }
        if (!isPhoneNumberCorrect(phoneNumber)) {
            returnError.put("wrongPhoneFormatError", "Wrong phone format!");
            return returnError;
        }
        if (!isEmailFormatCorrect(email)) {
            returnError.put("wrongMailFormatError", "Wrong mail format!");
            return returnError;

        }

        if (!password.equals(confirmPassword)) {
            returnError.put("wrongConfirmPassword", "The second password must be the same!");
            return returnError;
        }
        if (!role.equals("staff")) {
            if (!shopName.matches(SHOP_NAME_PATTERN)) {
                returnError.put("wrongShopnameFormatError", "Invalid shop name format. Format should be brand@shop_type.");
                return returnError;
            }
        }

        if (userName.trim().contains(" ") || password.trim().contains(" ")) {
            returnError.put("invalidFormatError", "Username and password cannot contain spaces.");
            return returnError;
        }

        for (User user : userService.getUser()) {
            if (user.getUsername().equalsIgnoreCase(userName)) {
                returnError.put("usernameExistedError", "This username is already exist!");
                return returnError;
            }

            if (user.getPhonenumber().equalsIgnoreCase(phoneNumber.trim())) {
                returnError.put("usernameExistedError", "This username is already exist!");
                return returnError;
            }

            if (user.getEmail().equalsIgnoreCase(email)) {
                returnError.put("mailExistedError", "This mail is already exist!");
                return returnError;

            }

        }

        return returnError;

    }

}
