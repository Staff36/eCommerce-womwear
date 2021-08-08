package ru.tronin.mswarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.tronin")
public class MsWarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsWarehouseApplication.class, args);
    }

}
