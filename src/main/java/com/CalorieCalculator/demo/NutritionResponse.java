package com.CalorieCalculator.demo;

public class NutritionResponse {
    private double calories;
    private double protein;

    public NutritionResponse(double calories, double protein) {
        this.calories = calories;
        this.protein = protein;
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }
}
