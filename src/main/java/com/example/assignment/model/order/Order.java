package com.example.assignment.model.order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.assignment.model.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )

    private long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> orderItems;

    private LocalDate orderDate;
    private double totalBill;
    private String status;

    @ManyToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "id")
    private User employee;

    public Order(User employee, LocalDate orderDate, String status, double totalBill) {
        this.employee = employee;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
        this.status = status;
        this.totalBill = totalBill;
    }

    public Order() {
        this.orderItems = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public String getStatus() {
        return status;
    }

    public User getEmployee() {
        return employee;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
