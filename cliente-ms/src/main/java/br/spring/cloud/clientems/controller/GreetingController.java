package br.spring.cloud.clientems.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @Value("${greeting.message:Olá, mundo!}")
    private String message;

    @GetMapping("/greeting")
    public String greet() {
        return message;
    }
}
