package com.bnp.kata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class PriceResult {

    private double price;
    private List<GroupDetails> groups;
}
