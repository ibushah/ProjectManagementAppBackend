package com.example.hubProject.Commons;

public class AuthToken {

    private String token;
    private String username;
    private String userType;

    public AuthToken(String token, String username, String userType) {
        this.token = token;
        this.username = username;
        this.userType = userType;
    }

    public AuthToken(){

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
