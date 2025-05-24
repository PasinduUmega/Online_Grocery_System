package com.example.demo.util;


import com.example.demo.model.Item;

import java.util.List;

public class MergeSortUtil {

    public static List<Item> mergeSortByPrice(List<Item> items) {
        if (items.size() <= 1) {
            return items;
        }

        int mid = items.size() / 2;
        List<Item> left = mergeSortByPrice(items.subList(0, mid));
        List<Item> right = mergeSortByPrice(items.subList(mid, items.size()));

        return merge(left, right);
    }

    private static List<Item> merge(List<Item> left, List<Item> right) {
        List<Item> result = new java.util.ArrayList<>();
        int i = 0, j = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getPrice() <= right.get(j).getPrice()) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i++));
        }

        while (j < right.size()) {
            result.add(right.get(j++));
        }

        return result;
    }
}
