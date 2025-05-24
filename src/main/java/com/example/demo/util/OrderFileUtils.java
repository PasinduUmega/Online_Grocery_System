package com.example.demo.util;

import com.example.demo.model.Order;
import com.example.demo.model.PickUpOrder;
import com.example.demo.model.OnlineOrder;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderFileUtils {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<Order> readOrdersFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Order order = parseOrderFromLine(line);
                    if (order != null) {
                        orders.add(order);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing order line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading orders file: " + e.getMessage());
        }
        return orders;
    }

    public static void writeOrdersToFile(String filePath, List<Order> orders) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                String orderLine = formatOrderToLine(order);
                if (orderLine != null) {
                    writer.write(orderLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing orders file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Order parseOrderFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 8) {
            return null;
        }

        try {
            String orderType = parts[0];
            String id = parts[1];
            String userId = parts[2];
            List<String> itemIds = parseStringList(parts[3]);
            List<Integer> quantities = parseIntegerList(parts[4]);
            double totalAmount = Double.parseDouble(parts[5]);
            String status = parts[6];
            LocalDateTime orderTime = LocalDateTime.parse(parts[7], DATE_FORMAT);
            LocalDateTime estimatedTime = parts[8].isEmpty() ? null : LocalDateTime.parse(parts[8], DATE_FORMAT);

            if ("PICKUP".equals(orderType)) {
                // Parse pickup-specific fields
                if (parts.length >= 13) {
                    String customerName = parts[9];
                    String phoneNumber = parts[10];
                    String pickupLocation = parts[11];
                    LocalDateTime preferredPickupTime = parts[12].isEmpty() ? null : LocalDateTime.parse(parts[12], DATE_FORMAT);

                    PickUpOrder pickupOrder = new PickUpOrder(id, userId, itemIds, quantities, totalAmount,
                            status, orderTime, estimatedTime, customerName,
                            phoneNumber, pickupLocation, preferredPickupTime);

                    if (parts.length > 13 && !parts[13].isEmpty()) {
                        pickupOrder.setPickedUp(Boolean.parseBoolean(parts[13]));
                    }

                    return pickupOrder;
                }
            } else if ("ONLINE".equals(orderType)) {
                // Parse online-specific fields
                if (parts.length >= 17) {
                    String deliveryAddress = parts[9];
                    String city = parts[10];
                    String postalCode = parts[11];
                    String deliveryInstructions = parts[12];
                    double deliveryFee = Double.parseDouble(parts[13]);
                    String paymentMethod = parts[14];
                    String paymentStatus = parts[15];
                    String trackingNumber = parts[16].isEmpty() ? null : parts[16];

                    OnlineOrder onlineOrder = new OnlineOrder(id, userId, itemIds, quantities, totalAmount,
                            status, orderTime, estimatedTime, deliveryAddress,
                            city, postalCode, deliveryInstructions, deliveryFee,
                            paymentMethod, paymentStatus);

                    onlineOrder.setTrackingNumber(trackingNumber);

                    if (parts.length > 17 && !parts[17].isEmpty()) {
                        onlineOrder.setDelivered(Boolean.parseBoolean(parts[17]));
                    }

                    return onlineOrder;
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing order: " + e.getMessage());
        }

        return null;
    }

    private static String formatOrderToLine(Order order) {
        try {
            StringBuilder sb = new StringBuilder();

            // Common fields
            sb.append(order.getOrderType()).append("|");
            sb.append(order.getId()).append("|");
            sb.append(order.getUserId()).append("|");
            sb.append(formatStringList(order.getItemIds())).append("|");
            sb.append(formatIntegerList(order.getQuantities())).append("|");
            sb.append(order.getTotalAmount()).append("|");
            sb.append(order.getStatus()).append("|");
            sb.append(order.getOrderTime().format(DATE_FORMAT)).append("|");
            sb.append(order.getEstimatedTime() != null ? order.getEstimatedTime().format(DATE_FORMAT) : "").append("|");

            if (order instanceof PickUpOrder) {
                PickUpOrder pickupOrder = (PickUpOrder) order;
                sb.append(pickupOrder.getCustomerName()).append("|");
                sb.append(pickupOrder.getPhoneNumber()).append("|");
                sb.append(pickupOrder.getPickupLocation()).append("|");
                sb.append(pickupOrder.getPreferredPickupTime() != null ?
                        pickupOrder.getPreferredPickupTime().format(DATE_FORMAT) : "").append("|");
                sb.append(pickupOrder.isPickedUp());

            } else if (order instanceof OnlineOrder) {
                OnlineOrder onlineOrder = (OnlineOrder) order;
                sb.append(onlineOrder.getDeliveryAddress()).append("|");
                sb.append(onlineOrder.getCity()).append("|");
                sb.append(onlineOrder.getPostalCode()).append("|");
                sb.append(onlineOrder.getDeliveryInstructions() != null ? onlineOrder.getDeliveryInstructions() : "").append("|");
                sb.append(onlineOrder.getDeliveryFee()).append("|");
                sb.append(onlineOrder.getPaymentMethod()).append("|");
                sb.append(onlineOrder.getPaymentStatus()).append("|");
                sb.append(onlineOrder.getTrackingNumber() != null ? onlineOrder.getTrackingNumber() : "").append("|");
                sb.append(onlineOrder.isDelivered());
            }

            return sb.toString();
        } catch (Exception e) {
            System.err.println("Error formatting order: " + e.getMessage());
            return null;
        }
    }

    private static List<String> parseStringList(String listStr) {
        if (listStr.isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(listStr.split(","));
    }

    private static List<Integer> parseIntegerList(String listStr) {
        if (listStr.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        String[] parts = listStr.split(",");
        for (String part : parts) {
            result.add(Integer.parseInt(part.trim()));
        }
        return result;
    }

    private static String formatStringList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(",", list);
    }

    private static String formatIntegerList(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
    }
}