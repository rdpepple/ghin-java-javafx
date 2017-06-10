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
public class GolfScore {
    private int id;
    private String course;
    private float rating;
    private short slope;
    private String date_played;
    private short score;

    public GolfScore(int id, String course, float rating, short slope, String date_played, short score) {
            super();
            this.id = id;
            this.course = course;
            this.rating = rating;
            this.slope = slope;
            this.date_played = date_played;
            this.score = score;
    }

    public GolfScore(String course, float rating, short slope, String date_played, short score) {
            super();
            this.course = course;
            this.rating = rating;
            this.slope = slope;
            this.date_played = date_played;
            this.score = score;
    }

    public GolfScore() {}

    public Integer getId() {
            return id;
    }

    public void setId(int id) {
            this.id = id;
    }

    public String getCourse() {
            return course;
    }

    public void setCourse(String course) {
            this.course = course;
    }

    public float getRating() {
            return rating;
    }

    public void setRating(float rating) {
            this.rating = rating;
    }

    public short getSlope() {
            return slope;
    }

    public void setSlope(short slope) {
            this.slope = slope;
    }

    public String getDate_played() {
            return date_played;
    }

    public void setDate_played(String date_played) {
            this.date_played = date_played;
    }

    public short getScore() {
            return score;
    }

    public void setScore(short score) {
            this.score = score;
    }	

    public String dumpScore() {
            return "Record ID: " + id + ", Course name: " + course + ", Slope: " + slope + ", Rating: " + rating + ", Date: " + date_played + ", Score: " + score;
    }    
}
