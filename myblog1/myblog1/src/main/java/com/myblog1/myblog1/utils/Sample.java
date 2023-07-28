package com.myblog1.myblog1.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sample {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("wasim"));
    }
}
