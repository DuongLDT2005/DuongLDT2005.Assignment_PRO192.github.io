package com.example.assignment.model.order;

import com.example.assignment.model.product.Product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name="selected_product", referencedColumnName = "id")
    private Product product;
    private int quantity;
    private double price;
    private String note;

    

    public OrderItem(Product product, int quantity, String note) {
        this.product = product;
        this.quantity = quantity;
        
        this.note = note;
    }
    

    public OrderItem(Order order, Product product, int quantity, String note) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.note = note;
    }

    public OrderItem() {
    }
    


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return product.getPrice()*quantity;
    }

    public String getNote() {
        return note;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }


}
