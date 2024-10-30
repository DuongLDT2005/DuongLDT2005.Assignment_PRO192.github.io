package com.example.assignment.controller;

public class Validation {

    public static boolean isCostHigherThanPrice(double cost, double price) {
        return cost > price;
    }

    public static boolean isStringNull(String input) {
        return input == null || input.trim().isEmpty();
    }

}
