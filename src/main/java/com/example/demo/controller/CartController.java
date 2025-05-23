package com.example.shoppingcartapp.controller;


import com.example.shoppingcartapp.model.CartItem;
import com.example.shoppingcartapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public String addToCart(@RequestBody CartItem item) {
        cartService.addToCart(item);
        return "Item added to cart";
    }

    @GetMapping
    public List<CartItem> getCart() {
        return new ArrayList<>(cartService.getCartItems());
    }

    @DeleteMapping("/remove")
    public String removeFromCart() {
        cartService.removeFromCart();
        return "Item removed from cart";
    }

    @DeleteMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "Cart cleared";
    }

    @PostMapping("/checkout")
    public String checkout() {
        cartService.checkout();
        return "Checkout complete!";
    }
}
