package com.code.tdd.controller;

import com.code.tdd.model.Books;
import com.code.tdd.service.BookPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    BookPricingService bookPricingService;

    @PostMapping("/basket")
    public double totalOrderDetails(@RequestBody Books books){


    double value =  bookPricingService.calculateBestPrice(books.getBookList());
    System.out.println("value is"+value);
    return value;

    }

}
