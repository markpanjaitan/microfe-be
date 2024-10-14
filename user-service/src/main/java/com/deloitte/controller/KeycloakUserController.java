package com.deloitte.controller;

import com.deloitte.dto.UserRegistrationRecord;
import com.deloitte.dto.UserResDto;
import com.deloitte.service.KeycloakUserService;
import lombok.AllArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class KeycloakUserController {

    private static final Logger log = LoggerFactory.getLogger(KeycloakUserController.class);

    private final KeycloakUserService keycloakUserService;

    @PostMapping
    public UserRegistrationRecord createUser(@RequestBody UserRegistrationRecord userRegistrationRecord) {
        return keycloakUserService.createUser(userRegistrationRecord);
    }

    @GetMapping("/me")  // Changed endpoint to differentiate
    public UserRepresentation getUserById(Principal principal) {
        return keycloakUserService.getUserById(principal.getName());
    }

//    @GetMapping
//    public ResponseEntity<List<UserResDto>> getUsers(@RequestHeader("Authorization") String authToken) {
//        try {
//            // Remove Principal; use the authToken directly
//            List<UserResDto> users = keycloakUserService.getUsers(authToken.replace("Bearer ", ""));
//            return ResponseEntity.ok(users);
//        } catch (Exception e) {
//            log.error("Error retrieving users", e);
//            return ResponseEntity.status(500).build(); // Return a 500 error in case of an exception
//        }
//    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResDto>> getUsers() {
        try {
            List<UserResDto> users = keycloakUserService.getUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            log.error("Error retrieving users", e);
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable String userId) {
        keycloakUserService.deleteUserById(userId);
    }

    @PutMapping("/{userId}/send-verify-email")
    public void sendVerificationEmail(@PathVariable String userId) {
        keycloakUserService.emailVerification(userId);
    }

    @PutMapping("/update-password")
    public void updatePassword(Principal principal) {
        keycloakUserService.updatePassword(principal.getName());
    }
}
