package com.bnp.kata.service;

import com.bnp.kata.model.BookItems;
import com.bnp.kata.model.GroupDetails;
import com.bnp.kata.model.OrderResponse;
import com.bnp.kata.model.PriceResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class BookPricingService {

    private static final double BOOK_PRICE = 50;

    private static final Map<Integer, Double> DISCOUNT =
            Map.of(1, 0.0, 2, 0.05, 3, 0.10, 4, 0.20, 5, 0.25);

    public OrderResponse calculateOrderDetails(List<BookItems> items) {

        int[] quantities = items.stream()
                .mapToInt(BookItems::getQuantity)
                .toArray();

        String[] titles = items.stream()
                .map(BookItems::getTitle)
                .toArray(String[]::new);

        PriceResult result = findBestPrice(quantities, titles);

        int totalBooks = Arrays.stream(quantities).sum();

        double totalPrice = totalBooks * BOOK_PRICE;

        return new OrderResponse(
                result.getGroups(),
                totalPrice,
                result.getPrice()
        );
    }

    private PriceResult findBestPrice(int[] books, String[] titles) {

        if (Arrays.stream(books).allMatch(q -> q == 0)) {
            return new PriceResult(0, new ArrayList<>());
        }

        return IntStream.rangeClosed(1, books.length)
                .mapToObj(size -> calculateGroupPrice(size, books, titles))
                .filter(Objects::nonNull)
                .min(Comparator.comparingDouble(PriceResult::getPrice))
                .orElse(new PriceResult(Double.MAX_VALUE, new ArrayList<>()));
    }

    private PriceResult calculateGroupPrice(int size, int[] books, String[] titles) {

        int[] next = Arrays.copyOf(books, books.length);

        List<Integer> selectedIndices = IntStream.range(0, next.length)
                .filter(i -> next[i] > 0)
                .limit(size)
                .boxed()
                .toList();
        if (selectedIndices.size() != size) {
            return null;
        }
        selectedIndices.forEach(i -> next[i]--);

        List<String> groupBooks = selectedIndices.stream().map(i -> titles[i]).toList();

        double groupPrice = size * BOOK_PRICE * (1 - DISCOUNT.get(size));

        PriceResult result = findBestPrice(next, titles);

        double total = groupPrice + result.getPrice();

        List<GroupDetails> groups = new ArrayList<>();

        groups.add(new GroupDetails(
                groupBooks,
                size,
                DISCOUNT.get(size) * 100,
                groupPrice
        ));

        groups.addAll(result.getGroups());

        return new PriceResult(total, groups);
    }


}
