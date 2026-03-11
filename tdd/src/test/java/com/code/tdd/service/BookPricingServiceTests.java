package com.code.tdd.service;

import com.code.tdd.model.BookItems;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookPricingServiceTests {

    BookPricingService bookPricingService = new BookPricingService();

    @Test
    void singleBook() {
        List<BookItems> books = List.of(new BookItems("Clean Code", 1));
        double price = bookPricingService.calculateBestPrice(books);
        assertEquals(50, price);
    }

    @Test
    void twoDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1)
        );

        double price = bookPricingService.calculateBestPrice(books);

        assertEquals(95, price);
    }

    @Test
    void threeDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1),
                new BookItems("Clean Architecture", 1)
        );
        double price = bookPricingService.calculateBestPrice(books);

        assertEquals(135, price);
    }


    @Test
    void fourDifferentBooks() {

        List<BookItems> books = List.of(
                new BookItems("Clean Code", 1),
                new BookItems("Clean Coder", 1),
                new BookItems("Clean Architecture", 1),
                new BookItems("TDD", 1)
        );

        double price = bookPricingService.calculateBestPrice(books);

        assertEquals(160, price);
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

        double price = bookPricingService.calculateBestPrice(books);

        assertEquals(187.5, price);
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

        double price = bookPricingService.calculateBestPrice(books);

        assertEquals(320, price);
    }
}

