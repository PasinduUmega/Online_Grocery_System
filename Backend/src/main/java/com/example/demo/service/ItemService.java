package com.example.demo.service;


import com.example.demo.model.Item;
import com.example.demo.util.ItemFileUtils;
import com.example.demo.util.MergeSortUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private static final String FILE_PATH = "data/items.txt";

    public List<Item> getAllItems() {
        return ItemFileUtils.readItemsFromFile(FILE_PATH);
    }

    public void addItem(Item item) {
        List<Item> items = getAllItems();
        items.add(item);
        ItemFileUtils.writeItemsToFile(FILE_PATH, items);
    }

    public void updateItem(String id, Item updatedItem) {
        List<Item> items = getAllItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                items.set(i, updatedItem);
                break;
            }
        }
        ItemFileUtils.writeItemsToFile(FILE_PATH, items);
    }

    public void deleteItem(String id) {
        List<Item> items = getAllItems();
        items.removeIf(item -> item.getId().equals(id));
        ItemFileUtils.writeItemsToFile(FILE_PATH, items);
    }

    public List<Item> getItemsSortedByPrice() {
        List<Item> items = getAllItems();
        return MergeSortUtil.mergeSortByPrice(items);
    }
}
