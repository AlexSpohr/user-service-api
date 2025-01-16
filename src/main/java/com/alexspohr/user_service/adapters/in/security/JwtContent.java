package com.alexspohr.user_service.adapters.in.security;

public class JwtContent {
    private String name;
    private String email;

    public JwtContent(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
