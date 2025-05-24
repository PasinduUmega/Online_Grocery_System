package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.util.OrderFileUtils;
import com.example.demo.util.OrderQueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    private static final String ORDERS_FILE_PATH = "data/orders.txt";

    @Autowired
    private OrderQueueManager queueManager;

    @Autowired
    private CartService cartService;

    @Autowired
    private ItemService itemService;

    // Create pickup order from user's cart
    public PickUpOrder createPickupOrderFromCart(String userId, String customerName,
                                                 String phoneNumber, String pickupLocation,
                                                 LocalDateTime preferredPickupTime) {
        // Get user's cart items
        List<Cart> cartItems = cartService.getCartByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Get all items to calculate prices
        List<Item> allItems = itemService.getAllItems();

        // Extract item IDs, quantities, and prices from cart
        List<String> itemIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Double> itemPrices = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            Item item = allItems.stream()
                    .filter(i -> i.getId().equals(cartItem.getItemId()))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                itemIds.add(cartItem.getItemId());
                quantities.add(cartItem.getQuantity());
                itemPrices.add(item.getPrice());
            }
        }

        // Generate order ID
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8);

        // Create pickup order
        PickUpOrder order = new PickUpOrder(
                orderId, userId, itemIds, quantities, 0.0, "PENDING",
                LocalDateTime.now(), null, customerName, phoneNumber,
                pickupLocation, preferredPickupTime
        );

        // Calculate total
        order.calculateTotal(itemPrices);

        // Save order
        saveOrder(order);

        // Add to queue
        queueManager.addPickupOrder(order);

        // Clear user's cart
        cartService.clearCartByUserId(userId);

        return order;
    }

    // Create online order from user's cart
    public OnlineOrder createOnlineOrderFromCart(String userId, String deliveryAddress,
                                                 String city, String postalCode,
                                                 String deliveryInstructions, double deliveryFee,
                                                 String paymentMethod) {
        // Get user's cart items
        List<Cart> cartItems = cartService.getCartByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Get all items to calculate prices
        List<Item> allItems = itemService.getAllItems();

        // Extract item IDs, quantities, and prices from cart
        List<String> itemIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Double> itemPrices = new ArrayList<>();

        for (Cart cartItem : cartItems) {
            Item item = allItems.stream()
                    .filter(i -> i.getId().equals(cartItem.getItemId()))
                    .findFirst()
                    .orElse(null);

            if (item != null) {
                itemIds.add(cartItem.getItemId());
                quantities.add(cartItem.getQuantity());
                itemPrices.add(item.getPrice());
            }
        }

        // Generate order ID
        String orderId = "ORD" + UUID.randomUUID().toString().substring(0, 8);

        // Determine initial payment status
        String paymentStatus = "CASH_ON_DELIVERY".equals(paymentMethod) ? "PENDING" : "PAID";

        // Create online order
        OnlineOrder order = new OnlineOrder(
                orderId, userId, itemIds, quantities, 0.0, "PENDING",
                LocalDateTime.now(), null, deliveryAddress, city,
                postalCode, deliveryInstructions, deliveryFee,
                paymentMethod, paymentStatus
        );

        // Calculate total (includes delivery fee)
        order.calculateTotal(itemPrices);

        // Save order
        saveOrder(order);

        // Add to queue
        queueManager.addOnlineOrder(order);

        // Clear user's cart
        cartService.clearCartByUserId(userId);

        return order;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return OrderFileUtils.readOrdersFromFile(ORDERS_FILE_PATH);
    }

    // Get orders by user ID
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> allOrders = getAllOrders();
        return allOrders.stream()
                .filter(order -> order.getUserId().equals(userId))
                .toList();
    }

    // Get all orders in processing queue
    public List<Order> getAllOrdersInQueue() {
        return queueManager.getAllOrdersInQueue();
    }

    // Get completed orders
    public List<Order> getCompletedOrders() {
        return queueManager.getCompletedOrders();
    }

    // Process next order in queue
    public Order processNextOrder() {
        return queueManager.processNextOrder();
    }

    // Process all orders in queue
    public void processAllOrders() {
        queueManager.processAllOrders();
    }

    // Complete an order
    public void completeOrder(String orderId) {
        List<Order> allOrders = getAllOrders();
        Order orderToComplete = allOrders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        queueManager.completeOrder(orderToComplete);

        // Update order status in file
        orderToComplete.setStatus("COMPLETED");
        OrderFileUtils.writeOrdersToFile(ORDERS_FILE_PATH, allOrders);
    }

    // Cancel an order
    public boolean cancelOrder(String orderId) {
        return queueManager.removeOrderFromQueue(orderId);
    }

    // Get order position in queue
    public int getOrderPosition(String orderId) {
        return queueManager.getOrderPosition(orderId);
    }

    // Get estimated wait time for an order
    public LocalDateTime getEstimatedWaitTime(String orderId) {
        return queueManager.getEstimatedWaitTime(orderId);
    }

    // Update payment status for online orders
    public void updatePaymentStatus(String orderId, String paymentStatus) {
        List<Order> allOrders = getAllOrders();
        for (Order order : allOrders) {
            if (order.getId().equals(orderId) && order instanceof OnlineOrder) {
                OnlineOrder onlineOrder = (OnlineOrder) order;
                onlineOrder.updatePaymentStatus(paymentStatus);
                OrderFileUtils.writeOrdersToFile(ORDERS_FILE_PATH, allOrders);
                return;
            }
        }
        throw new RuntimeException("Online order not found: " + orderId);
    }

    // Get queue statistics
    public String getQueueStatistics() {
        return queueManager.getQueueStatistics();
    }

    // Save order to file
    private void saveOrder(Order order) {
        List<Order> allOrders = getAllOrders();
        allOrders.add(order);
        OrderFileUtils.writeOrdersToFile(ORDERS_FILE_PATH, allOrders);
    }
}