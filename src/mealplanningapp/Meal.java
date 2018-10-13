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
    //To do: Protein? Carbs?

    public Meal(String name, int calories, Category category){
        id = -1; //-1 indicates new ID
        this.name = name;
        this.calories = calories;
        this.category = category;
    }
    
    //Getters
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public Category getCategory() { return category; }
    
    @Override
    public String toString() { return getName(); }
    
}
