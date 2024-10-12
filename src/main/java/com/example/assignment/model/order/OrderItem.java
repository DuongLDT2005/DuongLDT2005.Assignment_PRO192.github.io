package com.example.assignment.model.order;

import com.example.assignment.model.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
@Entity
// @Table
public class OrderItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name="selected_product")
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
