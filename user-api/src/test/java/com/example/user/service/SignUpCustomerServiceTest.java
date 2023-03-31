package com.example.user.service;

import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;


@SpringBootTest
class SignUpCustomerServiceTest {

    @Autowired
    private SignUpCustomerService service;

    @Test
    void singUp() {
        SignUpForm form = SignUpForm.builder()
                .name("name")
                .password("password")
                .birth(LocalDate.now())
                .email("tot0119@naver.com")
                .phone("0100000000")
                .build();

        Customer customer = service.singUp(form);
        Assert.notNull(customer.getId());
        Assert.notNull(customer.getPassword());
        Assert.notNull(customer.getBirth());
        Assert.notNull(customer.getEmail());
        Assert.notNull(customer.getPhone());
    }
}