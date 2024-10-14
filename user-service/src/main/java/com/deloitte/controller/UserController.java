//package com.deloitte.controller;
//
//import com.deloitte.dto.UserResDto;
//import com.deloitte.service.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.deloitte.service.CamundaAuthService;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class UserController {
//    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
//
//    private final KeycloakService keycloakService;
//    private final CamundaAuthService authService;
//    private final UserService userService;
//
//    public UserController(KeycloakService keycloakService, CamundaAuthService authService, UserService userService) {
//        this.keycloakService = keycloakService;
//        this.authService = authService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/users")
//    public ResponseEntity<List<UserResDto>> getUsers(Principal principal) {
//        LOG.debug("REST request to get all Keycloak Users");
//
//        List<UserResDto> listUsers;
//        try {
//            // Fetch the access token
//            String accessToken = authService.getAccessToken();
//
//            listUsers = keycloakService.getUsers(accessToken, authService.getAuthServerUrl(), authService.getRealm());
//            return ResponseEntity.ok(listUsers); // Return the list wrapped in a ResponseEntity
//
//        } catch (Exception e) {
//            LOG.error("Error fetching users: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//}
