package com.example.demo.service;


import com.example.demo.model.Cart;
import com.example.demo.util.CartFileUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {
    private static final String FILE_PATH = "data/carts.txt";

    public void addToCart(Cart cart) {
        List<Cart> carts = CartFileUtils.readCartsFromFile(FILE_PATH);
        cart.setId("C" + UUID.randomUUID().toString().substring(0, 8));
        carts.add(cart);
        CartFileUtils.writeCartsToFile(FILE_PATH, carts);
    }

    public List<Cart> getCartByUserId(String userId) {
        List<Cart> allCarts = CartFileUtils.readCartsFromFile(FILE_PATH);
        List<Cart> userCarts = new ArrayList<>();
        for (Cart cart : allCarts) {
            if (cart.getUserId().equals(userId)) {
                userCarts.add(cart);
            }
        }
        return userCarts;
    }

    public void removeFromCart(String cartId) {
        List<Cart> carts = CartFileUtils.readCartsFromFile(FILE_PATH);
        carts.removeIf(cart -> cart.getId().equals(cartId));
        CartFileUtils.writeCartsToFile(FILE_PATH, carts);
    }
    public void clearCartByUserId(String userId) {
        List<Cart> carts = CartFileUtils.readCartsFromFile(FILE_PATH);
        carts.removeIf(cart -> cart.getUserId().equals(userId));
        CartFileUtils.writeCartsToFile(FILE_PATH, carts);
    }

}

