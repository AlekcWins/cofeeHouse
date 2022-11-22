package ru.cofee.house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CoffeeHouseApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeHouseApiApplication.class, args);
    }

}
