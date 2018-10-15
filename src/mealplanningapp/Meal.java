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

    //Create a meal from an existing one (with an ID number)
    public Meal(int id, String name, int calories, Category category){
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.category = category;
    }
    
    //Create a new meal without an ID number
    public Meal(String name, int calories, Category category){
        this(-1, name, calories, category); //Call full constructor with -1 as ID number
    }
    
    //Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCalories() { return calories; }
    public Category getCategory() { return category; }
    
    @Override
    public String toString() {
        return String.format("%s, %s, %d calories", name, category.toString(), calories);
    }
    
}
