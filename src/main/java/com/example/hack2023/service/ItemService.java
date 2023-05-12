package com.example.hack2023.service;

import com.example.hack2023.model.Item;
import com.example.hack2023.model.Pool;
import com.example.hack2023.repository.ItemRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
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


    public void loadItems() throws IOException {
        // read the JSON file from the classpath
        Resource resource = new ClassPathResource("data/stock.json");
        InputStream inputStream = resource.getInputStream();

        // convert the JSON file to a list of Item objects
        ObjectMapper objectMapper = new ObjectMapper();
        List<Item> items = objectMapper.readValue(inputStream, new TypeReference<List<Item>>() {
        });

        // save the items to the database
        repository.saveAll(items);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updateExpiringNextDayById(Long id, boolean expiringNextDay) {
        Query query = entityManager.createQuery("UPDATE Item i SET i.expiringNextDay = :expiringNextDay WHERE i.id = :id");
        query.setParameter("expiringNextDay", expiringNextDay);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
