package com.example.student_demo.model.order;

import com.example.student_demo.model.product.Product;

public class OrderItem {
    private Product product;
    private int quantity;
    private double price;
    private String note;
    public OrderItem(Product product, int quantity, double price, String note) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.note = note;
    }
    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getPrice() {
        return price;
    }
    public String getNote() {
        return note;
    }
    
}
