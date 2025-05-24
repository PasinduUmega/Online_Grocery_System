package com.example.demo.util;


import com.example.demo.model.Cart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CartFileUtils {
    public static List<Cart> readCartsFromFile(String filePath) {
        List<Cart> carts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    carts.add(new Cart(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public static void writeCartsToFile(String filePath, List<Cart> carts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Cart cart : carts) {
                writer.write(String.join(",",
                        cart.getId(),
                        cart.getUserId(),
                        cart.getItemId(),
                        String.valueOf(cart.getQuantity())
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

