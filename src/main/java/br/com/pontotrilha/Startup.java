package br.com.pontotrilha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class Startup {

    /*@PostConstruct
    public void setup(){
        Stripe.apiKey = "";
    }*/

    public static void main(String[] args) {
        SpringApplication.run(Startup.class, args);
    }
}