package com.bnp.kata.model;

import java.util.List;

public class OrderResponse {

    private List<BookItems> books;
    private int totalBooks;
    private double finalPrice;

    public OrderResponse(List<BookItems> books, int totalBooks, double finalPrice) {
        this.books = books;
        this.totalBooks = totalBooks;
        this.finalPrice = finalPrice;
    }

    public List<BookItems> getBooks() {
        return books;
    }

    public void setBooks(List<BookItems> books) {
        this.books = books;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }
}
