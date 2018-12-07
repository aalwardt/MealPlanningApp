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
import java.util.Set;
import java.util.function.Predicate;
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
    
    //List containing the filtered lists for the meal comboboxes
    private List<FilteredList> mealFilters;
    
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
    
    private int getRemainingCalories(int index) {
        //Returns how many calories are available for the meal given in the index
        int remainingCalories = Integer.parseInt(maxCal.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                remainingCalories -= Integer.parseInt(mealsCals.get(i).getText());
            }
        }
        return remainingCalories;
    }
    
    private int getRemainingProtein(int index) {
        //Returns how many protein are available for the meal given in the index
        int remainingProtein = Integer.parseInt(maxProt.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                remainingProtein -= Integer.parseInt(mealsProts.get(i).getText());
            }
        }
        return remainingProtein;
    }
    
    private int getRemainingCarbs(int index) {
        //Returns how many carbs are available for the meal given in the index
        int remainingCarbs = Integer.parseInt(maxCarbs.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                remainingCarbs -= Integer.parseInt(mealsCarbs.get(i).getText());
            }
        }
        return remainingCarbs;
    }
    
    private int getRequiredCalories (int index) {
        //Returns how many calories are required to satisfy the meal plan
        int requiredCalories = Integer.parseInt(minCal.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                requiredCalories -= Integer.parseInt(mealsCals.get(i).getText());
            }
        }
        return requiredCalories;
    }
    
    private int getRequiredProtein (int index) {
        //Returns how many protein are required to satisfy the meal plan
        int requiredProtein = Integer.parseInt(minProt.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                requiredProtein -= Integer.parseInt(mealsProts.get(i).getText());
            }
        }
        return requiredProtein;
    }
    
    private int getRequiredCarbs (int index) {
        //Returns how many calories are required to satisfy the meal plan
        int requiredCarbs = Integer.parseInt(minCarbs.getText());
        for (int i = 0; i < 5; i++) {
            if (i != index) {
                requiredCarbs -= Integer.parseInt(mealsCarbs.get(i).getText());
            }
        }
        return requiredCarbs;
    }
    
    private void updateMealListFilters() {
        for (int i = 0; i < 5; i++) {
            //Check if all other meals are assigned
            boolean lastMeal = allOtherMealsSelected(i);
            //Get if protein and carb filtering is enabled
            boolean protFilter = protToggle.isSelected();
            boolean carbFilter = carbsToggle.isSelected();

            //Set up filter by meal type
            Meal.Category mealCategory = (Meal.Category) mealsCats.get(i).getSelectionModel().getSelectedItem();
            Predicate<Meal> compoundPredicate = m -> (m.getCategory().equals(mealCategory));
            
            //Predicates for meal stats
            final int remainingCalories = getRemainingCalories(i);
            Predicate<Meal> underRemainingCalories = m -> (m.getCalories() < remainingCalories);   
            compoundPredicate = compoundPredicate.and(underRemainingCalories);
            //If all other meals assigned, also require minimum calories
            if (lastMeal) {
                final int requiredCalories = getRequiredCalories(i);
                Predicate<Meal> overRequiredCalories = m -> (m.getCalories() > requiredCalories);
                compoundPredicate = compoundPredicate.and(overRequiredCalories);
            }
            
            if (protFilter) {
                final int remainingProtein = getRemainingProtein(i);
                Predicate<Meal> underRemainingProtein = m -> (m.getProtein() < remainingProtein);
                compoundPredicate = compoundPredicate.and(underRemainingProtein);
                if (lastMeal) {
                    final int requiredProtein = getRequiredProtein(i);
                    Predicate<Meal> overRequiredProtein = m -> (m.getProtein() > requiredProtein);
                    compoundPredicate = compoundPredicate.and(overRequiredProtein);
                }
            }
            
            if (carbFilter) {
                final int remainingCarbs = getRemainingCarbs(i);
                Predicate<Meal> underRemainingCarbs = m -> (m.getCarbs() < remainingCarbs);
                compoundPredicate = compoundPredicate.and(underRemainingCarbs);
                if (lastMeal) {
                    final int requiredCarbs = getRequiredCarbs(i);
                    Predicate<Meal> overRequiredCarbs = m -> (m.getCarbs() > requiredCarbs);
                    compoundPredicate = compoundPredicate.and(overRequiredCarbs);
                }
            }
            
            //Save the meal that was previously selected
            Meal oldMeal = meals[i];
            
            //Update the predicate
            mealFilters.get(i).setPredicate(compoundPredicate);
            
            //Retain previously selected meal when updating predicate
            mealsComboBox.get(i).getSelectionModel().select(oldMeal);
        }   
    }
    
    //Returns true if this is the last meal being selected
    private boolean allOtherMealsSelected(int current) {
        //Check all meals
        for (int i = 0; i < 5; i++) {
            //Skip if checking current meal slot
            if (i != current) {
                //Skip if category is "None"
                if (!mealsCats.get(i).getSelectionModel().getSelectedItem().equals(Meal.Category.None)) {
                    //If category is not None, and it's empty, return false
                    if (mealsComboBox.get(i).getSelectionModel().isEmpty()) {
                        return false;
                    }
                }
            }
        }
        //If previously not returned false, return true
        return true;
    }
    
    
    @FXML
    private void generateRandomPlan(ActionEvent event) {
        ArrayList<Meal.Category> categories = new ArrayList<>();
        for (ChoiceBox<Meal.Category> catBox : mealsCats) {
            Meal.Category currentCat = catBox.getSelectionModel().getSelectedItem();
            //If it's not none, add it to the list
            if (!currentCat.equals(Meal.Category.None))
                categories.add(currentCat);
        }
        
        ArrayList<Meal> mealPlan;
        int maxCalories = Integer.parseInt(maxCal.getText());
        int minCalories = Integer.parseInt(minCal.getText());
        int maxProtein;
        int minProtein;
        if (protToggle.isSelected()) {
            maxProtein = Integer.parseInt(maxProt.getText());
            minProtein = Integer.parseInt(minProt.getText());
        } else {
            maxProtein = -1;
            minProtein = -1;
        }
        int maxCarbo;
        int minCarbo;
        if (protToggle.isSelected()) {
            maxCarbo = Integer.parseInt(maxCarbs.getText());
            minCarbo = Integer.parseInt(minCarbs.getText());
        } else {
            maxCarbo = -1;
            minCarbo = -1;
        }
        mealPlan = MealDatabase.GenerateMealPlan(categories, minCalories, maxCalories, minProtein, minProtein, maxCarbo, minCarbo);
        
        //Select the items in each of the meal combo boxes
        int j = 0;
        for (int i = 0; i < 5; i++) {
            if(!mealsCats.get(i).getSelectionModel().getSelectedItem().equals(Meal.Category.None)) {
                mealsComboBox.get(i).getSelectionModel().select(mealPlan.get(j));
                j++;
            }
        }
        
        if (mealPlan.isEmpty()) {
            for (ComboBox<Meal> cbox : mealsComboBox) {
                cbox.getSelectionModel().clearSelection();
            }
        }
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
        
        mealFilters = Arrays.asList(meal1FilteredList, meal2FilteredList, meal3FilteredList, meal4FilteredList, meal5FilteredList);
        
        //Set the filtered lists for each of the ComboBoxes
        meal1ComboBox.setItems(meal1FilteredList);
        meal2ComboBox.setItems(meal2FilteredList);
        meal3ComboBox.setItems(meal3FilteredList);
        meal4ComboBox.setItems(meal4FilteredList);
        meal5ComboBox.setItems(meal5FilteredList);
        
        //Set the ComboBoxes to be auto complete
        /*
        FxUtil.autoCompleteComboBoxPlus(meal1ComboBox, (typedText, mealToCompare) -> mealToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        FxUtil.autoCompleteComboBoxPlus(meal2ComboBox, (typedText, mealToCompare) -> mealToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        FxUtil.autoCompleteComboBoxPlus(meal3ComboBox, (typedText, mealToCompare) -> mealToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        FxUtil.autoCompleteComboBoxPlus(meal4ComboBox, (typedText, mealToCompare) -> mealToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        FxUtil.autoCompleteComboBoxPlus(meal5ComboBox, (typedText, mealToCompare) -> mealToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        */
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
                meal1ComboBox.setValue(null);
                updateMealListFilters();
            }
        });
        meal2Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal2ComboBox.getSelectionModel().clearSelection();
                meal2ComboBox.setValue(null);
                updateMealListFilters();
            }
        });
        meal3Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal3ComboBox.getSelectionModel().clearSelection();
                meal3ComboBox.setValue(null);
                updateMealListFilters();
            }
        });
        meal4Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal4ComboBox.getSelectionModel().clearSelection();
                meal4ComboBox.setValue(null);
                updateMealListFilters();
            }
        });
        meal5Cat.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meal.Category>() {
            @Override
            public void changed(ObservableValue<? extends Meal.Category> observable, Meal.Category oldValue, Meal.Category newValue) {
                //Deselect entry from associated ComboBox
                meal5ComboBox.getSelectionModel().clearSelection();
                meal5ComboBox.setValue(null);
                updateMealListFilters();
            }
        });
        
        //Listners to ComboBoxes to update fields upon selections
        meal1ComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            Meal meal;
            if (newValue instanceof Meal) {
                meal = (Meal) newValue;
            } else {
                meal = null;
            }
            setMeal(meal, 0);
            updateMealListFilters();            
        });
        meal2ComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            Meal meal;
            if (newValue instanceof Meal) {
                meal = (Meal) newValue;
            } else {
                meal = null;
            }
            setMeal(meal, 1);
            updateMealListFilters();            
        });
        meal3ComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            Meal meal;
            if (newValue instanceof Meal) {
                meal = (Meal) newValue;
            } else {
                meal = null;
            }
            setMeal(meal, 2);
            updateMealListFilters();            
        });
        meal4ComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            Meal meal;
            if (newValue instanceof Meal) {
                meal = (Meal) newValue;
            } else {
                meal = null;
            }
            setMeal(meal, 3);
            updateMealListFilters();            
        });
        meal5ComboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Object> observable, Object oldValue, Object newValue) -> {
            Meal meal;
            if (newValue instanceof Meal) {
                meal = (Meal) newValue;
            } else {
                meal = null;
            }
            setMeal(meal, 4);
            updateMealListFilters();            
        });
        
        //Make maxCal field numeric only, update meal filters when it is changed
        maxCal.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                maxCal.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        minCal.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                minCal.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        //Set Initial Values
        maxCal.textProperty().set(String.valueOf(2000));
        minCal.textProperty().set(String.valueOf(0));

        maxProt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                maxProt.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        minProt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                minProt.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        //Set Initial Values
        maxProt.textProperty().set(String.valueOf(0));
        minProt.textProperty().set(String.valueOf(0));
        
        maxCarbs.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                maxCarbs.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        minCarbs.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*"))
                minCarbs.setText(newValue.replaceAll("[^\\d]", ""));
            updateMealListFilters();    
        });
        //Set Initial Values
        maxCarbs.textProperty().set(String.valueOf(0));
        minCarbs.textProperty().set(String.valueOf(0));
        
        
        //Listeners for changes in Protein and Carbs checkboxes
        protToggle.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            maxProt.disableProperty().set(!newValue);
            minProt.disableProperty().set(!newValue);
            updateMealListFilters();    
        });
        //Disable these initially
        maxProt.disableProperty().set(true);
        minProt.disableProperty().set(true);
            
        carbsToggle.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            maxCarbs.disableProperty().set(!newValue);
            minCarbs.disableProperty().set(!newValue);
            updateMealListFilters();    
        });
        //Disable these initially
        maxCarbs.disableProperty().set(true);
        minCarbs.disableProperty().set(true);
    }   
    
    @FXML
    private void openEditDB(ActionEvent event) throws IOException {
        //Open the EditDB window
        Parent root = FXMLLoader.load(getClass().getResource("fxml/EditDB.fxml"));
        Stage stage = new Stage();
        
        stage.setScene(new Scene(root));
        stage.show();
        
        //When the EditDB window is closed, update the meal database in this window
        stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                getMeals();
            }
        }));
    }

    
}
