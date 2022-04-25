package ru.tronin.routinglib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "ru.tronin")
@EnableFeignClients
public class RoutingLibApplication {


    public static void main(String[] args) {
        SpringApplication.run(RoutingLibApplication.class, args);
    }

}
