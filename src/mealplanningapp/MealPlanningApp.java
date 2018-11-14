/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mealplanningapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author alexa
 */
public class MealPlanningApp extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("fxml/EditSP.fxml"));
        
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/mealplanningapp/css/practice_theme.css");
        stage.setScene(scene);
        stage.show();
        
        //When main window is closed, exit application
        stage.setOnCloseRequest((new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
            }
        }));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {       
        launch(args);
    }
    
}
