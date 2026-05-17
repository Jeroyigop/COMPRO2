package com.library;

import java.time.LocalDate;

public class PerishableProduct extends Product {

    private LocalDate expiryDate;

    public PerishableProduct(int id, String name,
                             double price, int stock,
                             LocalDate expiryDate) {

        super(id, name, price, stock);
        this.expiryDate = expiryDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }
}
