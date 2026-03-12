package com.bnp.kata.service;

import com.bnp.kata.model.BookItems;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class BookPricingService {

    private static final double BOOK_PRICE = 50;

    private static final Map<Integer, Double> DISCOUNT =
            Map.of(1, 0.0, 2, 0.05, 3, 0.10, 4, 0.20, 5, 0.25);

    private final Map<String, Double> cache = new HashMap<>();

    public double calculateBestPrice(List<BookItems> items) {

        int[] quantities = items.stream()
                .mapToInt(BookItems::getQuantity)
                .toArray();

        return findMinPrice(quantities);
    }

    private double findMinPrice(int[] books) {

        String key = Arrays.toString(books);

        if (cache.containsKey(key))
            return cache.get(key);

        if (Arrays.stream(books).allMatch(q -> q == 0))
            return 0;

        double minPrice = IntStream.rangeClosed(1, books.length)
                .mapToDouble(size -> calculateSetPrice(size, books))
                .filter(price -> price > 0)
                .min()
                .orElse(Double.MAX_VALUE);

        cache.put(key, minPrice);

        return minPrice;
    }

    private double calculateSetPrice(int size, int[] books) {

        int[] next = Arrays.copyOf(books, books.length);

        int count = 0;

        for (int i = 0; i < next.length && count < size; i++) {

            if (next[i] > 0) {
                next[i]--;
                count++;
            }
        }

        if (count != size)
            return 0;

        double price = size * BOOK_PRICE * (1 - DISCOUNT.get(size));

        return price + findMinPrice(next);
    }


}
