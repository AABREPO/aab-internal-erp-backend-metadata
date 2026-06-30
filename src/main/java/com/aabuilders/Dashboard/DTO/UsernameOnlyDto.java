package com.aabuilders.Dashboard.DTO;

public class UsernameOnlyDto {
    private String username;

    public UsernameOnlyDto() {
    }

    public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

