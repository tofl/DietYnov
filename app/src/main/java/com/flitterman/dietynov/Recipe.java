package com.flitterman.dietynov;

import java.util.ArrayList;

public class Recipe {

    private String title;
    private int portions;
    private String pictureUrl;
    private int totalTime;
    private int prepTime;
    private int bakingTime;
    private ArrayList<Ingredients> ingredients;
    private ArrayList<Steps> steps;
    private float kcal;
    private float protein;
    private float fat;
    private float carbohydrate;
    private float sugar;
    private float sat_fat;
    private float fiber;
    private float sodium;

    public Recipe() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPortions() {
        return portions;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getBakingTime() {
        return bakingTime;
    }

    public void setBakingTime(int bakingTime) {
        this.bakingTime = bakingTime;
    }

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

    public void addIngredient(Ingredients ingredient) {
        ingredients.add(ingredient);
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void addStep(Steps step) {
        steps.add(step);
    }

    public float getKcal() {
        return kcal;
    }

    public void setKcal(float kcal) {
        this.kcal = kcal;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public float getSat_fat() {
        return sat_fat;
    }

    public void setSat_fat(float sat_fat) {
        this.sat_fat = sat_fat;
    }

    public float getFiber() {
        return fiber;
    }

    public void setFiber(float fiber) {
        this.fiber = fiber;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }
}
