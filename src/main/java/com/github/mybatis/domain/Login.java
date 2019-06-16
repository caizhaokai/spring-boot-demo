package com.github.mybatis.domain;

public class Login {
    @Override
    public String toString() {
        return "Login [username=" + username + ", password=" + password + "]";
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
