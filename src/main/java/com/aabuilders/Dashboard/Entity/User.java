package com.aabuilders.Dashboard.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(name = "user_image_url")
    private String userImageUrl;

    private String employeeId;
    private String position;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRoles> userRoles;

    private Long branchId;

    private Boolean emailVerified;

    private String userStatus = "ACTIVE";

    private Boolean superAdmin = false;

    private Boolean canCreateUsers = false;

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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
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

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified == null || emailVerified;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isActiveUser() {
        if (userStatus == null || userStatus.isBlank()) {
            return true;
        }
        String normalizedStatus = userStatus.trim().toUpperCase();
        return !normalizedStatus.equals("TERMINATED")
                && !normalizedStatus.equals("LEFT")
                && !normalizedStatus.equals("INACTIVE")
                && !normalizedStatus.equals("RESIGNED");
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public boolean isSuperAdmin() {
        return Boolean.TRUE.equals(superAdmin);
    }

    public Boolean getCanCreateUsers() {
        return canCreateUsers;
    }

    public void setCanCreateUsers(Boolean canCreateUsers) {
        this.canCreateUsers = canCreateUsers;
    }

    public boolean canCreateUsers() {
        return isSuperAdmin() || Boolean.TRUE.equals(canCreateUsers);
    }
}
