package com.example.assignment.model.product.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.ProductRepository;
import com.example.assignment.model.user.Manager;
import com.example.assignment.model.user.Staff;
import com.example.assignment.model.user.service.UserService;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
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

    public Product getProductById(long id) {
        for (Product product : getProducts()) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public List<Product> getProductByUser(Manager currentManager) {
        if (currentManager.getInventoryManager().getProducts() == null) {
            return null;
        }
        List<Product> productList = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getInventory().getId().equals(currentManager.getInventoryManager().getId())) {
                productList.add(product);
            }
        }
        return productList;

    }

    public List<Product> getProductByStaff(Staff currentStaff) {
        Manager currentManager = userService.findManagerByShopName(currentStaff.getShopName());
        if (currentManager.getInventoryManager().getProducts().isEmpty()) {
            return null;
        }
        List<Product> productList = new ArrayList<>();
        for (Product product : getProducts()) {
            if (product.getInventory().getId().equals(currentManager.getInventoryManager().getId())) {
                productList.add(product);
            }
        }
        return productList;

    }

    public List<Product> sortProductByCatagory(List<Product> inputList) {  
        Collections.sort(inputList, Comparator.comparing(Product::getCategory));
        return inputList;
    }

    public List<Product> getProductByCatagory(List<Product> inputList,String category) {
        List<Product> returnProduct = new ArrayList<>();
        if(category==null){
            return inputList;
        }
        for(Product product : inputList){
            if(product.getCategory().equals(category)){
                returnProduct.add(product);
            }
        }
        return returnProduct;
    }

    public ArrayList<String> getCategoryList(List<Product> inputList){
        ArrayList<String> result =new ArrayList<>();
        for(Product product:inputList){
                if (!result.contains(product.getCategory())) {
                result.add(product.getCategory());
            }
        }
        return result;
    }


}
