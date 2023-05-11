package com.example.hack2023.service;

import com.example.hack2023.model.Item;
import com.example.hack2023.model.Pool;
import com.example.hack2023.repository.ItemRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItemService {
    private final ItemRepository repository;
    private final PoolService poolService;


    @Autowired
    public ItemService(ItemRepository repository, PoolService poolService) {
        this.repository = repository;
        this.poolService = poolService;
    }

    public void add(Item item) throws Exception {
        repository.save(item);
    }

    public List<Item> findAll(String user) {
        ArrayList<Item> items = Lists.newArrayList(repository.findAll());
        if (user == null) {
            return items;
        }
        return items
                .stream()
                .filter(item -> user.equals(item.getUser()))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public void addToPool(Long id) throws Exception {
        Item item = repository.getById(id);
        repository.deleteById(id);
        Pool pool = new Pool();
        pool.setItemName(item.getName());
        poolService.add(pool);

    }
}
