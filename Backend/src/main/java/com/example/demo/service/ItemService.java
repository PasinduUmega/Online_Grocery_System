package com.example.demo.service;


import com.example.demo.model.Item;
import com.example.demo.model.User;
import com.example.demo.util.ItemFileUtils;
import com.example.demo.util.MergeSortUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemService {
    private static final String FILE_PATH = "data/items.txt";

    public List<Item> getAllItems() {
        return ItemFileUtils.readItemsFromFile(FILE_PATH);
    }

    public void addItem(Item item) {
        List<Item> items = getAllItems();

        // Generate new user ID
        String newId = "I" + UUID.randomUUID().toString().substring(0, 8);  // You can use UUID or timestamp too
        item.setId(newId);  // make sure User.java has setId()

        items.add(item);
        ItemFileUtils.writeItemsToFile(FILE_PATH, items);
    }

    public void updateItem(String id, Item updatedItem) {
        List<Item> items = getAllItems();
        for (int i = 0; i < items.size(); i++) {
            Item existingItem = items.get(i);
            if (existingItem.getId().equals(id)) {

                // Only update if value is not null
                if (updatedItem.getName() != null)
                    existingItem.setName(updatedItem.getName());

                if (updatedItem.getCategory() != null)
                    existingItem.setCategory(updatedItem.getCategory());

                if (updatedItem.getQuantity() != 0)
                    existingItem.setQuantity(updatedItem.getQuantity());

                if (updatedItem.getPrice() != 0)
                    existingItem.setPrice(updatedItem.getPrice());

                if (updatedItem.getImagePath() != null)
                    existingItem.setImagePath(updatedItem.getImagePath());

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
