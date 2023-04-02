package com.example.order.controller;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.order.domain.model.Product;
import com.example.order.domain.product.*;
import com.example.order.service.ProductItemService;
import com.example.order.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller/product")
@RequiredArgsConstructor
public class SellerProductController {
    private final ProductService productService;
    private final ProductItemService productItemService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    @PostMapping
    public ResponseEntity<ProductDto> addProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody AddProductForm form) {
        return ResponseEntity.ok(ProductDto.from(productService.addProduct(jwtAuthenticationProvider.getUserVo(token).getId(), form)));
    }

    @PostMapping("/item")
    public ResponseEntity<ProductDto> addProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                     @RequestBody AddProductItemForm form) {
        return ResponseEntity.ok(ProductDto.from(productItemService.addProductItem(jwtAuthenticationProvider.getUserVo(token).getId(), form)));
    }



    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                 @RequestBody UpdateProductForm form) {
        return ResponseEntity.ok(ProductDto.from(productService.updateProduct(jwtAuthenticationProvider.getUserVo(token).getId(), form)));

    }

    @PutMapping("/item")
    public ResponseEntity<ProductItemDto> updateProductItem(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                                     @RequestBody UpdateProductItemForm form) {
        return ResponseEntity.ok(ProductItemDto.from(productItemService.updateProductItem(jwtAuthenticationProvider.getUserVo(token).getId(), form)));
    }
}
