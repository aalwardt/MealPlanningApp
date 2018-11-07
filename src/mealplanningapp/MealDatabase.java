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
            String sql = String.format("insert into meals(name, category, calories, protein, carbs) values('%s', '%s', %d, %d, %d);",
                    meal.getName(),
                    meal.getCategory().toString(),
                    meal.getCalories(),
                    meal.getProtein(),
                    meal.getCarbs());
            
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
    
    //Removes a meal from the database
    public static void UpdateMeal(Meal oldMeal, Meal newMeal) {
        connectToDb();
        try {
            Statement stmt = connector.createStatement();
            String sql = String.format("update meals set name = '%s', category = '%s', calories = %d, protein = %d, carbs = %d where ID=%d;",
                    newMeal.getName(),
                    newMeal.getCategory().toString(),
                    newMeal.getCalories(),
                    newMeal.getProtein(),
                    newMeal.getCarbs(),
                    oldMeal.getId());
            
            stmt.executeUpdate(sql);
            System.out.println("Meal updated in database");
            
            //Clean up environment
            stmt.close();
            connector.close();
        } catch (SQLException e) {
            System.err.println("SQL Update Failure: " + e.getMessage());
        }  
    }
    
    //Gets all meals from the database
    public static ArrayList<Meal> GetAllMeals() {
        ArrayList<Meal> mealList = new ArrayList<>();
        
        connectToDb();
        try {
            Statement stmt = connector.createStatement();
            String sql = "Select id, name, category, calories, protein, carbs from meals;";
            ResultSet rs = stmt.executeQuery(sql);

            //Get data from result set
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int calories = rs.getInt("calories");
                int protein = rs.getInt("protein");
                int carbs = rs.getInt("carbs");

                mealList.add(new Meal(id, name, Meal.Category.valueOf(category), calories, protein, carbs));
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
    
    //Get meal plan satisfying requirements
    public static ArrayList<Meal> GenerateMealPlan( ArrayList<Meal.Category> categories, int minCal, int maxCal, int minProt, int maxProt, int minCarb, int maxCarb) {
        String[] alias = {"A", "B", "C", "D", "E"};
        int numMeals = categories.size();

        String selectedCols = ""; //The columns used in select statement
        String selectedTables = ""; //The tables used in the select statement;
        String catConditions = ""; //Conditions for category of each meal
        String totalCalories = "";
        String totalProtein = "";
        String totalCarbs = "";
        
        for (int i = 0; i < numMeals; i++) {
            //Selected Columns
            //Add a comma if this is not the first entry
            if (i != 0)
                selectedCols += ",";
            //" A.ID, A.category, A.calories, A.protein, A.carbs"
            selectedCols += String.format(" %1$s.ID, %1$s.category, %1$s.calories, %1$s.protein, %1$s.carbs", alias[i]);
            
            //Selected tables
            //Add a comma if this is not the first entry
            if (i != 0)
                selectedTables += ",";
            //" Meals A, Meals B, Meals C... "
            selectedTables += String.format(" Meals %s", alias[i]);
            
            //Category conditions
            //Add an 'and' if this isn't the first entry
            if (i != 0)
                catConditions += " and";
            //" A.category = 'Breakfast'"
            catConditions += String.format(" %s.category = '%s'", alias[i], categories.get(i).toString());
            
            //Totals
            //Add a " + " if this isn't the first entry
            if (i != 0) {
                totalCalories += " + ";
                totalProtein += " + ";
                totalCarbs += " + ";
            }
            //"A.Calories + B.Calories + ..."
            totalCalories += String.format("%s.calories", alias[i]);
            totalProtein += String.format("%s.protein", alias[i]);
            totalCarbs += String.format("%s.carbs", alias[i]);
        }
        
        String sql = "select"
                + selectedCols
                + " from"
                + selectedTables
                + " where"
                + catConditions;
        
        //Add the conditions for max and min calories, protein, and carbs
        if (maxCal != -1) {
           sql += String.format(" and (%s) > %d", totalCalories, minCal);
           sql += String.format(" and (%s) < %d", totalCalories, maxCal);
        }
        if (maxProt != -1) {
           sql += String.format(" and (%s) > %d", totalProtein, minProt);
           sql += String.format(" and (%s) < %d", totalProtein, maxProt);
        }
        if (maxCarb != -1) {
           sql += String.format(" and (%s) > %d", totalCarbs, minCarb);
           sql += String.format(" and (%s) < %d", totalCarbs, maxCarb);
        }
        sql += " order by rand() limit 1;";
        
        
        
        return null;
    }
}
