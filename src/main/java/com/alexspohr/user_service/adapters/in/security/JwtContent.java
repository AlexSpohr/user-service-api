package com.alexspohr.user_service.adapters.in.security;

public class JwtContent {
    private String email;
    private String givenName;
    private String familyName;
    private String fullName;
    private String pictureUrl;

    public JwtContent(String email,
                      String givenName,
                      String familyName,
                      String fullName,
                      String pictureUrl) {
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.fullName = fullName;
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
