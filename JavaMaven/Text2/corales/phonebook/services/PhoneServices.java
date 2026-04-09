package com.corales.phonebook.services;

import com.corales.phonebook.models.Contact;

import java.util.HashMap;

import java.util.Map;

public class PhoneServices {

    private final Map<String, Contact> contacts = new HashMap<>();

    public void addContact(Contact c) {
        contacts.put(c.getName(), c);
    }

    public Contact searchContact(String name) {
        return contacts.get(name);
    }

    public Contact removeContact(String name) {
        return contacts.remove(name);
    }

}

