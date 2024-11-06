package com.example.assignment.model.order;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
public class Invoice {

    @Id
    @SequenceGenerator(
        name = "invoice_sequence",
        sequenceName="invoice_sequence",
        allocationSize=1
    )
    @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="invoice_sequence"
    )
    private long id;

    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="order_id")
    private Order order;
    
    private double totalAmount;
    private String paymentMethod;
    private LocalDate invoiceDate;

    public Invoice() {
    }

    public Invoice(LocalDate invoiceDate, Order order, String paymentMethod, double totalAmount) {
        this.invoiceDate = invoiceDate;
        this.order = order;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }

    public long getId() {
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

    public void setId(long id) {
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
