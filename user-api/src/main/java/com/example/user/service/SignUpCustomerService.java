package com.example.user.service;

import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import com.example.user.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class SignUpCustomerService {

    private final CustomerRepository customerRepository;

    public Customer singUp(SignUpForm form) {
        return customerRepository.save(Customer.from(form));
    }


    public boolean isEmailExist(String email) {
        return customerRepository.findByEmail(email.toLowerCase(Locale.ROOT))
                .isPresent();
    }
}
