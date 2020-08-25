package com.github.dabasan.mjgl.gl.renderer.shape;

import com.github.dabasan.mjgl.gl.Color;

/**
 * Circle 2D
 * 
 * @author Daba
 *
 */
public class Circle2D {
	private Point2D center;
	private float radius;
	private Color color;
	private int numDivisions;

	public Circle2D() {
		center = new Point2D();
		radius = 0.1f;
		color = Color.WHITE;
		numDivisions = 32;
	}

	public Point2D getCenter() {
		return center;
	}
	public float getRadius() {
		return radius;
	}
	public Color getColor() {
		return color;
	}
	public int getNumDivisions() {
		return numDivisions;
	}

	public void setCenter(Point2D center) {
		this.center = center;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	public void setNumDivisions(int numDivisions) {
		this.numDivisions = numDivisions;
	}
}
