package com.example.student_demo.model.order;

import java.time.LocalDate;

public class Invoice {
private String id;
private Order order;
private double totalAmount;
private String paymentMethod;
private LocalDate invoiceDate;

    public Invoice(String id, LocalDate invoiceDate, Order order, String paymentMethod, double totalAmount) {
        this.id = id;
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    public Invoice() {
    }

    public Invoice(LocalDate invoiceDate, Order order, String paymentMethod, double totalAmount) {
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    public String getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }


}
