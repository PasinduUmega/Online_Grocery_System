package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.model.PickUpOrder;
import com.example.demo.model.OnlineOrder;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create pickup order from user's cart
    @PostMapping("/pickup")
    public ResponseEntity<?> createPickupOrder(@RequestBody Map<String, Object> requestBody) {
        try {
            String userId = (String) requestBody.get("userId");
            String customerName = (String) requestBody.get("customerName");
            String phoneNumber = (String) requestBody.get("phoneNumber");
            String pickupLocation = (String) requestBody.get("pickupLocation");

            LocalDateTime preferredPickupTime = null;
            if (requestBody.get("preferredPickupTime") != null) {
                String timeString = (String) requestBody.get("preferredPickupTime");
                preferredPickupTime = LocalDateTime.parse(timeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            PickUpOrder order = orderService.createPickupOrderFromCart(
                    userId, customerName, phoneNumber, pickupLocation, preferredPickupTime
            );

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating pickup order: " + e.getMessage());
        }
    }

    // Create online order from user's cart
    @PostMapping("/online")
    public ResponseEntity<?> createOnlineOrder(@RequestBody Map<String, Object> requestBody) {
        try {
            String userId = (String) requestBody.get("userId");
            String deliveryAddress = (String) requestBody.get("deliveryAddress");
            String city = (String) requestBody.get("city");
            String postalCode = (String) requestBody.get("postalCode");
            String deliveryInstructions = (String) requestBody.get("deliveryInstructions");
            double deliveryFee = Double.parseDouble(requestBody.get("deliveryFee").toString());
            String paymentMethod = (String) requestBody.get("paymentMethod");

            OnlineOrder order = orderService.createOnlineOrderFromCart(
                    userId, deliveryAddress, city, postalCode, deliveryInstructions, deliveryFee, paymentMethod
            );

            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating online order: " + e.getMessage());
        }
    }

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get orders by user ID
    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable String userId) {
        return orderService.getOrdersByUserId(userId);
    }

    // Get all orders in processing queue
    @GetMapping("/queue")
    public List<Order> getAllOrdersInQueue() {
        return orderService.getAllOrdersInQueue();
    }

    // Get completed orders
    @GetMapping("/completed")
    public List<Order> getCompletedOrders() {
        return orderService.getCompletedOrders();
    }

    // Process next order in queue
    @PostMapping("/process-next")
    public ResponseEntity<?> processNextOrder() {
        try {
            Order processedOrder = orderService.processNextOrder();
            if (processedOrder != null) {
                return ResponseEntity.ok(processedOrder);
            } else {
                return ResponseEntity.ok("No orders in queue to process");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing order: " + e.getMessage());
        }
    }

    // Process all orders in queue
    @PostMapping("/process-all")
    public ResponseEntity<String> processAllOrders() {
        try {
            orderService.processAllOrders();
            return ResponseEntity.ok("All orders processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing orders: " + e.getMessage());
        }
    }

    // Complete an order
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable String orderId) {
        try {
            orderService.completeOrder(orderId);
            return ResponseEntity.ok("Order completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error completing order: " + e.getMessage());
        }
    }

    // Cancel an order
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable String orderId) {
        try {
            boolean cancelled = orderService.cancelOrder(orderId);
            if (cancelled) {
                return ResponseEntity.ok("Order cancelled successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found in queue");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error cancelling order: " + e.getMessage());
        }
    }

    // Get order position in queue
    @GetMapping("/{orderId}/position")
    public ResponseEntity<?> getOrderPosition(@PathVariable String orderId) {
        try {
            int position = orderService.getOrderPosition(orderId);
            if (position == -1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found in queue");
            }
            return ResponseEntity.ok(Map.of("orderId", orderId, "position", position));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting order position: " + e.getMessage());
        }
    }

    // Get estimated wait time for an order
    @GetMapping("/{orderId}/wait-time")
    public ResponseEntity<?> getEstimatedWaitTime(@PathVariable String orderId) {
        try {
            LocalDateTime waitTime = orderService.getEstimatedWaitTime(orderId);
            if (waitTime == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found in queue");
            }
            return ResponseEntity.ok(Map.of(
                    "orderId", orderId,
                    "estimatedWaitTime", waitTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting wait time: " + e.getMessage());
        }
    }

    // Update payment status for online orders
    @PutMapping("/{orderId}/payment")
    public ResponseEntity<String> updatePaymentStatus(@PathVariable String orderId,
                                                      @RequestBody Map<String, String> requestBody) {
        try {
            String paymentStatus = requestBody.get("paymentStatus");
            orderService.updatePaymentStatus(orderId, paymentStatus);
            return ResponseEntity.ok("Payment status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating payment status: " + e.getMessage());
        }
    }

    // Get queue statistics
    @GetMapping("/queue/statistics")
    public ResponseEntity<String> getQueueStatistics() {
        try {
            String statistics = orderService.getQueueStatistics();
            return ResponseEntity.ok(statistics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error getting statistics: " + e.getMessage());
        }
    }

    // Mark pickup order as ready
    @PutMapping("/pickup/{orderId}/ready")
    public ResponseEntity<String> markPickupOrderReady(@PathVariable String orderId) {
        try {
            List<Order> allOrders = orderService.getAllOrders();
            for (Order order : allOrders) {
                if (order.getId().equals(orderId) && order instanceof PickUpOrder) {
                    ((PickUpOrder) order).markReadyForPickup();
                    return ResponseEntity.ok("Pickup order marked as ready");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pickup order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking order ready: " + e.getMessage());
        }
    }

    // Confirm pickup
    @PutMapping("/pickup/{orderId}/confirm")
    public ResponseEntity<String> confirmPickup(@PathVariable String orderId) {
        try {
            List<Order> allOrders = orderService.getAllOrders();
            for (Order order : allOrders) {
                if (order.getId().equals(orderId) && order instanceof PickUpOrder) {
                    ((PickUpOrder) order).confirmPickup();
                    orderService.completeOrder(orderId);
                    return ResponseEntity.ok("Pickup confirmed and order completed");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pickup order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error confirming pickup: " + e.getMessage());
        }
    }

    // Mark online order as out for delivery
    @PutMapping("/online/{orderId}/delivery")
    public ResponseEntity<String> markOutForDelivery(@PathVariable String orderId) {
        try {
            List<Order> allOrders = orderService.getAllOrders();
            for (Order order : allOrders) {
                if (order.getId().equals(orderId) && order instanceof OnlineOrder) {
                    ((OnlineOrder) order).markOutForDelivery();
                    return ResponseEntity.ok("Order marked as out for delivery");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Online order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error marking out for delivery: " + e.getMessage());
        }
    }

    // Confirm delivery
    @PutMapping("/online/{orderId}/confirm-delivery")
    public ResponseEntity<String> confirmDelivery(@PathVariable String orderId) {
        try {
            List<Order> allOrders = orderService.getAllOrders();
            for (Order order : allOrders) {
                if (order.getId().equals(orderId) && order instanceof OnlineOrder) {
                    ((OnlineOrder) order).confirmDelivery();
                    orderService.completeOrder(orderId);
                    return ResponseEntity.ok("Delivery confirmed and order completed");
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Online order not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error confirming delivery: " + e.getMessage());
        }
    }
}