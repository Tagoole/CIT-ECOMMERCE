package com.ecommerce.demo.features.UserProfile.dto;



//public record UserResponse(String username,String phoneNumber, String email) {
//}



public class UserResponse{
    private String username;
    private String phoneNumber;
    private String email;


    public UserResponse(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
