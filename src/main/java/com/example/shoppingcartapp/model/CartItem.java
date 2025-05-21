package com.example.shoppingcartapp.model;


public class CartItem {
    private String name;
    private String category;
    private int quantity;
    private double price;
    private String imagePath;

    // Constructors
    public CartItem() {}

    public CartItem(String name, String category, int quantity, double price, String imagePath) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
