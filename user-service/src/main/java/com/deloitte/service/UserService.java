//package com.deloitte.service;
//
//import com.deloitte.security.AuthoritiesConstants;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Service;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//
//    public List<String> extractRoles(Principal principal) {
//        if (principal instanceof JwtAuthenticationToken) {
//            JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) principal;
//
//            // Access the JWT claims
//            Map<String, Object> claims = jwtToken.getToken().getClaims();
//
//            // Get the roles from realm_access
//            if (claims.containsKey("realm_access")) {
//                Map<String, Object> realmAccess = (Map<String, Object>) claims.get("realm_access");
//                if (realmAccess.containsKey("roles")) {
//                    List<String> roles =
//                            ((List<String>) realmAccess.get("roles")).stream()
//                                    .distinct() // Remove duplicates if needed
//                                    .collect(Collectors.toList());
//
//                    // Normalize the roles
//                    return normalizeRoles(roles);
//                }
//            }
//        }
//        return Collections.emptyList();
//    }
//
//    public List<String> normalizeRoles(List<String> roles) {
//        List<String> normalized = new ArrayList<>();
//        for (String role : roles) {
//            switch (role) {
//                case AuthoritiesConstants.ASSIGNEE:
//                    normalized.add("assignee");
//                    break;
//                case AuthoritiesConstants.APPROVER:
//                    normalized.add("approver");
//                    break;
//                case AuthoritiesConstants.USER:
//                    normalized.add("user");
//                    break;
//                case AuthoritiesConstants.ADMIN:
//                    normalized.add("admin");
//                    break;
//                // Add more mappings if necessary
//                default:
//                    normalized.add(role); // Add the role as-is if no mapping exists
//            }
//        }
//        return normalized;
//    }
//}
