package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OnlineOrder extends Order {
    private String deliveryAddress;
    private String city;
    private String postalCode;
    private String deliveryInstructions;
    private double deliveryFee;
    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, PAYPAL, etc.
    private String paymentStatus; // PENDING, PAID, FAILED, REFUNDED
    private String trackingNumber;
    private boolean isDelivered;

    public OnlineOrder(String id, String userId, List<String> itemIds, List<Integer> quantities,
                       double totalAmount, String status, LocalDateTime orderTime,
                       LocalDateTime estimatedTime, String deliveryAddress, String city,
                       String postalCode, String deliveryInstructions, double deliveryFee,
                       String paymentMethod, String paymentStatus) {
        super(id, userId, itemIds, quantities, totalAmount, status, orderTime, estimatedTime);
        this.deliveryAddress = deliveryAddress;
        this.city = city;
        this.postalCode = postalCode;
        this.deliveryInstructions = deliveryInstructions;
        this.deliveryFee = deliveryFee;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.isDelivered = false;
    }

    @Override
    public String getOrderType() {
        return "ONLINE";
    }

    @Override
    public void processOrder() {
        // Online order specific processing
        if (!"PAID".equals(paymentStatus)) {
            setStatus("PENDING_PAYMENT");
            System.out.println("Waiting for payment confirmation for order: " + getId());
            return;
        }

        setStatus("PROCESSING");

        // Set estimated delivery time (typically longer than pickup)
        if (getEstimatedTime() == null) {
            setEstimatedTime(getOrderTime().plusMinutes(45)); // 45 minutes for delivery
        }

        // Generate tracking number
        if (trackingNumber == null) {
            this.trackingNumber = "TRK" + getId() + System.currentTimeMillis();
        }

        System.out.println("Processing online order: " + getId());
        System.out.println("Delivery address: " + getFullAddress());
        System.out.println("Payment method: " + paymentMethod);
        System.out.println("Tracking number: " + trackingNumber);
    }

    @Override
    public void calculateTotal(List<Double> itemPrices) {
        super.calculateTotal(itemPrices);
        // Add delivery fee to total for online orders
        setTotalAmount(getTotalAmount() + deliveryFee);
    }

    // Get full formatted address
    public String getFullAddress() {
        return deliveryAddress + ", " + city + " " + postalCode;
    }

    // Method to mark order as out for delivery
    public void markOutForDelivery() {
        setStatus("OUT_FOR_DELIVERY");
        System.out.println("Order " + getId() + " is out for delivery to " + getFullAddress());
    }

    // Method to confirm delivery
    public void confirmDelivery() {
        this.isDelivered = true;
        setStatus("COMPLETED");
        System.out.println("Order " + getId() + " has been delivered to " + getFullAddress());
    }

    // Method to update payment status
    public void updatePaymentStatus(String newPaymentStatus) {
        this.paymentStatus = newPaymentStatus;
        if ("PAID".equals(newPaymentStatus) && "PENDING_PAYMENT".equals(getStatus())) {
            processOrder(); // Continue processing after payment
        }
    }

    // Check if payment is successful
    public boolean isPaymentSuccessful() {
        return "PAID".equals(paymentStatus);
    }
}