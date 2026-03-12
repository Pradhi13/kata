package com.bnp.kata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GroupDetails {

    private List<String> books;
    private  int groupSize;
    private double discountPercentage;
    private double afterdiscountPrice;

}
