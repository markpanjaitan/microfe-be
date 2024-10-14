package com.deloitte.service;

import com.deloitte.dto.UserRegistrationRecord;
import com.deloitte.dto.UserResDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.urls.users}")
    private String usersUrl;
    @Value("${keycloak.realm}")
    private String realm;
    private Keycloak keycloak;
    private final RestTemplate restTemplate;

    public KeycloakUserServiceImpl(Keycloak keycloak, RestTemplate restTemplate) {
        this.keycloak = keycloak;
        this.restTemplate = restTemplate;
    }

    @Override
    public UserRegistrationRecord createUser(UserRegistrationRecord userRegistrationRecord) {

        UserRepresentation user=new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(userRegistrationRecord.username());
        user.setEmail(userRegistrationRecord.email());
        user.setFirstName(userRegistrationRecord.firstName());
        user.setLastName(userRegistrationRecord.lastName());
        user.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation=new CredentialRepresentation();
        credentialRepresentation.setValue(userRegistrationRecord.password());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(credentialRepresentation);
        user.setCredentials(list);

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(user);

        if(Objects.equals(201,response.getStatus())){

            List<UserRepresentation> representationList = usersResource.searchByUsername(userRegistrationRecord.username(), true);
            if(!CollectionUtils.isEmpty(representationList)){
                UserRepresentation userRepresentation1 = representationList.stream().filter(userRepresentation -> Objects.equals(false, userRepresentation.isEmailVerified())).findFirst().orElse(null);
                assert userRepresentation1 != null;
                emailVerification(userRepresentation1.getId());
            }
            return  userRegistrationRecord;
        }

//        response.readEntity()

        return null;
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {

        return  getUsersResource().get(userId).toRepresentation();
    }

    public List<UserResDto> getUsers(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(usersUrl, HttpMethod.GET, entity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<UserRepresentation> userRepresentations = objectMapper.readValue(responseEntity.getBody(), new TypeReference<List<UserRepresentation>>() {
                });
                List<UserResDto> userResDtos = new ArrayList<>();

                for (UserRepresentation user : userRepresentations) {
                    UserResDto userResDto = new UserResDto();
                    userResDto.setId(user.getId());
                    userResDto.setUsername(user.getUsername());
                    userResDto.setEmail(user.getEmail());
                    userResDto.setFirstName(user.getFirstName());
                    userResDto.setLastName(user.getLastName());
                    userResDtos.add(userResDto);
                }

                return userResDtos;  // Return the list directly
            } catch (Exception e) {
                log.error("Error parsing user response", e);
                throw new RuntimeException("Error parsing user response", e); // Throw an exception to handle it in the controller
            }
        } else {
            throw new RuntimeException("Failed to fetch users, status code: " + responseEntity.getStatusCode()); // Handle unsuccessful response
        }
    }

    @Override
    public void deleteUserById(String userId) {

        getUsersResource().delete(userId);
    }


    @Override
    public void emailVerification(String userId){

        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void updatePassword(String userId) {

        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);

    }

//    public List<User> getUsers() {
//        ResponseEntity<User[]> response = keycloakRestTemplate.getForEntity(keycloakUserListEndpoint, User[].class);
//        return Arrays.asList(response.getBody());
//    }
}
