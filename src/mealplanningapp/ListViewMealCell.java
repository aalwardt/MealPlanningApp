/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import javafx.scene.control.ListCell;

/**
 *
 * @author alexa
 */
public class ListViewMealCell extends ListCell<Meal>{
    @Override
    public void updateItem(Meal meal, boolean empty) {
        super.updateItem(meal, empty);
        
        if(meal != null) {
            MealData data = new MealData(); //Create ListCell
            data.setInfo(meal); //Update with info on meal
            setGraphic(data.getPane()); //Set this item's graphic to the new pane
        }
    }
    
}
