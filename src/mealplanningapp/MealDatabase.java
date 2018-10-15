/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.util.List;

/**
 *
 * @author alexa
 */
public class MealDatabase {
    
    //The path of the database file
    private static final String PATH = "./data/mealdb";
    
    //Inserts a meal into the database
    public static void InsertMeal(Meal meal) {
        
    }
    
    //Removes a meal from the database
    public static void RemoveMeal(Meal meal) {
        
    }
    
    //Gets all meals from the database
    public static List<Meal> GetAllMeals() {
        return null;
    }
    
    //Get all meals from the database under a calorie limit
    public static List<Meal> GetMealsUnderCalories(int maxCalories) {
        return null;
    }
}
