/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import java.io.IOException;
/**
 *
 * @author alexa
 */
public class MealData {
    
    @FXML
    private GridPane gPane;
    @FXML
    private Label name;
    @FXML
    private Label category;
    @FXML
    private Label calories;
    
    public MealData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MealListCell.fxml"));
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void setInfo(Meal meal) {
        name.setText(meal.getName());
        category.setText(meal.getCategory().toString());
        calories.setText(String.format("%d calories", meal.getCalories()));
        //TODO set icon based on category
        //Possibly have images for each meal in database later?
    }
    
    public GridPane getPane() {
        return gPane;
    }
    
}
