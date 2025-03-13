package com.alexspohr.user_service.core.domain.models;

public class UserModel {
    private String id;
    private String fullName;
    private String email;
    private String givenName;
    private String familyName;
    private String pictureUrl;

    public UserModel(String id, String fullName, String email, String givenName, String familyName,
                     String pictureUrl) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.givenName = givenName;
        this.familyName = familyName;
        this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String fullName;
        private String email;
        private String givenName;
        private String familyName;
        private String pictureUrl;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder givenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder pictureUrl(String pictureUrl) {
            this.pictureUrl = pictureUrl;
            return this;
        }

        public UserModel build() {
            return new UserModel(id, fullName, email, givenName, familyName, pictureUrl);
        }
    }
}

