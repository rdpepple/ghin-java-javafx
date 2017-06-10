/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ghinDatabase;

/**
 *
 * @author robertpepple
 */
public class GolfScoreUsed extends GolfScore{
    private boolean used;

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
    
    public GolfScoreUsed(int id, String course, float rating, short slope, String date_played, short score, boolean used) {
        super(id, course, rating, slope, date_played, score);
        this.used = false;
    }

    public GolfScoreUsed(String course, float rating, short slope, String date_played, short score, boolean used) {
        super(course, rating, slope, date_played, score);
        this.used = false;
    }

    public GolfScoreUsed() {};
    
}
