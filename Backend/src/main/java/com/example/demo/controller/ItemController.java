package com.example.demo.controller;

import com.example.demo.model.Item;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    public ResponseEntity<String> addItem(@RequestBody Item item) {
        itemService.addItem(item);
        return ResponseEntity.ok("Item added successfully");
    }

    @PutMapping("/{id}")
    public void updateItem(@PathVariable String id, @RequestBody Item item) {
        itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
    }

    @GetMapping("/sort/price")
    public List<Item> sortByPrice() {
        return itemService.getItemsSortedByPrice();
    }
}
