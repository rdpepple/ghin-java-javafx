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
public class GolfCourse {
	private int id;
	private String name;
	private float rating;
	private short slope;

	public GolfCourse(int id, String name, float rating, short slope) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.slope = slope;
	}

	public GolfCourse(String name, float rating, short slope) {
		super();
		this.name = name;
		this.rating = rating;
		this.slope = slope;
	}

	public GolfCourse() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String dumpCourse() {
		return "Record ID: " + id + ", Course name: " + name + ", Slope: " + slope + ", Rating: " + rating;
	}
}

