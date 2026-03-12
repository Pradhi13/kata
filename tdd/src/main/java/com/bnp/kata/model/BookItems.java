package com.bnp.kata.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookItems {

    private String title;
    private int quantity;

    public BookItems(String title, int quantity) {
        this.title = title;
        this.quantity = quantity;
    }
}
