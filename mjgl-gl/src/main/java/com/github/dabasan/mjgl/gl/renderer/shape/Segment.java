package com.github.dabasan.mjgl.gl.renderer.shape;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;

/**
 * Segment
 * 
 * @author Daba
 *
 */
public class Segment {
	private Point point1;
	private Point point2;

	public Segment() {
		point1 = new Point();
		point2 = new Point();
	}

	public Point getPoint1() {
		return point1;
	}
	public Point getPoint2() {
		return point2;
	}

	public void setPoint1(Point point) {
		point1 = point;
	}
	public void setPoint2(Point point) {
		point2 = point;
	}
	public void setPositions(Vector position1, Vector position2) {
		point1.setPosition(position1);
		point2.setPosition(position2);
	}
	public void setColors(Color color1, Color color2) {
		point1.setColor(color1);
		point2.setColor(color2);
	}
}
