/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghincalculator;

import ghinDatabase.GhinPlusUsedMap;
import ghinDatabase.GolfCourse;
import ghinDatabase.GolfCourseDAO;
import ghinDatabase.GolfScore;
import ghinDatabase.GolfScoreDAO;
import ghinDatabase.GolfScoreUsed;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Labeled;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author robertpepple
 */
public class GHINCalculatorController implements Initializable {

    @FXML
    private ChoiceBox<String> golfCourseChoiceBox;
    @FXML
    private DatePicker playDatePicker;
    @FXML
    private TextField coursePlayedField;
    @FXML
    private TextField courseSlopeField;
    @FXML
    private TextField courseRatingField;
    @FXML
    private TextField scoreToPostField;
    @FXML
    private Button postScoreButton;
    @FXML
    private Button clearFormButton;
    @FXML
    private Label calculatedGhin;
    @FXML
    private TextArea formValidationTextField;
    @FXML
    private TableView<GolfScoreUsed> scoreHistoryTableView;
    @FXML
    private TableColumn<GolfScoreUsed, String> scoreTableDatesCol;
    @FXML
    private TableColumn<GolfScoreUsed, String> scoreTableCoursesCol;
    @FXML
    private TableColumn<GolfScoreUsed, Number> scoreTableSlopesCol;
    @FXML
    private TableColumn<GolfScoreUsed, Float> scoreTableRatingsCol;
    @FXML
    private TableColumn<GolfScoreUsed, Number> scoreTableScoresCol;
    @FXML
    private TableColumn<GolfScoreUsed, Boolean> scoreTableUsedCol;
    @FXML
    private SplitPane splitMain;
    @FXML
    private SplitPane splitH1;
    @FXML
    private SplitPane splitHc;

    private ObservableList<String> getGcList () {

        ObservableList<String> golfCourseList = FXCollections.observableArrayList("-- Select Golf Course --");
        GolfCourseDAO gcDao = new GolfCourseDAO();
        try {
            List<GolfCourse> gcList = gcDao.getAllGolfCourses();
            gcList.stream().forEach((gc) -> { golfCourseList.add(gc.getName()); });

        } catch (SQLException ex) {
            Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
     return golfCourseList;
    }

    private GolfCourse getGcParams (String gcName) {

        GolfCourse gc = new GolfCourse();
        GolfCourseDAO gcDao = new GolfCourseDAO();
        try {
            gc = gcDao.getGolfCourseParams(gcName);
        } catch (SQLException ex) {
            Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
     return gc;
    }

    private ObservableList<GolfScoreUsed> getScrList () {
        ObservableList<GolfScoreUsed> golfScoreList = FXCollections.observableArrayList();
        GolfScoreDAO scrDao = new GolfScoreDAO();

        try {
            List<GolfScore> scrList = scrDao.getAllGolfScores();
            
            scrList.stream().forEach((scr) -> {GolfScoreUsed golfScoreUsed;
                                               golfScoreUsed = new GolfScoreUsed();
                                               golfScoreUsed.setId(scr.getId());
                                               golfScoreUsed.setCourse(scr.getCourse());
                                               golfScoreUsed.setDate_played(scr.getDate_played());
                                               golfScoreUsed.setSlope(scr.getSlope());
                                               golfScoreUsed.setRating(scr.getRating());
                                               golfScoreUsed.setScore(scr.getScore());
                                               golfScoreUsed.setUsed(false);
                                               golfScoreList.add(golfScoreUsed); });
        } catch (SQLException ex) {
            Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return golfScoreList;
    }

    private void populateGHINAndScoreTable () {
        ObservableList<GolfScoreUsed> scoreList;
        scoreList = getScrList();
        GhinPlusUsedMap calculatedGhinObj = calculateGHIN(scoreList);
        calculatedGhin.setText(calculatedGhinObj.getGhinNumber());
        
        ObservableList<GolfScoreUsed> scoresUsedList = FXCollections.observableArrayList();
        scoresUsedList = calculatedGhinObj.getGolfScoresUsed();

        scoreTableDatesCol.setCellValueFactory(
            new PropertyValueFactory<>("date_played")
        );
        scoreTableCoursesCol.setCellValueFactory(
            new PropertyValueFactory<>("course")
        );
        scoreTableSlopesCol.setCellValueFactory(
            new PropertyValueFactory<>("slope")
        ); 
        scoreTableRatingsCol.setCellValueFactory(
            new PropertyValueFactory<>("rating")
        );             
        scoreTableScoresCol.setCellValueFactory(
            new PropertyValueFactory<>("score")
        ); 
        scoreTableUsedCol.setCellValueFactory(
            new PropertyValueFactory<>("used")
        ); 
        
        scoreHistoryTableView.setItems(scoresUsedList);

        scoreHistoryTableView.setRowFactory((TableView<GolfScoreUsed> row) -> new TableRow<GolfScoreUsed>(){
            @Override
            public void updateItem(GolfScoreUsed item, boolean empty){
                super.updateItem(item, empty);
                
                if (item == null || empty) {
                    setStyle("");
                } else {
                    //Now 'item' has all the info of the Person in this row
                    if (item.isUsed()) {
                        //We apply now the changes in all the cells of the row
                        for(int i=0; i<getChildren().size();i++){
                            ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #006600; -fx-text-fill: #ffffff");
                        }
                        scoreHistoryTableView.getColumns().get(0).setVisible(false);
                        scoreHistoryTableView.getColumns().get(0).setVisible(true);
                    } else {
                        if(getTableView().getSelectionModel().getSelectedItems().contains(item)){
                            for(int i=0; i<getChildren().size();i++){
                                ((Labeled) getChildren().get(i)).setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
                            }
                        }
                        else{
                            for(int i=0; i<getChildren().size();i++){
                                ((Labeled) getChildren().get(i)).setStyle("-fx-text-fill: #000000");
                            }
                        }
                    }
                }
            }
        });        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String pattern = "yyyy-MM-dd";
    
        StringConverter<LocalDate> converter;
        converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
            
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        playDatePicker.setConverter(converter);
        playDatePicker.setPromptText(pattern.toLowerCase());
        
        golfCourseChoiceBox.setValue("-- Select Golf Course --");
        golfCourseChoiceBox.setItems(getGcList());
        
        populateGHINAndScoreTable();
    }

    @FXML
    private void handleCourseChoice(MouseEvent event) {   
        golfCourseChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            System.out.println(golfCourseChoiceBox.getItems().get((Integer) number2));
            String selectedCourse;
            selectedCourse = golfCourseChoiceBox.getItems().get((Integer) number2);
            GolfCourse selectedCourseObj = getGcParams(selectedCourse);
            
            coursePlayedField.setText(selectedCourseObj.getName());
            courseSlopeField.setText(Short.toString(selectedCourseObj.getSlope()));
            courseRatingField.setText(Float.toString(selectedCourseObj.getRating()));
        });        
    }
    
    @FXML
    private void handlePostScore(MouseEvent event) {
        formValidationTextField.clear();
        List<String> validationMessages;
        validationMessages = new ArrayList();
        boolean fieldsValidated = true;
        LocalDate playDate = playDatePicker.getValue();
// Check for null date from form
        String datePlayed = "";
        if (playDatePicker.getValue() != null) {
            datePlayed = playDate.toString();            
        } else {
            validationMessages.add("Must select a play date\n");
            fieldsValidated = false;
        }
// Check for properly formatted course name (name:tee box)
        String coursePlayed = "";
        if (coursePlayedField.getText().matches("^.*:.*$")) {
            coursePlayed = coursePlayedField.getText();            
        } else {
            validationMessages.add("Course must be formatted as <name:teebox>\n");
            fieldsValidated = false;
        }
// Check for proper rating (60.0 - 75.0)
        float floatRating;
        if ("".equals(courseRatingField.getText())) {
            floatRating = (float) 0.0;
        } else {
            floatRating = Float.parseFloat(courseRatingField.getText());
        }
        String courseRating = "";
        if ((floatRating >= 65.0 && floatRating <= 75.0)) {        
            courseRating = courseRatingField.getText();
        } else {
            validationMessages.add("Rating must be between 65.0 and 75.0\n");
            fieldsValidated = false;            
        }
// Check for proper slope (100 - 140)
        short shortSlope;
        if("".equals(courseSlopeField.getText())) {
            shortSlope = 0;
        } else {           
            shortSlope = Short.parseShort(courseSlopeField.getText());
        }
        String courseSlope = "";
        if ((shortSlope >= 100 && shortSlope <= 140)) {        
            courseSlope = courseSlopeField.getText();
        } else {
            validationMessages.add("Slope must be between 100 and 140\n");
            fieldsValidated = false;            
        }
// Check for proper score entry (55 - 110)
        short shortScore;
        if("".equals(courseSlopeField.getText())) {
            shortScore = 0;
        } else {
            shortScore = Short.parseShort(scoreToPostField.getText());
        }
        String scoreToPost = "";
        if ((shortScore >= 55 && shortScore <= 110)) {        
            scoreToPost = scoreToPostField.getText();
        } else {
            validationMessages.add("Score must be between 55 and 110\n");
            fieldsValidated = false;            
        }

        if (fieldsValidated) {
// Check course name for existence in DB. Add if not there.
            GolfCourseDAO gcDao = new GolfCourseDAO();
            GolfScoreDAO scrDao = new GolfScoreDAO();
            
            try {
                if (!gcDao.checkForGolfCourse(coursePlayed)) {
// Add new golf course to DB
                    gcDao.addNewGolfCourse(coursePlayed, floatRating, shortSlope);                   
                }                
            } catch (SQLException ex) {
                Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
// Generate short course name (:teebox removed)
            String shortCoursePlayed;
            shortCoursePlayed = coursePlayed.split(":")[0];
            
// Add new score to DB
            try {
                scrDao.addNewGolfScore(shortCoursePlayed, floatRating, shortSlope, datePlayed, shortScore);
            } catch (SQLException ex) {
                Logger.getLogger(GHINCalculatorController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
// Recalculate GHIN and regenerate score history table
            populateGHINAndScoreTable();
        } else {
            validationMessages.stream().forEach((message) -> {formValidationTextField.appendText(message); });
        }
    }

    @FXML
    private void handleClearForm(MouseEvent event) {
        playDatePicker.getEditor().clear();
        coursePlayedField.setText("");
        courseSlopeField.setText("");
        courseRatingField.setText("");
        scoreToPostField.setText("");
        formValidationTextField.clear();
    }

    private static Map<Integer, Float> sortByScoreDifferential(Map<Integer, Float> unsortMap) {

    // 1. Convert Map to List of Map
        List<Map.Entry<Integer, Float>> list =
                new LinkedList<>(unsortMap.entrySet());

        // 2. Sort list with Collections.sort(), provide a custom Comparator
        //    Try switch the o1 o2 position for a different order
        Collections.sort(list, (Map.Entry<Integer, Float> o1, Map.Entry<Integer, Float> o2) -> (o1.getValue()).compareTo(o2.getValue()));

        // 3. Loop the sorted list and put it into a new insertion order Map LinkedHashMap
        Map<Integer, Float> sortedMap = new LinkedHashMap<>();
        list.stream().forEach((entry) -> {
            sortedMap.put(entry.getKey(), entry.getValue());
        });
        return sortedMap;
    }

    private GhinPlusUsedMap calculateGHIN(ObservableList<GolfScoreUsed> scoreList) {
        Map<Integer, Float> scoreDiffs = new HashMap<>();
        float scoreDiff;
        
// create id->score differential map
        
        for(GolfScoreUsed gscr:scoreList) {

// calculate score differential
                scoreDiff = (float) (((gscr.getScore() - gscr.getRating())*113.0)/gscr.getSlope());
                scoreDiffs.put(gscr.getId(), scoreDiff);
        }
        Map<Integer, Float> sortedScoreDiffs = sortByScoreDifferential(scoreDiffs);

        int n = 0;
        float totalScoreDiffs = (float) 0.0;
        int[] usedScoreIds = new int[10];
        for (Map.Entry<Integer, Float> entry : sortedScoreDiffs.entrySet()) {
            if(n < 10) {
                totalScoreDiffs = totalScoreDiffs + entry.getValue();
                usedScoreIds[n] = entry.getKey();
                n++;
            } else {
            	break;
            }
        }
        double calculatedHandicap = ((totalScoreDiffs/10.0)*0.96);
        
        double upGhin = calculatedHandicap * 10;
        upGhin = java.lang.Math.floor(upGhin);
        calculatedHandicap = upGhin/10;
        
        Formatter fmt = new Formatter();
        fmt.format("%4.1f", calculatedHandicap);
        String calculatedGhinStr = fmt.toString();
        
        GhinPlusUsedMap calculatedGhinObj = new GhinPlusUsedMap();
        calculatedGhinObj.setGhinNumber(calculatedGhinStr);
        
// Add used score flags to scoreList

        int usedFlag;
        for(GolfScoreUsed gscr:scoreList) {
                usedFlag = 0;
                for (n=0;n<10;n++) {
                    if (usedScoreIds[n] == gscr.getId()) {
                        gscr.setUsed(true);
                        break;
                    }
                }
        }
        calculatedGhinObj.setUsedScoreDiffIds(scoreList);

        return calculatedGhinObj;
    }
}
