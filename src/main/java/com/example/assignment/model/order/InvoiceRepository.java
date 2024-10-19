package com.example.assignment.model.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

}
