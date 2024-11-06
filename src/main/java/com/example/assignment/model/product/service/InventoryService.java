package com.example.assignment.model.product.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public void addInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }

    public List<Product> getProducts() {
        return productService.getProducts();
    }

    public List<Inventory> getInventory() {
        return inventoryRepository.findAll();
    }

    public void setRestockDate(Inventory inventory) {
        Optional<Inventory> existingInventory = inventoryRepository.findById(inventory.getId());
        if (existingInventory.isPresent()) {
            existingInventory.get().setRestockDate(LocalDate.now());
            inventoryRepository.save(existingInventory.get());
        }

    }

}
