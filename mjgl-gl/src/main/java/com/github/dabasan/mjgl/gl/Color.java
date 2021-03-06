package com.github.dabasan.mjgl.gl;

/**
 * Color
 * 
 * @author Daba
 *
 */
public final class Color {
	private float r;
	private float g;
	private float b;
	private float a;

	public static final Color BLACK = new Color(0, 0, 0, 255);
	public static final Color GRAY = new Color(128, 128, 128, 255);
	public static final Color SILVER = new Color(192, 192, 192, 255);
	public static final Color WHITE = new Color(255, 255, 255, 255);
	public static final Color RED = new Color(255, 0, 0, 255);
	public static final Color MAROON = new Color(128, 0, 0, 255);
	public static final Color FUCHSIA = new Color(255, 0, 255, 255);
	public static final Color LIME = new Color(0, 255, 0, 255);
	public static final Color GREEN = new Color(0, 128, 0, 255);
	public static final Color YELLOW = new Color(255, 255, 0, 255);
	public static final Color OLIVE = new Color(128, 128, 0, 255);
	public static final Color BLUE = new Color(0, 0, 255, 255);
	public static final Color NAVY = new Color(0, 0, 128, 255);
	public static final Color AQUA = new Color(0, 255, 255, 255);
	public static final Color TEAL = new Color(0, 128, 128, 255);

	public Color() {
		this.set(0.0f, 0.0f, 0.0f, 0.0f);
	}
	public Color(float r, float g, float b, float a) {
		this.set(r, g, b, a);
	}
	public Color(int r, int g, int b, int a) {
		this.set(r, g, b, a);
	}
	public Color(Color c) {
		this.r = c.r;
		this.g = c.g;
		this.b = c.b;
		this.a = c.a;
	}

	@Override
	public String toString() {
		return "(" + r + "," + g + "," + b + "," + a + ")";
	}

	public float getR() {
		return r;
	}
	public float getG() {
		return g;
	}
	public float getB() {
		return b;
	}
	public float getA() {
		return a;
	}

	private void set(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	private void set(int r, int g, int b, int a) {
		this.r = r / 255.0f;
		this.g = g / 255.0f;
		this.b = b / 255.0f;
		this.a = a / 255.0f;
	}

	/**
	 * Scales a color. Returns a new instance.
	 * 
	 * @param scale
	 *            Scale
	 * @return New instance of Color
	 */
	public Color scale(float scale) {
		return new Color(r * scale, g * scale, b * scale, a * scale);
	}
}
