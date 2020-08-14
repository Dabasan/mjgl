package com.github.dabasan.mjgl.gl.renderer;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;

/**
 * Point
 * 
 * @author Daba
 *
 */
public class Point {
	private Vector position;
	private Color color;

	public Point() {
		position = new Vector();
		color = Color.WHITE;
	}

	public Vector getPosition() {
		return position;
	}
	public Color getColor() {
		return color;
	}

	public void setPosition(Vector position) {
		this.position = position;
	}
	public void setColor(Color color) {
		this.color = color;
	}
}
