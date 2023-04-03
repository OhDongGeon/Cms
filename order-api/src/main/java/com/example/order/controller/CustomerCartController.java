package com.example.order.controller;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.order.application.CartApplication;
import com.example.order.domain.product.AddProductCartForm;
import com.example.order.domain.redis.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
@RequiredArgsConstructor
public class CustomerCartController {
    private final CartApplication cartApplication;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    @PostMapping
    public ResponseEntity<Cart> addCart(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                                        @RequestBody AddProductCartForm form) {
        return ResponseEntity.ok(cartApplication.addCart(jwtAuthenticationProvider.getUserVo(token).getId(), form));
    }
}