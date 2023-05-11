package com.example.hack2023.repository;

import com.example.hack2023.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item getById(Long id);
}
