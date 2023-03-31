package com.example.user.domain.customer;

import com.example.user.domain.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class SellerDto {
    private Long id;
    private String email;


    public static SellerDto from(Seller seller) {
        return new SellerDto(seller.getId(), seller.getEmail());
    }
}
