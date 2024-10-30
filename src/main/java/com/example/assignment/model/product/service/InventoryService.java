package com.example.assignment.model.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.product.Inventory;
import com.example.assignment.model.product.InventoryRepository;
import com.example.assignment.model.product.Product;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductService productService;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository, ProductService productService) {
        this.inventoryRepository = inventoryRepository;
        this.productService = productService;
    }

    public List<Product> getProducts() {
        return productService.getProducts();
    }

    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }

    // public Inventory getInventoryById(long id){
    // }
    // public void addProductIntoInventory(int inventoryIndex){
    //     getInventory().get(inventoryIndex).getProducts().add(e);
    // }

}
