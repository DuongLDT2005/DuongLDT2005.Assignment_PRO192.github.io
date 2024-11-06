package com.example.assignment.model.product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Inventory {

    @Id
    @SequenceGenerator(
        name = "inventory_sequence",
        sequenceName="inventory_sequence",
        allocationSize=1
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="inventory_sequence"
    )
    private long id;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Product> products;

    @Column(name = "restock_date")
    private LocalDate restockDate;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public Inventory(Long id, LocalDate restockDate) {
        this.products = new ArrayList<>();
        this.id = id;

        this.restockDate = restockDate;
    }

    public Inventory(LocalDate restockDate) {
        this.products = new ArrayList<>();

        this.restockDate = restockDate;
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDate getRestockDate() {
        return restockDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setRestockDate(LocalDate restockDate) {
        this.restockDate = restockDate;
    }

}
