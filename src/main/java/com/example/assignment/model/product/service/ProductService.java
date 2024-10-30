package com.example.assignment.model.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    // private final InvoiceService invoiceService;

    @Autowired
    public ProductService(ProductRepository productRepository
            // InvoiceService invoiceService
    ) {
        this.productRepository = productRepository;
        // this.invoiceService=invoiceService;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void removeProduct(long id) {
        productRepository.deleteById(id);
    }

    public void reduceProduct(long id, int subtract) {
        Optional<Product> productOpt = productRepository.findAll().stream()
                .filter(p -> p.getId() == (id))
                .findFirst();
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(product.getQuantity() - subtract);
            addProduct(product);
        }
    }

    // public void setTotalAmountPerProduct() {
    //     List<Invoice> invoices = invoiceService.getInvoice();
    //     for (Product product : getProducts()) {
    //         double totalPrice = 0;
    //         for (Invoice invoice : invoices) {
    //             for (OrderItem item : invoice.getOrder().getOrderItems()) {
    //                 if ((item.getProduct().getId()) == (product.getId())) {
    //                     double price = product.getPrice();
    //                     totalPrice += price;
    //                 }
    //             }
    //         }
    //         product.setTotalSold(totalPrice);
    //         addProduct(product);
    //     }
    // }

    public List<Product> getTopSoldProduct(int requireProductQuantity) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            productList.add(productRepository.findByOrderByTotalSoldsDesc().get(i));
        }
        return productList;
    }

}
