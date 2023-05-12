package com.example.hack2023;

import com.example.hack2023.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Hack2023Application {

    @Autowired
    private ItemService itemService;

    public static void main(String[] args) {
        SpringApplication.run(Hack2023Application.class, args);
    }

    @PostConstruct
    public void init() throws IOException {
        itemService.loadItems();
    }
}
