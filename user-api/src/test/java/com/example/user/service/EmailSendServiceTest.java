package com.example.user.service;

import com.example.user.client.MailgunClient;
import com.example.user.client.mailgun.SendMailForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailSendServiceTest {
    @Autowired
    private MailgunClient mailgunClient;

    @Test
    void EmailTest() {
        SendMailForm form = SendMailForm.builder()
                .from("ohdonggeon0119@gmail.com") //보내는 사람
                .to("tot0119@naver.com") // 받는사람
                .subject("Test email from zerobase") // 제목
                .text("my text") // 내용
                .build();

        mailgunClient.sendEmail(form);
    }
}