package com.example.navigation2;

public class SeenbyData {
    String email,name;
    public SeenbyData(){

    }

    public SeenbyData(String email, String name) {

        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
