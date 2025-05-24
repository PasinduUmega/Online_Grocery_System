package com.example.demo.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PickUpOrder extends Order {
    private String customerName;
    private String phoneNumber;
    private String pickupLocation;
    private LocalDateTime preferredPickupTime;
    private boolean isPickedUp;

    public PickUpOrder(String id, String userId, List<String> itemIds, List<Integer> quantities,
                       double totalAmount, String status, LocalDateTime orderTime,
                       LocalDateTime estimatedTime, String customerName, String phoneNumber,
                       String pickupLocation, LocalDateTime preferredPickupTime) {
        super(id, userId, itemIds, quantities, totalAmount, status, orderTime, estimatedTime);
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.pickupLocation = pickupLocation;
        this.preferredPickupTime = preferredPickupTime;
        this.isPickedUp = false;
    }

    @Override
    public String getOrderType() {
        return "PICKUP";
    }

    @Override
    public void processOrder() {
        // Pickup order specific processing
        setStatus("PROCESSING");

        // Set estimated time (typically faster than delivery)
        if (getEstimatedTime() == null) {
            setEstimatedTime(getOrderTime().plusMinutes(15)); // 15 minutes for pickup
        }

        System.out.println("Processing pickup order for: " + customerName);
        System.out.println("Pickup location: " + pickupLocation);
        System.out.println("Phone: " + phoneNumber);
    }

    // Method to mark order as ready for pickup
    public void markReadyForPickup() {
        setStatus("READY");
        System.out.println("Order " + getId() + " is ready for pickup at " + pickupLocation);
    }

    // Method to confirm pickup
    public void confirmPickup() {
        this.isPickedUp = true;
        setStatus("COMPLETED");
        System.out.println("Order " + getId() + " has been picked up by " + customerName);
    }

    // Check if pickup time has passed
    public boolean isPickupTimeExpired() {
        return preferredPickupTime != null && LocalDateTime.now().isAfter(preferredPickupTime.plusHours(1));
    }
}