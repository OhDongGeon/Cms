package com.example.user.application;


import com.example.user.client.MailgunClient;
import com.example.user.client.mailgun.SendMailForm;
import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import com.example.user.exception.CustomException;
import com.example.user.exception.ErrorCode;
import com.example.user.service.SignUpCustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;


    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }


    public String customerSignUp(SignUpForm signUpForm) {
        if (signUpCustomerService.isEmailExist(signUpForm.getEmail())) {
            throw new CustomException(ErrorCode.ALREADY_REGISTER_USER);
        } else {
            Customer customer = signUpCustomerService.singUp(signUpForm);
            LocalDateTime now = LocalDateTime.now();
            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("ohdonggeon0119@gmail.com") //보내는 사람
                    .to(signUpForm.getEmail()) // 받는사람
                    .subject("Hello! Please...") // 제목
                    .text(getVerificationEmailBody(signUpForm.getEmail(), signUpForm.getName(), code)) // 내용
                    .build();

            mailgunClient.sendEmail(sendMailForm);
            signUpCustomerService.changeCustomerValidateEmail(customer.getId(), code);

            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());
            return "회원가입에 성공하였습니다.";
        }
    }


    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }


    private String getVerificationEmailBody(String email, String name, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n")
                .append("http://localhost:8081/signup/verify/customer?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
