package com.example.order.service;


import com.example.order.domain.model.Product;
import com.example.order.domain.product.AddProductForm;
import com.example.order.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    @Transactional
    public Product addProduct(Long sellerId, AddProductForm addProductForm) {
        return productRepository.save(Product.of(sellerId, addProductForm));
    }
}
