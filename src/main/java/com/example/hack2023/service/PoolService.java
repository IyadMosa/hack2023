package com.example.hack2023.service;

import com.example.hack2023.model.Pool;
import com.example.hack2023.repository.PoolRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PoolService {

    private final PoolRepository repository;


    @Autowired
    public PoolService(PoolRepository repository) {
        this.repository = repository;
    }

    public void add(Pool item) throws Exception {
        repository.save(item);
    }

    public List<Pool> findAll() {
        ArrayList<Pool> items = Lists.newArrayList(repository.findAll());
        return items;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
