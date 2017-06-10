/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghincalculator;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author robertpepple
 */
public class GhinCalculator extends Application {
    
    private static void refreshScoreTableClick (int xPos, int yPos) throws AWTException {

        int clickX = xPos + 350;
        int clickY = yPos + 750;
        Robot r;
        r = new Robot();
        r.setAutoDelay(40);
        r.setAutoWaitForIdle(true);
        
        r.mouseMove(clickX, clickY);
        r.delay(200);
        r.mousePress( InputEvent.BUTTON1_MASK );
        r.delay(200);
        r.mouseRelease( InputEvent.BUTTON1_MASK );
        r.delay(200);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GHINCalculator.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        scene.getStylesheets().add
           (GhinCalculator.class.getResource("GhinCalculator.css").toExternalForm());
        stage.show();
        stage.setResizable(false);
        
        int xPos = (int) stage.getX();
        int yPos = (int) stage.getY();
        
        stage.requestFocus();
        
        try {
            refreshScoreTableClick(xPos, yPos);
        } catch (AWTException ex) {
            Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
