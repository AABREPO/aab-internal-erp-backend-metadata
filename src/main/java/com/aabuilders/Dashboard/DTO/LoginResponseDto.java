package com.aabuilders.Dashboard.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.aabuilders.Dashboard.Entity.User;

public class LoginResponseDto {
    @JsonProperty("access_token")
    private String token;
    private UserResponseDto user;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token, User user) {
        this.token = token;
        this.user = new UserResponseDto(user);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }
}
