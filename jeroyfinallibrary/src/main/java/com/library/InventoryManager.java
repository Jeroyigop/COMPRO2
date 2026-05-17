package com.library;

import java.util.ArrayList;

public class InventoryManager {

    private ArrayList<Product> products = new ArrayList<>();

    public synchronized void addProduct(Product product) {
        products.add(product);
    }

    public synchronized Product findProduct(int id) {
        for(Product p : products) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public synchronized ArrayList<Product> getProducts() {
        return products;
    }
}