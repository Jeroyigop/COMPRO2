package com.corales.phonebook.models;

public class Contact {
    private String name, email;
    private String phonenumber;

    


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber(){
        return phonenumber;
    }

    public Contact(String name, String email, String phonenumber) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String toCsvString(){
        return String.format("%s,%s,%s", name, email, phonenumber);
    }

}

