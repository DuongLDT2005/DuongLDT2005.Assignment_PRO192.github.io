package com.example.assignment.model.order.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Invoice;
import com.example.assignment.model.order.InvoiceRepository;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
            OrderService orderService) {
        this.invoiceRepository = invoiceRepository;
        this.orderService = orderService;
    }

    public List<Invoice> getInvoice(){
        return invoiceRepository.findAll();
    }

    public void addInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }

  

}
