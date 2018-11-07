/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author alexa
 */
public class EditSPController implements Initializable {
    
    //Minimum and Maximum Fields
    @FXML
    private TextField maxCal;
    @FXML
    private TextField minCal;
    @FXML
    private TextField maxCarbs;
    @FXML
    private TextField minCarbs;
    @FXML
    private TextField maxProt;
    @FXML
    private TextField minProt;
    
    //Field Toggles
    @FXML
    private CheckBox carbsToggle;
    @FXML
    private CheckBox protToggle;
    @FXML
    private CheckBox calToggle;

    //Meal Category ChoiceBoxes
    @FXML
    private ChoiceBox<?> meal1Cat;
    @FXML
    private ChoiceBox<?> meal2Cat;
    @FXML
    private ChoiceBox<?> meal3Cat;
    @FXML
    private ChoiceBox<?> meal4Cat;
    @FXML
    private ChoiceBox<?> meal5Cat;
    
    //Meal Name ComboBoxes
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
    
    //Meal Calorie Labels
    @FXML
    private Label meal1Cal;
    @FXML
    private Label meal2Cal;
    @FXML
    private Label meal3Cal;
    @FXML
    private Label meal4Cal;
    @FXML
    private Label meal5Cal;
    
    //Meal Protein Labels
    @FXML
    private Label meal1Prot;
    @FXML
    private Label meal2Prot;
    @FXML
    private Label meal3Prot;
    @FXML
    private Label meal4Prot;
    @FXML
    private Label meal5Prot;
    
    //Meal Carb Labels
    @FXML
    private Label meal1Carbs;
    @FXML
    private Label meal2Carbs;
    @FXML
    private Label meal3Carbs;
    @FXML
    private Label meal4Carbs;
    @FXML
    private Label meal5Carbs;
    
    //Meal Random Buttons
    @FXML
    private Button meal1Random;
    @FXML
    private Button meal2Random;
    @FXML
    private Button meal3Random;
    @FXML
    private Button meal4Random;
    @FXML
    private Button meal5Random;
    
    //Total Cal, Prot, Carb
    @FXML
    private Label totalCal;
    @FXML
    private Label totalProt;
    @FXML
    private Label totalCarbs;
    
    //Randomize All
    @FXML
    private Button randomAllButton;

    
    private Meal[] meals = new Meal[5];
    
    //Lists containing each category of control
    private List<ChoiceBox> mealsCats;
    private List<ComboBox> mealsNames;
    private List<Button> mealsRandom;
    private List<Label> mealsCals;
    private List<Label> mealsProts;
    private List<Label> mealsCarbs;
    
    
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
        mealsCals.get(index).setText(String.valueOf(cal));
        mealsProts.get(index).setText(String.valueOf(prot));
        mealsCarbs.get(index).setText(String.valueOf(carb));
    }
    
    private void updateMaxValues() {
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mealsCats = Arrays.asList(meal1Cat, meal2Cat, meal3Cat, meal4Cat, meal5Cat);
        mealsNames = Arrays.asList(meal1Name, meal2Name, meal3Name, meal4Name, meal5Name);
        mealsRandom = Arrays.asList(meal1Random, meal2Random, meal3Random, meal4Random, meal5Random);
        mealsCals = Arrays.asList(meal1Cal, meal2Cal, meal3Cal, meal4Cal, meal5Cal);
        mealsProts = Arrays.asList(meal1Prot, meal2Prot, meal3Prot, meal4Prot, meal5Prot);
        mealsCarbs = Arrays.asList(meal1Carbs, meal2Carbs, meal3Carbs, meal4Carbs, meal5Carbs);
        System.out.println(meal1Cat);
        
        //Set up the meal choice blocks
        for (ChoiceBox cbox : mealsCats) {
            System.out.println(cbox);
            cbox.setItems(FXCollections.observableArrayList(Meal.Category.Breakfast, Meal.Category.Lunch, Meal.Category.Dinner, Meal.Category.Snack));
            
            //TODO: Add listener so that when this is changed, mealsNames is unselected
        }
        
    }    

    @FXML
    private void openEditDB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/EditDB.fxml"));
        Stage stage = new Stage();
        
        stage.setScene(new Scene(root));
        stage.show();
    }
    
}
