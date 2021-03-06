/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alexa
 */
public class EditDBController implements Initializable {

    @FXML
    private TextField searchField;
    @FXML
    private ListView<Meal> mealListView;
    @FXML
    private Button removeEntryButton;
    @FXML
    private Button updateEntryButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField calorieField;
    @FXML
    private TextField proteinField;
    @FXML
    private TextField carbField;
    @FXML
    private ChoiceBox<Meal.Category> categoryBox;
    @FXML
    private Button newEntryButton;
    @FXML
    private DatePicker datePickr;
    
    private ArrayList<Meal> mealList;
    ObservableList observableList = FXCollections.observableArrayList();
    FilteredList<Meal> filteredList = new FilteredList<>(observableList, s -> true);

    
    @FXML
    private void onRemoveSelected(ActionEvent event) {
        //Get the selected list view item
        Meal meal = mealListView.getSelectionModel().getSelectedItem();
        if (meal != null) {
            //Remove it from the database
            MealDatabase.RemoveMeal(meal);
            //Update the list
            updateList();
        }
    }
    
    @FXML
    private void onAddNewEntry(ActionEvent event) {
        //Create new meal object from inputs
        Meal meal = getMealFromInput();
        
        //Add it to the database
        MealDatabase.InsertMeal(meal);
        //Update the list
        updateList();
    }
    
    @FXML
    private void onUpdateEntry(ActionEvent event) {
        //Create new meal object from inputs
        Meal newMeal = getMealFromInput();
        
        //Get the selected list view item
        Meal oldMeal = mealListView.getSelectionModel().getSelectedItem();
        if (oldMeal != null) {
            //Remove it from the database
            MealDatabase.UpdateMeal(oldMeal, newMeal);
            //Update the list
            updateList();
        }
    }
    
    //Creates a meal object based on the input fields on the window
    private Meal getMealFromInput() {
        //Get input from fields
        String name = nameField.getText();
        Meal.Category category = categoryBox.getSelectionModel().getSelectedItem();
        int calories = Integer.parseInt(calorieField.getText());
        int protein = Integer.parseInt(proteinField.getText());
        int carbs = Integer.parseInt(carbField.getText());
        
        //Create and return new meal object
        return new Meal(name, category, calories, protein, carbs);
    }
    
    private void updateList() {
        mealList = MealDatabase.GetAllMeals();
        observableList.setAll(mealList);   
    }
    
    /**
     * Initializes the controller class. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateList();
        //Populate meal list
        mealListView.setItems(filteredList);
        
        //Populate choice box
        categoryBox.setItems(FXCollections.observableArrayList(Meal.Category.Breakfast, Meal.Category.Lunch, Meal.Category.Dinner, Meal.Category.Snack));
        categoryBox.getSelectionModel().selectFirst();
        
        //Add listener for changes in search field
        searchField.textProperty().addListener(obs -> { 
            String filter = searchField.getText().toLowerCase();
            if (filter == null || filter.length() == 0) {
                filteredList.setPredicate(s -> true);
            } else {
                filteredList.setPredicate(s -> s.getName().toLowerCase().contains(filter));
            }
        });
        
        //Add listener for when a new meal is selected from list
        mealListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldMeal, Meal newMeal) {
                if (newMeal != null) {
                    //Update input fields to use data from selected meal
                    nameField.setText(newMeal.getName());
                    categoryBox.getSelectionModel().select(newMeal.getCategory());
                    calorieField.setText(String.valueOf(newMeal.getCalories()));
                    proteinField.setText(String.valueOf(newMeal.getProtein()));
                    carbField.setText(String.valueOf(newMeal.getCarbs()));
                }
            }
        });
        
        //Force the calorie, protein, and carb fields to be numeric only
        calorieField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                calorieField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        proteinField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                proteinField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        carbField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                carbField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }        
}
