package com.github.dabasan.mjgl.gl.renderer;

import com.github.dabasan.mjgl.gl.Color;

/**
 * Segment 2D
 * 
 * @author Daba
 *
 */
public class Segment2D {
	private Point2D point1;
	private Point2D point2;

	public Segment2D() {
		point1 = new Point2D();
		point2 = new Point2D();
	}

	public Point2D getPoint1() {
		return point1;
	}
	public Point2D getPoint2() {
		return point2;
	}

	public void setPoint1(Point2D point1) {
		this.point1 = point1;
	}
	public void setPoint2(Point2D point2) {
		this.point2 = point2;
	}
	public void setPositions(float x1, float y1, float x2, float y2) {
		point1.setPosition(x1, y1);
		point2.setPosition(x2, y2);
	}
	public void setColors(Color color1, Color color2) {
		point1.setColor(color1);
		point2.setColor(color2);
	}
}
