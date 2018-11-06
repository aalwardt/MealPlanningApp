/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author alexa
 */
public class EditSPController implements Initializable {

    @FXML
    private TextField maxCarbs;
    @FXML
    private Button meal1Random;
    @FXML
    private Button meal2Random;
    @FXML
    private Button meal3Random;
    @FXML
    private Button meal4Random;
    @FXML
    private ChoiceBox<?> meal1Cat;
    @FXML
    private ChoiceBox<?> meal2Cat;
    @FXML
    private ChoiceBox<?> meal3Cat;
    @FXML
    private ChoiceBox<?> meal4Cat;
    @FXML
    private CheckBox carbsToggle;
    @FXML
    private CheckBox protToggle;
    @FXML
    private CheckBox calToggle;
    @FXML
    private ChoiceBox<?> meal5Cat;
    @FXML
    private Button meal5Random;
    @FXML
    private Button randomAllButton;
    @FXML
    private TextField minCarbs;
    @FXML
    private TextField maxProt;
    @FXML
    private TextField maxCal;
    @FXML
    private TextField minCal;
    @FXML
    private TextField minProt;
    @FXML
    private Label meal1Cal;
    @FXML
    private Label meal1Prot;
    @FXML
    private Label meal1Carbs;
    @FXML
    private Label meal2Cal;
    @FXML
    private Label meal2Prot;
    @FXML
    private Label meal2Carbs;
    @FXML
    private Label meal3Cal;
    @FXML
    private Label meal3Prot;
    @FXML
    private Label meal3Carbs;
    @FXML
    private Label meal4Cal;
    @FXML
    private Label meal5Cal;
    @FXML
    private Label totalCal;
    @FXML
    private Label meal4Prot;
    @FXML
    private Label meal5Prot;
    @FXML
    private Label totalProt;
    @FXML
    private Label meal4Carbs;
    @FXML
    private Label meal5Carbs;
    @FXML
    private Label totalCarbs;
    @FXML
    private ComboBox<?> meal1Name;
    @FXML
    private ComboBox<?> meal2Name;
    @FXML
    private ComboBox<?> meal3Name;
    @FXML
    private ComboBox<?> meal4Name;
    @FXML
    private ComboBox<?> meal5Name;
    
    private Meal[] meals = new Meal[5];
    
    //Arrays containing each category of control
    private ChoiceBox[] mealsCats = {meal1Cat, meal2Cat, meal3Cat, meal4Cat, meal5Cat};
    private ComboBox[] mealsNames = {meal1Name, meal2Name, meal3Name, meal4Name, meal5Name};
    private Button[] mealsRandom = {meal1Random, meal2Random, meal3Random, meal4Random, meal5Random};
    private Label[] mealsCals = {meal1Cal, meal2Cal, meal3Cal, meal4Cal, meal5Cal};
    private Label[] mealsProts = {meal1Prot, meal2Prot, meal3Prot, meal4Prot, meal5Prot};
    private Label[] mealsCarbs = {meal1Carbs, meal2Carbs, meal3Carbs, meal4Carbs, meal5Carbs};
    
    private void setMeal(Meal meal, int index) {
        meals[index] = meal;
        
        int cal;
        int prot;
        int carb;
        
        if(meal == null) {
            //If meal is null, just set it all to 0
            cal = 0;
            prot = 0;
            carb = 0;
        } else {
            //Otherwise, grab the values from the meal
            cal = meal.getCalories();
            prot = meal.getProtein();
            carb = meal.getCarbs();
        }
        
        //Update the proper entries
        mealsCals[index].setText(String.valueOf(cal));
        mealsProts[index].setText(String.valueOf(prot));
        mealsCarbs[index].setText(String.valueOf(carb));
    }
    
    private void updateMaxValues() {
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Set up the meal choice blocks
        for (ChoiceBox cbox : mealsCats) {
            cbox.setItems(FXCollections.observableArrayList(Meal.Category.Breakfast, Meal.Category.Lunch, Meal.Category.Dinner, Meal.Category.Snack));
            
            //TODO: Add listener so that when this is changed, mealsNames is unselected
        }
        
    }    
    
}
