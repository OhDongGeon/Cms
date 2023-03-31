package com.example.user.application;


import com.example.domain.config.JwtAuthenticationProvider;
import com.example.domain.domain.common.UserType;
import com.example.user.domain.SignInForm;
import com.example.user.domain.model.Customer;
import com.example.user.exception.CustomException;
import com.example.user.exception.ErrorCode;
import com.example.user.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {
    private final CustomerService customerService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;


    public String customerLoginToken(SignInForm signInForm) {
        // 로그인 가능 여부
        Customer customer = customerService.findValidCustomer(signInForm.getEmail(), signInForm.getPassword())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_CHECK_FAIL));
        // 토큰 발행

        // 토큰 Response
        return jwtAuthenticationProvider.createToken(customer.getEmail(), customer.getId(), UserType.CUSTOMER);
    }
}
