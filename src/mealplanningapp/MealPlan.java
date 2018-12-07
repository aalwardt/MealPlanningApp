/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.time.LocalDate;


/**
 * Encapsulates a meal plan, consisting of 5 meals, max and min calories, protein, and carbs
 * @author alexa
 */
public class MealPlan {
    private LocalDate date;
    private int minCalories, maxCalories, minProtein, maxProtein, minCarbs, maxCarbs;
    private Meal meal1, meal2, meal3, meal4, meal5;
    
    public MealPlan(LocalDate date,
                    int minCalories, int maxCalories,
                    int minProtein, int maxProtein,
                    int minCarbs, int maxCarbs,
                    Meal meal1, Meal meal2, Meal meal3, Meal meal4, Meal meal5)
    {
        //Initialize all of the local variables
        this.date = date;
        this.minCalories = minCalories;
        this.maxCalories = maxCalories;
        this.minProtein = minProtein;
        this.maxProtein = maxProtein;
        this.minCarbs = minCarbs;
        this.maxCarbs = maxCarbs;
        this.meal1 = meal1;
        this.meal2 = meal2;
        this.meal3 = meal3;
        this.meal4 = meal4;
        this.meal5 = meal5;
    }
    
    //Getters for information
    public LocalDate getDate() { return date; }
    public int getMinCalories() { return minCalories; }
    public int getMaxCalories() { return maxCalories; }
    public int getMinProtein() { return minProtein; }
    public int getMaxProtein() { return maxProtein; }
    public int getMinCarbs() { return minCarbs; }
    public int getMaxCarbs() { return minCarbs; }
    
    public Meal getMeal(int index) {
        switch (index) {
            case 0:
                return meal1;
            case 1:
                return meal2;
            case 2:
                return meal3;
            case 3:
                return meal4;
            case 4:
                return meal5;
            default:
                return null;
        }
    }
    
    //Get whether protein and carb limits were used in plan
    //Min and Max protein/carbs are saved as -1 if not used
    public boolean proteinToggled() {
        if (minProtein == -1) return false;
        else return true;
    }
    public boolean carbToggled() {
        if (minCarbs == -1) return false;
        else return true;
    }
}
