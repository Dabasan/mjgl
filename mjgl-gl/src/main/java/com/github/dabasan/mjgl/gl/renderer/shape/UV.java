package com.github.dabasan.mjgl.gl.renderer.shape;

/**
 * UV
 * 
 * @author Daba
 *
 */
public class UV {
	private float u;
	private float v;

	public UV() {
		u = 0.0f;
		v = 0.0f;
	}

	public float getU() {
		return u;
	}
	public float getV() {
		return v;
	}

	public void setU(float u) {
		this.u = u;
	}
	public void setV(float v) {
		this.v = v;
	}
}
