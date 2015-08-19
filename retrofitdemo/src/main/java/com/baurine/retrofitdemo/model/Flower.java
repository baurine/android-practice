package com.baurine.retrofitdemo.model;

import android.graphics.Bitmap;

public class Flower {

    private int productId;
    private String name;
    private String category;
    private double price;
    private String instructions;
    private String photo;

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * @return The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return The price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * @param instructions The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * @return The photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo The photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The productId
     */
    public int getProductId() {
        return productId;
    }

    /**
     * @param productId The productId
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

}