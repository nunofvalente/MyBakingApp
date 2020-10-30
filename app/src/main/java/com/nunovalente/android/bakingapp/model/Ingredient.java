package com.nunovalente.android.bakingapp.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    /**
     * quantity : 2
     * measure : CUP
     * ingredient : Graham Cracker crumbs
     */

    private final float quantity;
    private final String measure;
    private final String ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public String toString() {
        return ingredient + " " + " " + quantity + measure;
    }
}
