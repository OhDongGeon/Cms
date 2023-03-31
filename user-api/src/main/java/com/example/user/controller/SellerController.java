package com.example.user.controller;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.common.UserVo;
import com.example.user.domain.customer.CustomerDto;
import com.example.user.domain.customer.SellerDto;
import com.example.user.domain.model.Seller;
import com.example.user.exception.CustomException;
import com.example.user.exception.ErrorCode;
import com.example.user.service.seller.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/seller")
@RequiredArgsConstructor
public class SellerController {
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final SellerService sellerService;


    @GetMapping("/getInfo")
    public ResponseEntity<SellerDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
        UserVo vo = jwtAuthenticationProvider.getUserVo(token);
        Seller seller = sellerService.findByIdAndEmail(vo.getId(), vo.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        return ResponseEntity.ok(SellerDto.from(seller));
    }

}
