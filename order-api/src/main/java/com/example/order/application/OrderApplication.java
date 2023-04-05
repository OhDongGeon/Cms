package com.example.order.application;


import com.example.order.client.UserClient;
import com.example.order.client.user.ChangeBalanceForm;
import com.example.order.client.user.CustomerDto;
import com.example.order.domain.model.ProductItem;
import com.example.order.domain.redis.Cart;
import com.example.order.exception.CustomException;
import com.example.order.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static com.example.order.exception.ErrorCode.ORDER_FAIL_CHECK_CART;
import static com.example.order.exception.ErrorCode.ORDER_FAIL_NO_MONEY;


@Service
@RequiredArgsConstructor
public class OrderApplication {
    private final CartApplication cartApplication;
    private final UserClient userClient;
    private final ProductItemService productItemService;


    @Transactional
    public void order(String token, Cart cart) {
        // 주문 시 기존 카트 버림
        Cart orderCart = cartApplication.refreshCart(cart);
        if (orderCart.getMessages().size() > 0) {
            throw new CustomException(ORDER_FAIL_CHECK_CART);
        }

        CustomerDto customerDto = userClient.getCustomerInfo(token).getBody();
        int totalPrice = getTotalPrice(cart);
        if (customerDto.getBalance() < getTotalPrice(cart)) {
            throw new CustomException(ORDER_FAIL_NO_MONEY);
        }

        userClient.changeBalance(token,
                ChangeBalanceForm.builder()
                        .from("USER")
                        .message("Oredr")
                        .money(-totalPrice)
                        .build());

        for (Cart.Product product : orderCart.getProducts()) {
            for (Cart.ProductItem cartItem : product.getItems()) {
                ProductItem productItem = productItemService.getProductItem(cartItem.getId());
                productItem.setCount(productItem.getCount() - cartItem.getCount());
            }
        }
    }


    private Integer getTotalPrice(Cart cart) {
        return cart.getProducts().stream().flatMapToInt(
                        product -> product.getItems().stream().flatMapToInt(
                                productItem -> IntStream.of(productItem.getPrice() * productItem.getCount())))
                .sum();

    }
}
