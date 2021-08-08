package ru.tronin.msproducts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(scanBasePackages = "ru.tronin")
public class MsProductsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsProductsApplication.class, args);
    }

}
