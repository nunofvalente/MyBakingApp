package com.nunovalente.android.bakingapp.model;

import java.io.Serializable;

public class Ingredient implements Serializable {

    /**
     * quantity : 2
     * measure : CUP
     * ingredient : Graham Cracker crumbs
     */

    private float quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return ingredient + " " + " " + quantity + measure;
    }
}
