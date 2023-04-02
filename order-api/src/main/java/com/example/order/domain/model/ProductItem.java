package com.example.order.domain.model;


import com.example.order.domain.product.AddProductForm;
import com.example.order.domain.product.AddProductItemForm;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Audited
@AuditOverride(forClass = BaseEntity.class)
public class ProductItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;
    @Audited
    private String name;
    @Audited
    private Integer price;
    private Integer count;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;


    public static ProductItem of(Long sellerId, AddProductItemForm addProductItemForm) {
        return ProductItem.builder()
                .sellerId(sellerId)
                .name(addProductItemForm.getName())
                .price(addProductItemForm.getPrice())
                .count(addProductItemForm.getCount())
                .build();
    }
}
