package com.deloitte.dto;

import lombok.Data;

@Data
public class UserResDto {

    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public UserResDto(String id, String username, String email, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Default constructor
    public UserResDto() {
    }

    // Parameterized constructor
    public UserResDto(String username, String email, String lastName, String firstName) {
        this.username = username;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "UserResDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
