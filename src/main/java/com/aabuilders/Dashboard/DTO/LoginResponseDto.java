package com.aabuilders.Dashboard.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Entity.UserRoles;

import java.util.List;

/**
 * Data Transfer Object for login response.
 * Contains JWT token and user information without sensitive data like password.
 */
public class LoginResponseDto {
    @JsonProperty("access_token")
    private String token;
    // private Long id;
    // private String email;
    // private String username;
    // private String employeeId;
    // private String position;
    // private List<UserRoles> userRoles;

    /**
     * Default constructor.
     */
    public LoginResponseDto() {
    }

    /**
     * Constructor with all fields.
     * 
     * @param token JWT token
     * @param user User entity (password will be excluded)
     */
    public LoginResponseDto(String token, User user) {
        this.token = token;
        // this.id = user.getId();
        // this.email = user.getEmail();
        // this.username = user.getUsername();
        // this.employeeId = user.getEmployeeId();
        // this.position = user.getPosition();
        // this.userRoles = user.getUserRoles();
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }

    // public void setToken(String token) {
    //     this.token = token;
    // }

    // public Long getId() {
    //     return id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getUsername() {
    //     return username;
    // }

    // public void setUsername(String username) {
    //     this.username = username;
    // }

    // public String getEmployeeId() {
    //     return employeeId;
    // }

    // public void setEmployeeId(String employeeId) {
    //     this.employeeId = employeeId;
    // }

    // public String getPosition() {
    //     return position;
    // }

    // public void setPosition(String position) {
    //     this.position = position;
    // }

    // public List<UserRoles> getUserRoles() {
    //     return userRoles;
    // }

    // public void setUserRoles(List<UserRoles> userRoles) {
    //     this.userRoles = userRoles;
    // }
} 