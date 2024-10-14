//package com.deloitte.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class CamundaAuthService {
//
//    @Value("${camunda.auth.token-url}")
//    private String tokenUrl;
//
//    @Value("${camunda.auth.client-id}")
//    private String clientId;
//
//    @Value("${camunda.auth.client-secret}")
//    private String clientSecret;
//
//    @Value("${camunda.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${camunda.realm}")
//    private String realm;
//
//    public String getRealm() {
//        return realm;
//    }
//
//    public void setRealm(String realm) {
//        this.realm = realm;
//    }
//
//    public String getAuthServerUrl() {
//        return authServerUrl;
//    }
//
//    public void setAuthServerUrl(String authServerUrl) {
//        this.authServerUrl = authServerUrl;
//    }
//
//    public String getTokenUrl() {
//        return tokenUrl;
//    }
//
//    public void setTokenUrl(String tokenUrl) {
//        this.tokenUrl = tokenUrl;
//    }
//
//    public String getAccessToken() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("client_id", clientId);
//        body.add("client_secret", clientSecret);
//        body.add("grant_type", "client_credentials");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);
//
//        // Parse the response body to extract the access token
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response.getBody());
//            return jsonNode.get("access_token").asText();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse access token from response", e);
//        }
//    }
//
//    public String getAccessToken(String username, String password) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("client_id", clientId);
//        body.add("client_secret", clientSecret);
//        body.add("grant_type", "password");
//        body.add("username", username);
//        body.add("password", password);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);
//
//        // Parse the response body to extract the access token
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode jsonNode = objectMapper.readTree(response.getBody());
//            return jsonNode.get("access_token").asText();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to parse access token from response", e);
//        }
//    }
//}
