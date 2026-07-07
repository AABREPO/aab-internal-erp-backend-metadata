package com.aabuilders.Dashboard.DTO;

import com.aabuilders.Dashboard.Entity.User;
import com.aabuilders.Dashboard.Entity.UserRoles;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String email;
    private String username;
    private String userImageUrl;
    private String employeeId;
    private String position;
    private List<UserRoles> userRoles;
    private Long branchId;
    private boolean emailVerified;
    private String userStatus;
    private boolean superAdmin;
    private boolean canCreateUsers;

    public UserResponseDto() {
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.userImageUrl = user.getUserImageUrl();
        this.employeeId = user.getEmployeeId();
        this.position = user.getPosition();
        this.userRoles = user.getUserRoles();
        this.branchId = user.getBranchId();
        this.emailVerified = user.isEmailVerified();
        this.userStatus = user.getUserStatus();
        this.superAdmin = user.isSuperAdmin();
        this.canCreateUsers = user.canCreateUsers();
    }

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

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public boolean isCanCreateUsers() {
        return canCreateUsers;
    }

    public void setCanCreateUsers(boolean canCreateUsers) {
        this.canCreateUsers = canCreateUsers;
    }
}
