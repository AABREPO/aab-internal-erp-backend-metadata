package com.aabuilders.Dashboard.DTO;

public class AuthMessageDto {
    private String message;

    public AuthMessageDto() {
    }

    public AuthMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
