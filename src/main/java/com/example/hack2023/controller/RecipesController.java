package com.example.hack2023.controller;

import com.example.hack2023.service.RecipeDetails;
import com.example.hack2023.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipesController {

    private final RecipesService service;

    public RecipesController(RecipesService service) {
        this.service = service;
    }


    @GetMapping("/{user}")
    public ResponseEntity<List<RecipeDetails>> suggest(@PathVariable String user) {
        return new ResponseEntity<>(service.suggest(user), HttpStatus.FOUND);
    }


}
