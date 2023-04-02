package com.example.order.domain.product;


import lombok.*;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProductForm {

    private String name;
    private String description;


    private List<AddProductItemForm> items;

}
