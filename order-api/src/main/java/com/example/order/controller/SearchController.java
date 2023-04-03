package com.example.order.controller;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.order.domain.product.ProductDto;
import com.example.order.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/product")
@RequiredArgsConstructor
public class SearchController {
    private final ProductSearchService productSearchService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(
                productSearchService.searchByName(name).stream()
                        .map(ProductDto::from).collect(Collectors.toList())
        );
    }


    @GetMapping("/detail")
    public ResponseEntity<ProductDto> getDetail(@RequestParam Long productId) {
        return ResponseEntity.ok(
                ProductDto.from( productSearchService.getByProductId(productId))
        );
    }
}
