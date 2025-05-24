package com.example.demo.util;


import com.example.demo.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ItemFileUtils {
    public static List<Item> readItemsFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String imagePath = parts.length == 6 ? parts[5] : null;
                    Item item = new Item(
                            parts[0],
                            parts[1],
                            parts[2],
                            Double.parseDouble(parts[3]),
                            Integer.parseInt(parts[4]),
                            imagePath
                    );
                    items.add(item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void writeItemsToFile(String filePath, List<Item> items) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Item item : items) {
                writer.write(String.join(",",
                        item.getId(),
                        item.getName(),
                        item.getCategory(),
                        String.valueOf(item.getPrice()),
                        String.valueOf(item.getQuantity()),
                        item.getImagePath() != null ? item.getImagePath() : ""
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
