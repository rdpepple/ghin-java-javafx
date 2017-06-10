/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghinDatabase;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author robertpepple
 */
public class GhinPlusUsedMap {
    private ObservableList<GolfScoreUsed> scoresUsed = FXCollections.observableArrayList();
    private String ghinNumber;

    public GhinPlusUsedMap() {}

    public ObservableList<GolfScoreUsed> getGolfScoresUsed() {
        return scoresUsed;
    }

    public String getGhinNumber() {
        return ghinNumber;
    }

    public void setUsedScoreDiffIds(ObservableList golfScoresUsed) {
        this.scoresUsed = golfScoresUsed;
    }

    public void setGhinNumber(String ghinNumber) {
        this.ghinNumber = ghinNumber;
    }
}
