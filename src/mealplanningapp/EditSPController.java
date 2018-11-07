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
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;

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
    private ChoiceBox<Meal.Category> meal1Cat;
    @FXML
    private ChoiceBox<Meal.Category> meal2Cat;
    @FXML
    private ChoiceBox<Meal.Category> meal3Cat;
    @FXML
    private ChoiceBox<Meal.Category> meal4Cat;
    @FXML
    private ChoiceBox<Meal.Category> meal5Cat;
    
    //Meal ComboBoxes
    @FXML
    private ComboBox<Meal> meal1ComboBox;
    @FXML
    private ComboBox<Meal> meal2ComboBox;
    @FXML
    private ComboBox<Meal> meal3ComboBox;
    @FXML
    private ComboBox<Meal> meal4ComboBox;
    @FXML
    private ComboBox<Meal> meal5ComboBox;
    
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
    private List<ComboBox> mealsComboBox;
    private List<Button> mealsRandom;
    private List<Label> mealsCals;
    private List<Label> mealsProts;
    private List<Label> mealsCarbs;
    
    ObservableList observableList = FXCollections.observableArrayList();
    FilteredList<Meal> meal1FilteredList = new FilteredList<>(observableList, s -> false);
    FilteredList<Meal> meal2FilteredList = new FilteredList<>(observableList, s -> false);
    FilteredList<Meal> meal3FilteredList = new FilteredList<>(observableList, s -> false);
    FilteredList<Meal> meal4FilteredList = new FilteredList<>(observableList, s -> false);
    FilteredList<Meal> meal5FilteredList = new FilteredList<>(observableList, s -> false);
    
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
        
        updateTotalValues();
    }
    
    private void updateTotalValues() {
        
        int sumCal = 0;
        int sumProt = 0;
        int sumCarb = 0;
        
        for (int i = 0; i < 5; i++) {
            sumCal += Integer.parseInt(mealsCals.get(i).getText());
            sumProt += Integer.parseInt(mealsProts.get(i).getText());
            sumCarb += Integer.parseInt(mealsCarbs.get(i).getText());
        }
        
        totalCal.setText(String.valueOf(sumCal));
        totalProt.setText(String.valueOf(sumProt));
        totalCarbs.setText(String.valueOf(sumCarb));
    }
    
    private void getMeals() {
        observableList.setAll(MealDatabase.GetAllMeals());   
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Load all meals from the database
        getMeals();
        
        //Setup control list objects
        mealsCats = Arrays.asList(meal1Cat, meal2Cat, meal3Cat, meal4Cat, meal5Cat);
        mealsComboBox = Arrays.asList(meal1ComboBox, meal2ComboBox, meal3ComboBox, meal4ComboBox, meal5ComboBox);
        mealsRandom = Arrays.asList(meal1Random, meal2Random, meal3Random, meal4Random, meal5Random);
        mealsCals = Arrays.asList(meal1Cal, meal2Cal, meal3Cal, meal4Cal, meal5Cal);
        mealsProts = Arrays.asList(meal1Prot, meal2Prot, meal3Prot, meal4Prot, meal5Prot);
        mealsCarbs = Arrays.asList(meal1Carbs, meal2Carbs, meal3Carbs, meal4Carbs, meal5Carbs);
        
        //Set the filtered lists for each of the ComboBoxes
        meal1ComboBox.setItems(meal1FilteredList);
        meal2ComboBox.setItems(meal2FilteredList);
        meal3ComboBox.setItems(meal3FilteredList);
        meal4ComboBox.setItems(meal4FilteredList);
        meal5ComboBox.setItems(meal5FilteredList);
        
        //Set up the meal choice blocks
        for (int i = 0; i < mealsCats.size(); i++) {
            mealsCats.get(i).setItems(FXCollections.observableArrayList(Meal.Category.None, Meal.Category.Breakfast, Meal.Category.Lunch, Meal.Category.Dinner, Meal.Category.Snack));
            mealsCats.get(i).getSelectionModel().selectFirst();
        }
        
        //Listeners to category ChoiceBoxes to deselect to unselect meal ComboBox
        meal1Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal1ComboBox.getSelectionModel().clearSelection();
                meal1FilteredList.setPredicate(s -> (s.getCategory().equals(newValue)));
            }
        });
        meal2Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal2ComboBox.getSelectionModel().clearSelection();
                meal2FilteredList.setPredicate(s -> (s.getCategory().equals(newValue)));
            }
        });
        meal3Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal3ComboBox.getSelectionModel().clearSelection();
                meal3FilteredList.setPredicate(s -> (s.getCategory().equals(newValue)));
            }
        });
        meal4Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal4ComboBox.getSelectionModel().clearSelection();
                meal4FilteredList.setPredicate(s -> (s.getCategory().equals(newValue)));
            }
        });
        meal5Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal5ComboBox.getSelectionModel().clearSelection();
                meal5FilteredList.setPredicate(s -> (s.getCategory().equals(newValue)));
            }
        });
        
        //Listners to ComboBoxes to update fields upon selections
        meal1ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
                setMeal(newValue, 0);
            }            
        });
        meal2ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
                setMeal(newValue, 1);
            }            
        });
        meal3ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
                setMeal(newValue, 2);
            }            
        });
        meal4ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
                setMeal(newValue, 3);
            }            
        });
        meal5ComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal>() {
            @Override
            public void changed(ObservableValue<? extends Meal> observable, Meal oldValue, Meal newValue) {
                setMeal(newValue, 4);
            }            
        });
    }   
    
    @FXML
    private void openEditDB(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/EditDB.fxml"));
        Stage stage = new Stage();
        
        stage.setScene(new Scene(root));
        stage.show();
        
        stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                getMeals();
            }
        }));
    }
    
}
