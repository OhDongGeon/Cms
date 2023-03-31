package com.example.domain.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Aes256utilTest {

    @Test
    void encrypt() {
        String encrypt = Aes256util.encrypt("Hello world");
        assertEquals(Aes256util.decrypt(encrypt), "Hello world");
    }
}