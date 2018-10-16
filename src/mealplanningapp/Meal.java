package mealplanningapp;

/**
 * This class encapsulates a Meal
 * @author alexa
 */
public class Meal {
    
    //Different categories for each meal type
    public enum Category {
        Breakfast, Lunch, Dinner, Snack
    }
    
    //ID number. Meals loaded from the database will have IDs.
    int id;
    
    //Private variables containing meal's properties
    private String name;
    private int calories;
    private Category category;
    private int protein;
    private int carbs;

    //Create a meal from an existing one (with an ID number)
    public Meal(int id, String name, Category category, int calories, int protein, int carbs){
        this.id = id;
        this.name = name;
        this.category = category;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
    }
    
    //Create a new meal without an ID number
    public Meal(String name, Category category, int calories, int protein, int carbs){
        this(-1, name, category, calories, protein, carbs); //Call full constructor with -1 as ID number
    }
    
    //Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public int getProtein() { return protein; }
    public int getCarbs() { return carbs; }
    public Category getCategory() { return category; }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %d calories, %dg Protein, %dg Carbs", name, category.toString(), calories, protein, carbs);
    }
    
}
