package com.aabuilders.Dashboard.DTO;

import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Entity.UserRoles;

import java.util.List;

/**
 * Data Transfer Object for user responses.
 * Contains user information without sensitive data like password.
 */
public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String employeeId;
    private String position;
    private List<UserRoles> userRoles;

    /**
     * Default constructor.
     */
    public UserResponseDto() {
    }

    /**
     * Constructor from User entity.
     * 
     * @param user User entity (password will be excluded)
     */
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.employeeId = user.getEmployeeId();
        this.position = user.getPosition();
        this.userRoles = user.getUserRoles();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
} 