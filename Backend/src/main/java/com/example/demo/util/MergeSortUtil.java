package com.example.demo.util;

import com.example.demo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MergeSortUtil {
    public static List<Item> mergeSortByPrice(List<Item> items) {
        if (items.size() <= 1) return items;

        int mid = items.size() / 2;
        List<Item> left = mergeSortByPrice(new ArrayList<>(items.subList(0, mid)));
        List<Item> right = mergeSortByPrice(new ArrayList<>(items.subList(mid, items.size())));

        return mergeByPrice(left, right);
    }

    private static List<Item> mergeByPrice(List<Item> left, List<Item> right) {
        List<Item> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getPrice() <= right.get(j).getPrice()) {
                result.add(left.get(i++));
            } else {
                result.add(right.get(j++));
            }
        }
        while (i < left.size()) result.add(left.get(i++));
        while (j < right.size()) result.add(right.get(j++));

        return result;
    }
}
