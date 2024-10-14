package com.deloitte.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${keycloak.urls.auth}/realms/${keycloak.realm}/protocol/openid-connect/token")
    private String tokenUrl;

    @Value("${keycloak.adminClientId}")
    private String clientId;

    @Value("${keycloak.adminClientSecret}")
    private String clientSecret;

    private String cachedToken;
    private long tokenExpirationTime;

    public String getToken() {
        long currentTime = System.currentTimeMillis();
        if (cachedToken == null || currentTime >= tokenExpirationTime) {
            // Retrieve a new token
            cachedToken = fetchToken();
            tokenExpirationTime = currentTime + (60 * 60 * 1000); // Cache for 1 hour
        }
        return cachedToken;
    }

    private String fetchToken() {
        RestTemplate restTemplate = new RestTemplate();

        // Create the form parameters
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("grant_type", "client_credentials");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the HttpEntity with the body and headers
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(tokenUrl, requestEntity, String.class);

        // Check the response status and handle accordingly
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            try {
                // Parse the JSON response to get the access token
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
                return jsonNode.get("access_token").asText(); // Extract and return the access token
            } catch (Exception e) {
                throw new RuntimeException("Error parsing token response", e);
            }
        } else {
            throw new RuntimeException("Failed to fetch token: " + responseEntity.getStatusCode());
        }
    }
}
