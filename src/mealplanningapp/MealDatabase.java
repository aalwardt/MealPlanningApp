/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author alexa
 */
public class MealDatabase {
    
    //The path of the database file
    private static final String PATH = "./data/mealdb";
    
    private static Connection connector;
    
    private static void connectToDb() {
        try {
            //Register JDBC driver
            Class.forName("org.h2.Driver");
            //Connect to H2 database
            connector = DriverManager.getConnection("jdbc:h2:" + PATH, "sa", "");                
        } catch (SQLException e) {
            System.err.println("SQL Connection failure: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found: " + e.getMessage());
        }
    }
    
    //Inserts a meal into the database
    public static void InsertMeal(Meal meal) {
        connectToDb();
        try {
            Statement stmt = connector.createStatement();
            String sql = String.format("insert into meals(name, category, calories) values('%s', '%s', %d);",
                    meal.getName(),
                    meal.getCategory().toString(),
                    meal.getCalories());
            
            stmt.executeUpdate(sql);
            System.out.println("New meal added to database");
            
            //Clean up environment
            stmt.close();
            connector.close();
        } catch (SQLException e) {
            System.err.println("SQL Insertion Failer: " + e.getMessage());
        }        
    }
    
    //Removes a meal from the database
    public static void RemoveMeal(Meal meal) {
        connectToDb();
        try {
            Statement stmt = connector.createStatement();
            String sql = String.format("delete from meals where id = %d;",
                    meal.getId());
            
            stmt.executeUpdate(sql);
            System.out.println("Meal removed from database");
            
            //Clean up environment
            stmt.close();
            connector.close();
        } catch (SQLException e) {
            System.err.println("SQL Deletion Failure: " + e.getMessage());
        }  
    }
    
    //Gets all meals from the database
    public static ArrayList<Meal> GetAllMeals() {
        ArrayList<Meal> mealList = new ArrayList<>();
        
        connectToDb();
        try {
            Statement stmt = connector.createStatement();
            String sql = "Select id, name, category, calories from meals;";
            ResultSet rs = stmt.executeQuery(sql);

            //Get data from result set
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int calories = rs.getInt("calories");

                mealList.add(new Meal(id, name, calories, Meal.Category.valueOf(category)));
            }
            System.out.println("Meals read from database");

            //Clean up environment
            stmt.close();
            connector.close();  
        } catch (SQLException e) {
            System.err.println("SQL Read Failure: " + e.getMessage());
        } 
        
        return mealList;
    }
    
    //Get all meals from the database under a calorie limit
    public static ArrayList<Meal> GetMealsUnderCalories(int maxCalories) {
        return null;
    }
}
