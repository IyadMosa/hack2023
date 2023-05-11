package com.example.hack2023.controller;

import com.example.hack2023.model.Item;
import com.example.hack2023.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<?> add(@RequestBody Item item) throws Exception {
        service.add(item);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/add-pool/{id}")
    public ResponseEntity<?> addToPool(@PathVariable Long id) throws Exception {
        service.addToPool(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/{user}")
    public ResponseEntity<List<Item>> getAll(@PathVariable String user) {
        return new ResponseEntity<>(service.findAll(user), HttpStatus.FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
