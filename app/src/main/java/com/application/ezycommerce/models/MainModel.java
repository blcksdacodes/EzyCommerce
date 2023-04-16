package com.application.ezycommerce.models;

import java.util.List;

public class MainModel {
    int statusCode;
    List<BookModel> products;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<BookModel> getProducts() {
        return products;
    }

    public void setProducts(List<BookModel> products) {
        this.products = products;
    }
}
