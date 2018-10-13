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
    
    //Private variables containing meal's properties
    private String name;
    private int calories;
    private Category category;
    //To do: Protein? Carbs?

    public Meal(String name, int calories, Category category){
        this.name = name;
        this.calories = calories;
        this.category = category;
    }
    
    //Getters
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public Category getCategory() { return category; }
}
