package com.bnp.kata.service;

import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.GroupDetails;
import com.bnp.kata.model.OrderResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPricingServiceTests {

    BookPricingService bookPricingService = new BookPricingService();



    @Test
    void singleBook() {
        List<BookItems> books = List.of(new BookItems("Clean Code", 1));
        OrderResponse response = bookPricingService.calculateOrderDetails(books);
        assertEquals(50, response.getDiscountedPrice());
    }

    @Test
    void twoDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1)
        );

        OrderResponse response = bookPricingService.calculateOrderDetails(books);

        assertEquals(95, response.getDiscountedPrice());
    }

    @Test
    void threeDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1),
                new BookItems("Clean Architecture", 1)
        );
        OrderResponse response = bookPricingService.calculateOrderDetails(books);

        assertEquals(135, response.getDiscountedPrice());
    }


    @Test
    void fourDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1),
                new BookItems("Clean Architecture", 1),
                new BookItems("TDD", 1)
        );

        OrderResponse response = bookPricingService.calculateOrderDetails(books);

        assertEquals(160, response.getDiscountedPrice());
    }

    @Test
    void fiveDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1),
                new BookItems("Clean Architecture", 1),
                new BookItems("TDD", 1),
                new BookItems("Legacy Code", 1)
        );

        OrderResponse response = bookPricingService.calculateOrderDetails(books);

        assertEquals(187.5, response.getDiscountedPrice());
    }

    @Test
    void complexBasket() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 2),
                new BookItems("Clean Coder", 2),
                new BookItems("Clean Architecture", 2),
                new BookItems("TDD", 1),
                new BookItems("Legacy Code", 1)
        );

        OrderResponse response = bookPricingService.calculateOrderDetails(books);

        assertEquals(320, response.getDiscountedPrice());
    }
}

