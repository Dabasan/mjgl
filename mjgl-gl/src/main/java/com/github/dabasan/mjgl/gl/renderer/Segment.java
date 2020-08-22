package com.github.dabasan.mjgl.gl.renderer;

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
}
