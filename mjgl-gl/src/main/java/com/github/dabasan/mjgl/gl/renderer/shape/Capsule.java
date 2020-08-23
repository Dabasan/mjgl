package com.github.dabasan.mjgl.gl.renderer.shape;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;

/**
 * Capsule
 * 
 * @author Daba
 *
 */
public class Capsule {
	private Vector position1;
	private Vector position2;
	private float radius;
	private int numSlices;
	private int numStacks;
	private Color color;

	public Capsule() {
		position1 = new Vector();
		position2 = new Vector();
		radius = 5.0f;
		numSlices = 16;
		numStacks = 16;
		color = Color.WHITE;
	}

	public Vector getPosition1() {
		return position1;
	}
	public Vector getPosition2() {
		return position2;
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

	public void setPosition1(Vector position1) {
		this.position1 = position1;
	}
	public void setPosition2(Vector position2) {
		this.position2 = position2;
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
