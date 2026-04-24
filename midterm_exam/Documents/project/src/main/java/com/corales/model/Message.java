package com.corales.model;

public class Message {

    public String type;
    public String text;
    public int move;

    public String username;
    public String password;

    public int score1;
    public int score2;

    public Message(String type) {
        this.type = type;
    }
}