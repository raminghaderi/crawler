package com.datenbanken.webcrawler;

/**
 *
 * @author ramin
 */
public class MealEntity extends Meal {

    private int mealId;

    public MealEntity(int mealId, int restaurantID, String name, String ingredient, float price) {
        super(restaurantID, name, ingredient, price);
        this.mealId = mealId;
    }

    public MealEntity(int restaurantID, String name, String ingredient, float price) {
        super(restaurantID, name, ingredient, price);
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

}
