package com.bnp.kata.controller;

import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.Books;
import com.bnp.kata.model.OrderResponse;
import com.bnp.kata.service.BookPricingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {


    private final BookPricingService bookPricingService;

    public BookController(BookPricingService bookPricingService) {
        this.bookPricingService = bookPricingService;
    }

    @PostMapping("/calculatePrice")
    public ResponseEntity totalOrderDetails(@RequestBody Books books) {
        double totalPrice = bookPricingService.calculateBestPrice(books.getBookList());
        int totalQuantity = books.getBookList().stream().mapToInt(BookItems::getQuantity).sum();
        OrderResponse orderResponse = new OrderResponse(books.getBookList(),totalQuantity,totalPrice);
        return ResponseEntity.ok(orderResponse);

    }


}
