package com.example.assignment.model.order;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {
        List<Invoice> findAllByInvoiceDate(LocalDate invoiceDate);
                
}
