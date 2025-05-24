const CART_API_URL = "http://localhost:8080/api/cart";

export async function addToCart(userId, itemId, quantity = 1) {
    const cartItem = {
        userId,
        itemId,
        quantity
    };

    try {
        const response = await fetch(`${CART_API_URL}/add`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(cartItem)
        });

        if (response.ok) {
            alert("Item added to cart!");
        } else {
            alert("Failed to add to cart.");
        }
    } catch (error) {
        console.error("Cart error:", error);
        alert("An error occurred.");
    }
}
