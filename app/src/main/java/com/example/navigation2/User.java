package com.example.navigation2;

public class User {
    public String nameuser,email,profile;

    public User() {
    }

    public User(String nameuser,String email) {
        this.nameuser = nameuser;
        this.email=email;

    }
    public User(String nameuser,String email,String profile){
        this.nameuser = nameuser;
        this.email=email;
        this.profile=profile;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
