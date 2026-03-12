package com.bnp.kata.controller;


import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.Books;
import com.bnp.kata.service.BookPricingService;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.jayway.jsonpath.JsonPath;
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

        mockMvc.perform(post("/api/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalPrice").value(320.0))
                .andExpect(jsonPath("$.totalBooks").value(8))
                .andExpect(jsonPath("$.books").isArray());
    }
}