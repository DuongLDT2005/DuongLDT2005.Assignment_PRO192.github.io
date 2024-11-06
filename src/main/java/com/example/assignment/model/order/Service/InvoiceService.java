package com.example.assignment.model.order.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.order.Invoice;
import com.example.assignment.model.order.InvoiceRepository;
import com.example.assignment.model.order.OrderItem;
import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.service.ProductService;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.User;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;
    private final ProductService productService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository,
            OrderService orderService,
            ProductService productService) {
        this.invoiceRepository = invoiceRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public List<Invoice> getInvoice() {
        return invoiceRepository.findAll();
    }

    public int getAmount() {
        return getInvoice().size();
    }

    public void addInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public List<Invoice> getInvoicesOfUser(User currentUser) {
        List<Invoice> returnInvoices = new ArrayList<>();
        for (Invoice invoice : getInvoice()) {
            if (invoice.getOrder().getEmployee().getShopName().equals(currentUser.getShopName())) {
                returnInvoices.add(invoice);
            }
        }
        return returnInvoices;
    }

    public List<Invoice> getInvoicesByDate(User currentUser, LocalDate date) {
        List<Invoice> returnInvoices = new ArrayList<>();
        for (Invoice invoice : getInvoicesOfUser(currentUser)) {
            if (invoice.getOrder().getOrderDate().isEqual(date)) {
                returnInvoices.add(invoice);
            }
        }
        return returnInvoices;
    }


    public double getTotalInvoiceByDay(LocalDate date, Manager currentManager) {
        double totalAmount = 0;
        if (currentManager.getInventoryManager().getRestockDate() == null) {
            return 0;
        }
        for (Invoice invoice : getInvoicesByDate(currentManager, date)) {
            totalAmount += invoice.getTotalAmount();
        }
        return totalAmount;
    }

    public void setTotalAmountPerProduct() {
        List<Product> products = productService.getProducts();
        for (Product product : products) {
            double totalPrice = 0;
            for (Invoice invoice : getInvoice()) {
                for (OrderItem item : invoice.getOrder().getOrderItems()) {
                    if ((item.getProduct().getId()) == (product.getId())) {
                        double price = product.getPrice();
                        totalPrice += price;
                    }
                }
            }
            product.setTotalSold(totalPrice);
            productService.addProduct(product);
        }
    }


    public List<Product> getTopSoldsByQuantity( Manager currentManager) {
        if (currentManager.getInventoryManager().getRestockDate() == null) {
            return null;
        }
        List<Product> products = productService.getProductByUser(currentManager);
        List<Product> result = new ArrayList<>();
        for (Product product : products) {
            int totalquantity = 0;
            for (Invoice invoice : getInvoicesOfUser(currentManager)) {
                for (OrderItem item : invoice.getOrder().getOrderItems()) {
                    if (product.getId() == item.getProduct().getId()) {
                        int temp = item.getQuantity();
                        totalquantity += temp;
                    }
                }
            }

            Product tproduct = new Product();
            tproduct.setId(product.getId());
            tproduct.setName(product.getName());
            tproduct.setPrice(product.getPrice());
            tproduct.setQuantity(totalquantity);
            result.add(tproduct);

        }
        Collections.sort(result, Comparator.comparing(Product::getQuantity).reversed());
        List<Product> returnResult = new ArrayList<>();
        int maxQuantity = 10;
        if (productService.getProductByUser(currentManager).size() < 10) {
            maxQuantity = productService.getProductByUser(currentManager).size();
        }
        for (int i = 0; i < maxQuantity; i++) {
            returnResult.add(result.get(i));
        }

        return returnResult;
    }
}
