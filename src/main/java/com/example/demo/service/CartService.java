package com.example.shoppingcartapp.service;



import com.example.shoppingcartapp.model.CartItem;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class CartService {

    private final Queue<CartItem> cartQueue = new LinkedList<>();
    private final String cartFilePath = "data/cart.txt";

    public CartService() {
        loadCartFromFile();
    }

    public Queue<CartItem> getCartItems() {
        return cartQueue;
    }

    public void addToCart(CartItem item) {
        cartQueue.add(item);
        saveCartToFile();
    }

    public void removeFromCart() {
        cartQueue.poll();
        saveCartToFile();
    }

    public void clearCart() {
        cartQueue.clear();
        saveCartToFile();
    }

    public Queue<CartItem> checkout() {
        Queue<CartItem> checkedOutItems = new LinkedList<>(cartQueue);
        clearCart(); // also saves
        return checkedOutItems;
    }

    private void saveCartToFile() {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs(); // ✅ create data/ directory if missing
            }

            FileWriter writer = new FileWriter(cartFilePath);
            for (CartItem item : cartQueue) {
                writer.write(item.getName() + "," +
                        item.getCategory() + "," +
                        item.getQuantity() + "," +
                        item.getPrice() + "," +
                        item.getImagePath() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCartFromFile() {
        File file = new File(cartFilePath);
        if (!file.exists()) {
            System.out.println("Cart file not found, starting with empty cart.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    CartItem item = new CartItem();
                    item.setName(parts[0]);
                    item.setCategory(parts[1]);
                    item.setQuantity(Integer.parseInt(parts[2]));
                    item.setPrice(Double.parseDouble(parts[3]));
                    item.setImagePath(parts[4]);
                    cartQueue.add(item);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
