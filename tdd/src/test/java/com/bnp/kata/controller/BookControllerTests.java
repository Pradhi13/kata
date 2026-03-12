package com.bnp.kata.controller;


import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.Books;
import com.bnp.kata.service.BookPricingService;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookPricingService bookPricingService;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldReturnBasketPrice() throws Exception {

        Books request = new Books();

        request.setBookList(List.of(
                new BookItems("Clean Code",2),
                new BookItems("Clean Coder",2),
                new BookItems("Clean Architecture",2),
                new BookItems("TDD",1),
                new BookItems("Legacy Code",1)
        ));

        Mockito.when(bookPricingService.calculateBestPrice(Mockito.anyList()))
                .thenReturn(320.0);

        mockMvc.perform(post("/api/books/basket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("320.0"));
    }
}