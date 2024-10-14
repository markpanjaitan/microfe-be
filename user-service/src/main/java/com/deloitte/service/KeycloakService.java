//package com.deloitte.service;
//
//import com.deloitte.dto.UserResDto;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//@Service
//public class KeycloakService {
//
//    @Value("${camunda.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${camunda.realm}")
//    private String realm;
//
//    @Value("${camunda.auth.client-id}")
//    private String clientId;
//
//    @Value("${camunda.auth.client-secret}")
//    private String clientSecret;
//
//    private final RestTemplate restTemplate;
//
//    public KeycloakService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String getAccessToken() {
//        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
//
//        // Prepare the request for access token
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("grant_type", "client_credentials");
//
//        // Specify the generic type explicitly for the ResponseEntity
//        var responseType = new ParameterizedTypeReference<Map<String, Object>>() {};
//        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, new HttpEntity<>(params), responseType);
//        return (String) Objects.requireNonNull(response.getBody()).get("access_token");
//    }
//
//    public List<UserResDto> getUsers(String accessToken, String authServerUrl, String realm) {
//        List<UserResDto> listUsers = new ArrayList<>();
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Set up the headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken); // Set the bearer token
//        HttpEntity<String> entity = new HttpEntity<>(headers); // Create the HttpEntity with headers
//
//        // Construct the URL for fetching users
//        String usersUrl = authServerUrl + "/admin/realms/" + realm + "/users";
//
//        // Make the GET request and retrieve the response
//        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
//                usersUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//                });
//
//        // Process the response
//        List<Map<String, Object>> usersData = response.getBody();
//        if (usersData != null) {
//            for (Map<String, Object> userMap : usersData) {
//                UserResDto user = new UserResDto();
////                user.setId((String) userMap.get("id"));
//                user.setUsername((String) userMap.get("username"));
//                user.setEmail((String) userMap.get("email"));
//                // Set other fields from the userMap as needed
//
//                listUsers.add(user);
//            }
//        }
//        return listUsers;
//    }
//
//}
