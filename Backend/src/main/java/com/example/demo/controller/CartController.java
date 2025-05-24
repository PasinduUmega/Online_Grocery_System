package com.example.demo.controller;


import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public String addToCart(@RequestBody Cart cart) {
        cartService.addToCart(cart);
        return "Item added to cart";
    }

    @GetMapping("/user/{userId}")
    public List<Cart> getCartByUserId(@PathVariable String userId) {
        return cartService.getCartByUserId(userId);
    }

    @DeleteMapping("/{cartId}")
    public String removeFromCart(@PathVariable String cartId) {
        cartService.removeFromCart(cartId);
        return "Item removed from cart";
    }
    @DeleteMapping("/clear/{userId}")
    public String clearCartByUserId(@PathVariable String userId) {
        cartService.clearCartByUserId(userId);
        return "Cart cleared";
    }
}
