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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

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
    private TextField nameField;
    @FXML
    private TextField calorieField;
    @FXML
    private ChoiceBox<Meal.Category> categoryBox;
    @FXML
    private Button newEntryButton;
    @FXML
    private Button closeButton;

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
        //Get input from fields
        String name = nameField.getText();
        Meal.Category category = categoryBox.getSelectionModel().getSelectedItem();
        int calories = Integer.parseInt(calorieField.getText());
        
        //Create new meal object
        Meal meal = new Meal(name, calories, category);
        //Add it to the database
        MealDatabase.InsertMeal(meal);
        //Update the list
        updateList();
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
        
        //Force the calorie field to be numeric only
        calorieField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                calorieField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }    
    
}
