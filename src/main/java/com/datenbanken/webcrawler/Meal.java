package com.datenbanken.webcrawler;

/**
 *
 * @author ramin
 */
public class Meal {

    private int restaurantID;
    private String name;
    private String ingredient;
    private float price;

    public Meal(int restaurantID, String name, String ingredient, float price) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.ingredient = ingredient;
        this.price = price;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
