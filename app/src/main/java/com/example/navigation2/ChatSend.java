package com.example.navigation2;


public class ChatSend {
    String name,mail,msage;
public ChatSend(){}
    public ChatSend(String name, String mail, String msage) {
        this.name = name;
        this.mail = mail;
        this.msage = msage;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getMsage() {
        return msage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMsage(String msage) {
        this.msage = msage;
    }
}
