package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Order {
    private String id;
    private String userId;
    private List<String> itemIds; // List of item IDs in the order
    private List<Integer> quantities; // Corresponding quantities for each item
    private double totalAmount;
    private String status; // PENDING, PROCESSING, READY, COMPLETED, CANCELLED
    private LocalDateTime orderTime;
    private LocalDateTime estimatedTime;

    // Abstract method to be implemented by subclasses
    public abstract String getOrderType();

    // Abstract method for order-specific processing
    public abstract void processOrder();

    // Common method for calculating total (can be overridden)
    public void calculateTotal(List<Double> itemPrices) {
        this.totalAmount = 0.0;
        for (int i = 0; i < itemPrices.size() && i < quantities.size(); i++) {
            this.totalAmount += itemPrices.get(i) * quantities.get(i);
        }
    }

    // Method to update order status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Check if order is ready
    public boolean isReady() {
        return "READY".equals(this.status);
    }

    // Check if order is completed
    public boolean isCompleted() {
        return "COMPLETED".equals(this.status);
    }
}