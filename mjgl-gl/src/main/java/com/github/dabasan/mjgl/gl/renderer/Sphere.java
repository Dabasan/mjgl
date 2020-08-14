package com.github.dabasan.mjgl.gl.renderer;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;

/**
 * Sphere
 * 
 * @author Daba
 *
 */
public class Sphere {
	private Vector position;
	private float radius;
	private int numSlices;
	private int numStacks;
	private Color color;

	public Sphere() {
		position = new Vector();
		radius = 5.0f;
		numSlices = 16;
		numStacks = 16;
		color = Color.WHITE;
	}

	public Vector getPosition() {
		return position;
	}
	public float getRadius() {
		return radius;
	}
	public int getNumSlices() {
		return numSlices;
	}
	public int getNumStacks() {
		return numStacks;
	}
	public Color getColor() {
		return color;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public void setNumSlices(int numSlices) {
		this.numSlices = numSlices;
	}
	public void setNumStacks(int numStacks) {
		this.numStacks = numStacks;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
