package com.code.tdd.service;

import com.code.tdd.model.BookItems;
import com.code.tdd.model.Books;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookPricingService {

    private static final double BOOK_PRICE = 50;

    private static final Map<Integer, Double> DISCOUNT = Map.of(
            1, 0.0,
            2, 0.05,
            3, 0.10,
            4, 0.20,
            5, 0.25
    );

    private final Map<String, Double> cache = new HashMap<>();

    public double calculateBestPrice(List<BookItems> items) {

        int[] quantities = items.stream().mapToInt(BookItems::getQuantity).toArray();
        return findMinPrice(quantities);
    }

    private double findMinPrice(int[] books) {

        String key = Arrays.toString(books);

        if (cache.containsKey(key))
            return cache.get(key);

        boolean empty = Arrays.stream(books).allMatch(q -> q == 0);
        if (empty)
            return 0;

        double minPrice = Double.MAX_VALUE;

        for (int size = 1; size <= books.length; size++) {

            int[] next = Arrays.copyOf(books, books.length);
            int count = 0;

            for (int i = 0; i < next.length && count < size; i++) {

                if (next[i] > 0) {
                    next[i]--;
                    count++;
                }
            }

            if (count == size) {

                double price = size * BOOK_PRICE * (1 - DISCOUNT.get(size));

                minPrice = Math.min(minPrice, price + findMinPrice(next));
            }
        }

        cache.put(key, minPrice);

        return minPrice;
    }


}
