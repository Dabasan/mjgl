package com.github.dabasan.mjgl.math;

/**
 * Math functions
 * 
 * @author Daba
 *
 */
public class MathFunctions {
	public static double convDegToRad(double deg) {
		return Math.PI / 180.0 * deg;
	}
	public static double convRadToDeg(double rad) {
		return 180.0 * rad / Math.PI;
	}

	public static double clamp(double value, double start, double end) {
		double ret;

		if (value < start) {
			ret = start;
		} else if (value > end) {
			ret = end;
		} else {
			ret = value;
		}

		return ret;
	}
}
