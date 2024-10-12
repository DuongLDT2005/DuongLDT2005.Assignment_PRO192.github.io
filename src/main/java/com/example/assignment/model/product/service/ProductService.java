package com.example.assignment.model.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.assignment.model.product.Product;
import com.example.assignment.model.product.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getProducts(){
        return productRepository.findAll();
    }
    
    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void reduceProduct(long id,int subtract){   
         Optional<Product> productOpt = productRepository.findAll().stream()
                .filter(p -> p.getId()==(id))
                .findFirst();
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setQuantity(product.getQuantity() - subtract);
        }
    }


}
