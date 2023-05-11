package com.example.hack2023.controller;

import com.example.hack2023.model.Pool;
import com.example.hack2023.service.PoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pool")
public class PoolController {

    private final PoolService service;

    public PoolController(PoolService service) {
        this.service = service;
    }


    @GetMapping("/all")
    public ResponseEntity<List<Pool>> getAll() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
