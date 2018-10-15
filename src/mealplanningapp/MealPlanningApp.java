/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author alexa
 */
public class MealPlanningApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         //Load the H2 database driver
        try {
            System.out.println("Loading H2 database driver...");
            Class.forName("org.h2.Driver");
            System.out.println("Load successful!");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Class not found: " + e.getMessage());
        }
        
        launch(args);
    }
    
}
