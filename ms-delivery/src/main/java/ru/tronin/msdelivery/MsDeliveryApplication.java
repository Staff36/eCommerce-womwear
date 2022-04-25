package ru.tronin.msdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.tronin")
public class MsDeliveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsDeliveryApplication.class, args);
    }

}
