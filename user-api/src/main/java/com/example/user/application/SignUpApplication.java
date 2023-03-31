package com.example.user.application;


import com.example.user.client.MailgunClient;
import com.example.user.client.mailgun.SendMailForm;
import com.example.user.domain.SignUpForm;
import com.example.user.domain.model.Customer;
import com.example.user.domain.model.Seller;
import com.example.user.exception.CustomException;
import com.example.user.service.customer.SignUpCustomerService;
import com.example.user.service.seller.SignUpSellerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import static com.example.user.exception.ErrorCode.ALREADY_REGISTER_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class SignUpApplication {
    private final MailgunClient mailgunClient;
    private final SignUpCustomerService signUpCustomerService;
    private final SignUpSellerService signUpSellerService;


    public void customerVerify(String email, String code) {
        signUpCustomerService.verifyEmail(email, code);
    }

    public String customerSignUp(SignUpForm signUpForm) {
        if (signUpCustomerService.isEmailExist(signUpForm.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Customer customer = signUpCustomerService.singUp(signUpForm);
            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("ohdonggeon0119@gmail.com") //보내는 사람
                    .to(signUpForm.getEmail()) // 받는사람
                    .subject("Hello! Please...") // 제목
                    .text(getVerificationEmailBody(customer.getEmail(), customer.getName(), "customer", code)) // 내용
                    .build();

            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());
            signUpCustomerService.changeCustomerValidateEmail(customer.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }


    public void sellerVerify(String email, String code) {
        signUpSellerService.verifyEmail(email, code);
    }

    public String sellerSignUp(SignUpForm signUpForm) {
        if (signUpSellerService.isEmailExist(signUpForm.getEmail())) {
            throw new CustomException(ALREADY_REGISTER_USER);
        } else {
            Seller seller = signUpSellerService.signUp(signUpForm);
            String code = getRandomCode();

            SendMailForm sendMailForm = SendMailForm.builder()
                    .from("ohdonggeon0119@gmail.com") //보내는 사람
                    .to(signUpForm.getEmail()) // 받는사람
                    .subject("Hello! Please...") // 제목
                    .text(getVerificationEmailBody(seller.getEmail(), seller.getName(), "seller", code)) // 내용
                    .build();

            log.info("Send email result : " + mailgunClient.sendEmail(sendMailForm).getBody());
            signUpSellerService.changeSellerValidateEmail(seller.getId(), code);

            return "회원가입에 성공하였습니다.";
        }
    }


    private String getRandomCode() {
        return RandomStringUtils.random(10, true, true);
    }


    private String getVerificationEmailBody(String email, String name, String type, String code) {
        StringBuilder builder = new StringBuilder();
        return builder.append("Hello ").append(name).append("! Please Click Link for verification.\n")
                .append("http://localhost:8081/signup/" + type + "/verify/?email=")
                .append(email)
                .append("&code=")
                .append(code).toString();
    }
}
