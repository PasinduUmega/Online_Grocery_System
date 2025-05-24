// orderService.js
const ORDER_API_URL = "http://localhost:8080/api/orders";

// Create a pickup order
export async function createPickupOrder(orderData) {
    try {
        const response = await fetch(`${ORDER_API_URL}/pickup`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orderData)
        });

        if (response.ok) {
            const order = await response.json();
            return { success: true, order };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error creating pickup order:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Create an online/delivery order
export async function createOnlineOrder(orderData) {
    try {
        const response = await fetch(`${ORDER_API_URL}/online`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orderData)
        });

        if (response.ok) {
            const order = await response.json();
            return { success: true, order };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error creating online order:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Get all orders
export async function getAllOrders() {
    try {
        const response = await fetch(ORDER_API_URL);
        return await response.json();
    } catch (error) {
        console.error("Error fetching orders:", error);
        return [];
    }
}

// Get orders by user ID
export async function getUserOrders(userId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/user/${userId}`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching user orders:", error);
        return [];
    }
}

// Get orders in queue
export async function getQueueOrders() {
    try {
        const response = await fetch(`${ORDER_API_URL}/queue`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching queue orders:", error);
        return [];
    }
}

// Get completed orders
export async function getCompletedOrders() {
    try {
        const response = await fetch(`${ORDER_API_URL}/completed`);
        return await response.json();
    } catch (error) {
        console.error("Error fetching completed orders:", error);
        return [];
    }
}

// Process next order in queue
export async function processNextOrder() {
    try {
        const response = await fetch(`${ORDER_API_URL}/process-next`, {
            method: "POST"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error processing next order:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Process all orders in queue
export async function processAllOrders() {
    try {
        const response = await fetch(`${ORDER_API_URL}/process-all`, {
            method: "POST"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error processing all orders:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Cancel an order
export async function cancelOrder(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/${orderId}`, {
            method: "DELETE"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error cancelling order:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Complete an order
export async function completeOrder(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/${orderId}/complete`, {
            method: "PUT"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error completing order:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Get order position in queue
export async function getOrderPosition(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/${orderId}/position`);

        if (response.ok) {
            return await response.json();
        } else {
            const error = await response.text();
            return { error };
        }
    } catch (error) {
        console.error("Error getting order position:", error);
        return { error: "Network error occurred" };
    }
}

// Get estimated wait time for an order
export async function getEstimatedWaitTime(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/${orderId}/wait-time`);

        if (response.ok) {
            return await response.json();
        } else {
            const error = await response.text();
            return { error };
        }
    } catch (error) {
        console.error("Error getting wait time:", error);
        return { error: "Network error occurred" };
    }
}

// Update payment status for online orders
export async function updatePaymentStatus(orderId, paymentStatus) {
    try {
        const response = await fetch(`${ORDER_API_URL}/${orderId}/payment`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ paymentStatus })
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error updating payment status:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Get queue statistics
export async function getQueueStatistics() {
    try {
        const response = await fetch(`${ORDER_API_URL}/queue/statistics`);

        if (response.ok) {
            return await response.text();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error("Error getting queue statistics:", error);
        throw error;
    }
}

// Mark pickup order as ready
export async function markPickupOrderReady(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/pickup/${orderId}/ready`, {
            method: "PUT"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error marking pickup ready:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Confirm pickup
export async function confirmPickup(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/pickup/${orderId}/confirm`, {
            method: "PUT"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error confirming pickup:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Mark online order as out for delivery
export async function markOutForDelivery(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/online/${orderId}/delivery`, {
            method: "PUT"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error marking out for delivery:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Confirm delivery
export async function confirmDelivery(orderId) {
    try {
        const response = await fetch(`${ORDER_API_URL}/online/${orderId}/confirm-delivery`, {
            method: "PUT"
        });

        if (response.ok) {
            const result = await response.text();
            return { success: true, message: result };
        } else {
            const error = await response.text();
            return { success: false, error };
        }
    } catch (error) {
        console.error("Error confirming delivery:", error);
        return { success: false, error: "Network error occurred" };
    }
}

// Utility function to format order data for display
export function formatOrderForDisplay(order) {
    return {
        ...order,
        formattedOrderTime: new Date(order.orderTime).toLocaleString(),
        formattedEstimatedTime: order.estimatedTime ? new Date(order.estimatedTime).toLocaleString() : 'Not set',
        formattedTotal: `Rs. ${order.totalAmount.toFixed(2)}`,
        statusClass: `status-${order.status.toLowerCase()}`,
        typeIcon: order.orderType === 'PICKUP' ? 'fas fa-map-marker-alt' : 'fas fa-truck'
    };
}

// Utility function to validate order data
export function validateOrderData(orderType, data) {
    const errors = [];

    if (orderType === 'pickup') {
        if (!data.customerName) errors.push('Customer name is required');
        if (!data.phoneNumber) errors.push('Phone number is required');
        if (!data.pickupLocation) errors.push('Pickup location is required');
    } else if (orderType === 'online') {
        if (!data.deliveryAddress) errors.push('Delivery address is required');
        if (!data.city) errors.push('City is required');
        if (!data.postalCode) errors.push('Postal code is required');
        if (!data.paymentMethod) errors.push('Payment method is required');
        if (data.deliveryFee === undefined || data.deliveryFee < 0) errors.push('Valid delivery fee is required');
    }

    return {
        isValid: errors.length === 0,
        errors
    };
}