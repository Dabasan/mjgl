package com.github.dabasan.mjgl.gl.renderer;

import com.github.dabasan.mjgl.gl.Color;

/**
 * Point 2D
 * 
 * @author Daba
 *
 */
public class Point2D {
	private float x;
	private float y;
	private Color color;

	public Point2D() {
		x = 0.0f;
		y = 0.0f;
		color = Color.WHITE;
	}

	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Color getColor() {
		return color;
	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
