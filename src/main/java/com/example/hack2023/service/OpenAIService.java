package com.example.hack2023.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private final String apiUrl = "https://api.openai.com/v1/engines/text-davinci-003/completions";

    private final String apiKey = "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";

    public List<String> suggestRecipes(List<String> foodItems) {
        RestTemplate restTemplate = new RestTemplate();

        String prompt = "suggest recipes for " + foodItems.stream().map(String::toLowerCase).collect(Collectors.joining(", ")) + "\n\n1. Title and description format: {title} : {description}";


        String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 64}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(apiUrl, request, String.class);

        List<String> suggestedRecipes = parseResponse(response);

        return suggestedRecipes;
    }

    private List<String> parseResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;

        try {
            rootNode = objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing response from OpenAI API", e);
        }

        JsonNode choicesNode = rootNode.path("choices");

        if (choicesNode.size() == 0) {
            throw new RuntimeException("No choices returned from OpenAI API");
        }

        JsonNode textNode = choicesNode.get(0).path("text");

        String text = textNode.asText().trim();

        String[] lines = text.split("\n\n");

        List<String> suggestedRecipes = List.of(lines);

        return suggestedRecipes;
    }


    public List<RecipeDetails> suggestRecipes2(List<String> foodItems) {
        String prompt = "suggest recipes for " + foodItems.stream().map(String::toLowerCase).collect(Collectors.joining(", ")) + "\n\n1. Title and description format: {title} : {description}";

        String url = "https://api.openai.com/v1/engines/text-davinci-003/completions";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        JSONObject json = new JSONObject();
        json.put("prompt", prompt);
        json.put("max_tokens", 64);
        json.put("temperature", 0.7);
        json.put("n", 1);

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        String responseBody = response.getBody();
        JSONObject jsonObject = new JSONObject(responseBody);

        String recipeList = jsonObject.getJSONArray("choices").getJSONObject(0).getString("text");
        List<RecipeDetails> recipes = Arrays.stream(recipeList.split("\n"))
                .map(s -> s.split(":"))
                .filter(arr -> arr.length == 2)
                .map(arr -> new RecipeDetails(arr[0].trim(), arr[1].trim()))
                .collect(Collectors.toList());

        return recipes;
    }


}
