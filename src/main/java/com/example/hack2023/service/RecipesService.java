package com.example.hack2023.service;

import com.example.hack2023.helper.ListUtils;
import com.example.hack2023.model.Item;
import com.example.hack2023.model.Pool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class RecipesService {
    private final ItemService itemService;
    private final PoolService poolService;

    private final OpenAIService openAIService;


    @Autowired
    public RecipesService(ItemService itemService, PoolService poolService, OpenAIService openAIService) {
        this.itemService = itemService;
        this.poolService = poolService;
        this.openAIService = openAIService;
    }

    public List<RecipeDetails> suggest(String user) {
        List<String> myItems = itemService.findAll(user).stream().map(Item::getName).collect(Collectors.toList());
        List<String> poolItems = poolService.findAll().stream().map(Pool::getItemName).collect(Collectors.toList());
        //List<String> suggestedRecipes = openAIService.suggestRecipes(ListUtils.mergeAndRemoveDuplicates(myItems, poolItems));

        return openAIService.suggestRecipes2(ListUtils.mergeAndRemoveDuplicates(myItems, poolItems));
    }
}
