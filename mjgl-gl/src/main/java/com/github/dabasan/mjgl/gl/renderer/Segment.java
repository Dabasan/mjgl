package com.github.dabasan.mjgl.gl.renderer;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;

/**
 * Segment
 * 
 * @author Daba
 *
 */
public class Segment {
	private Vector position1;
	private Vector position2;
	private Color color1;
	private Color color2;

	public Segment() {
		position1 = new Vector();
		position2 = new Vector();
		color1 = Color.WHITE;
		color2 = Color.WHITE;
	}

	public Vector getPosition1() {
		return position1;
	}
	public Vector getPosition2() {
		return position2;
	}
	public Color getColor1() {
		return color1;
	}
	public Color getColor2() {
		return color2;
	}

	public void setPosition1(Vector position1) {
		this.position1 = position1;
	}
	public void setPosition2(Vector position2) {
		this.position2 = position2;
	}
	public void setColor1(Color color1) {
		this.color1 = color1;
	}
	public void setColor2(Color color2) {
		this.color2 = color2;
	}
}
