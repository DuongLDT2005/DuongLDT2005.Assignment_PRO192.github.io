package com.example.assignment.model.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="product_sequence"
    )

    private long  id;
    private String name;
    private String description;
    private float price;
    private int quantity;
    private String category;
    private boolean status;//true=availabel

    // public Product(Long id, String name, String description, float price, int quantity, String category,
    //         boolean status) {
    //     this.id = id;
    //     this.name = name;
    //     this.description = description;
    //     this.price = price;
    //     this.quantity = quantity;
    //     this.category = category;
    //     this.status = status;
    // }

    public Product() {
    }

    public Product(String name, String description, float price, int quantity, String category, boolean status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // @Override
    // public String toString() {
    //     return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
    //             + ", quantity=" + quantity + ", category=" + category + ", status=" + status + "]";
    // }

}
