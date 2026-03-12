package com.bnp.kata.controller;


import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.Books;
import com.bnp.kata.model.GroupDetails;
import com.bnp.kata.model.OrderResponse;
import com.bnp.kata.service.BookPricingService;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
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

        request.setBooks(List.of(
                new BookItems("Clean Code",2),
                new BookItems("Clean Coder",2),
                new BookItems("Clean Architecture",2),
                new BookItems("TDD",1),
                new BookItems("Legacy Code",1)
        ));

        OrderResponse response = getOrderResponse();
        Mockito.when(bookPricingService.calculateOrderDetails(Mockito.anyList()))
                .thenReturn(response);

        mockMvc.perform(post("/api/books/calculatePrice")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groups").isArray())
                .andExpect(jsonPath("$.totalPrice").value(400.0))
                .andExpect(jsonPath("$.discountedPrice").value(320.0));
    }

    private static OrderResponse getOrderResponse() {
        List<String> books1 = Arrays.asList("Clean Code","Clean Coder","Clean Architecture","TDD","Legacy Code");
        List<String> books2 = Arrays.asList("Clean Coder","Clean Architecture","TDD","Legacy Code");

        GroupDetails groupDetails1 = new GroupDetails(books1,4,20,160);
        GroupDetails groupDetails2 = new GroupDetails(books2,4,20,160);

        List<GroupDetails> groupDetailsList = new ArrayList<>();
        groupDetailsList.add(groupDetails1);
        groupDetailsList.add(groupDetails2);
        OrderResponse response = new OrderResponse(groupDetailsList,
                400.0,320.0
        );
        return response;
    }
}