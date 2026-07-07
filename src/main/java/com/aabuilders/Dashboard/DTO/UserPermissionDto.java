package com.aabuilders.Dashboard.DTO;

public class UserPermissionDto {
    private Boolean canCreateUsers;
    private Boolean superAdmin;

    public Boolean getCanCreateUsers() {
        return canCreateUsers;
    }

    public void setCanCreateUsers(Boolean canCreateUsers) {
        this.canCreateUsers = canCreateUsers;
    }

    public Boolean getSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}
