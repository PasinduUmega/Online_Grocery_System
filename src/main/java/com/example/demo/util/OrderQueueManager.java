package com.example.demo.util;

import com.example.demo.model.Order;
import com.example.demo.model.PickUpOrder;
import com.example.demo.model.OnlineOrder;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Component
public class OrderQueueManager {

    // Separate queues for different order types for better management
    private Queue<PickUpOrder> pickupOrderQueue;
    private Queue<OnlineOrder> onlineOrderQueue;

    // Combined processing queue (FIFO - First In, First Out)
    private Queue<Order> processingQueue;

    // Completed orders list for tracking
    private List<Order> completedOrders;

    public OrderQueueManager() {
        this.pickupOrderQueue = new LinkedList<>();
        this.onlineOrderQueue = new LinkedList<>();
        this.processingQueue = new LinkedList<>();
        this.completedOrders = new ArrayList<>();
    }

    // Add pickup order to queue
    public void addPickupOrder(PickUpOrder order) {
        pickupOrderQueue.offer(order);
        processingQueue.offer(order);
        System.out.println("Added pickup order " + order.getId() + " to queue. Queue size: " + processingQueue.size());
    }

    // Add online order to queue
    public void addOnlineOrder(OnlineOrder order) {
        onlineOrderQueue.offer(order);
        processingQueue.offer(order);
        System.out.println("Added online order " + order.getId() + " to queue. Queue size: " + processingQueue.size());
    }

    // Process next order in queue
    public Order processNextOrder() {
        Order nextOrder = processingQueue.poll();
        if (nextOrder != null) {
            nextOrder.processOrder();
            System.out.println("Processing order: " + nextOrder.getId() + " (Type: " + nextOrder.getOrderType() + ")");
            return nextOrder;
        }
        System.out.println("No orders in queue to process.");
        return null;
    }

    // Process all orders in queue
    public void processAllOrders() {
        System.out.println("Processing " + processingQueue.size() + " orders in queue...");
        while (!processingQueue.isEmpty()) {
            processNextOrder();
        }
        System.out.println("All orders processed.");
    }

    // Complete an order (move from processing to completed)
    public void completeOrder(Order order) {
        order.setStatus("COMPLETED");
        completedOrders.add(order);

        // Remove from specific queues if still there
        pickupOrderQueue.remove(order);
        onlineOrderQueue.remove(order);
        processingQueue.remove(order);

        System.out.println("Order " + order.getId() + " completed and moved to completed orders.");
    }

    // Get next order without removing from queue (peek)
    public Order peekNextOrder() {
        return processingQueue.peek();
    }

    // Get queue size
    public int getQueueSize() {
        return processingQueue.size();
    }

    // Get pickup queue size
    public int getPickupQueueSize() {
        return pickupOrderQueue.size();
    }

    // Get online queue size
    public int getOnlineQueueSize() {
        return onlineOrderQueue.size();
    }

    // Check if queue is empty
    public boolean isQueueEmpty() {
        return processingQueue.isEmpty();
    }

    // Get all orders in processing queue
    public List<Order> getAllOrdersInQueue() {
        return new ArrayList<>(processingQueue);
    }

    // Get all pickup orders in queue
    public List<PickUpOrder> getAllPickupOrders() {
        return new ArrayList<>(pickupOrderQueue);
    }

    // Get all online orders in queue
    public List<OnlineOrder> getAllOnlineOrders() {
        return new ArrayList<>(onlineOrderQueue);
    }

    // Get completed orders
    public List<Order> getCompletedOrders() {
        return new ArrayList<>(completedOrders);
    }

    // Remove specific order from queue
    public boolean removeOrderFromQueue(String orderId) {
        Order orderToRemove = null;

        // Find the order in the processing queue
        for (Order order : processingQueue) {
            if (order.getId().equals(orderId)) {
                orderToRemove = order;
                break;
            }
        }

        if (orderToRemove != null) {
            processingQueue.remove(orderToRemove);
            pickupOrderQueue.remove(orderToRemove);
            onlineOrderQueue.remove(orderToRemove);

            // Mark as cancelled
            orderToRemove.setStatus("CANCELLED");
            System.out.println("Order " + orderId + " removed from queue and cancelled.");
            return true;
        }

        System.out.println("Order " + orderId + " not found in queue.");
        return false;
    }

    // Get order position in queue
    public int getOrderPosition(String orderId) {
        int position = 1;
        for (Order order : processingQueue) {
            if (order.getId().equals(orderId)) {
                return position;
            }
            position++;
        }
        return -1; // Order not found
    }

    // Get estimated wait time for an order
    public LocalDateTime getEstimatedWaitTime(String orderId) {
        int position = getOrderPosition(orderId);
        if (position == -1) {
            return null;
        }

        // Estimate 15 minutes per order ahead in queue
        LocalDateTime estimatedTime = LocalDateTime.now().plusMinutes(15 * (position - 1));
        return estimatedTime;
    }

    // Clear all queues (for testing or reset purposes)
    public void clearAllQueues() {
        processingQueue.clear();
        pickupOrderQueue.clear();
        onlineOrderQueue.clear();
        System.out.println("All queues cleared.");
    }

    // Get queue statistics
    public String getQueueStatistics() {
        return String.format(
                "Queue Statistics:\n" +
                        "Total orders in processing queue: %d\n" +
                        "Pickup orders: %d\n" +
                        "Online orders: %d\n" +
                        "Completed orders: %d",
                getQueueSize(), getPickupQueueSize(), getOnlineQueueSize(), completedOrders.size()
        );
    }
}