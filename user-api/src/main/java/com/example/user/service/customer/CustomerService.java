package com.example.user.service.customer;


import com.example.user.domain.model.Customer;
import com.example.user.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;


    public  Optional<Customer> findByIdAndEmail(Long id, String email) {
        return customerRepository.findById(id)
                .stream().filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }


    public Optional<Customer> findValidCustomer(String email, String password) {
        // 이메일을 찾아서 비밀번호 검사
        return customerRepository.findByEmail(email).stream()
                .filter(customer -> customer.getPassword().equals(password) && customer.isVerify())
                .findFirst();
    }
}
