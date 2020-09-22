package com.github.dabasan.mjgl.gl.scene.light;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.IUpdatable;
import com.github.dabasan.mjgl.gl.scene.Node;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Base class for lights
 * 
 * @author Daba
 *
 */
class LightBase extends Node implements IUpdatable {
	private Vector target;
	private Color colorAmbient;
	private Color colorDiffuse;
	private Color colorSpecular;

	// Following variables are used for shadow mapping.
	private float near;
	private float far;

	public LightBase() {
		this.setPosition(new Vector(50.0, 50.0, 50.0));

		target = new Vector(0.0, 0.0, 0.0);
		colorAmbient = Color.WHITE.scale(0.6f);
		colorDiffuse = Color.WHITE.scale(0.3f);
		colorSpecular = Color.WHITE.scale(0.1f);

		near = 1.0f;
		far = 200.0f;
	}

	public Vector getTarget() {
		return target;
	}
	public Color getColorAmbient() {
		return colorAmbient;
	}
	public Color getColorDiffuse() {
		return colorDiffuse;
	}
	public Color getColorSpecular() {
		return colorSpecular;
	}
	public float getNear() {
		return near;
	}
	public float getFar() {
		return far;
	}

	public void setTarget(Vector target) {
		this.target = target;
	}
	public void setColorAmbient(Color colorAmbient) {
		this.colorAmbient = colorAmbient;
	}
	public void setColorDiffuse(Color colorDiffuse) {
		this.colorDiffuse = colorDiffuse;
	}
	public void setColorSpecular(Color colorSpecular) {
		this.colorSpecular = colorSpecular;
	}
	public void setNear(float near) {
		this.near = near;
	}
	public void setFar(float far) {
		this.far = far;
	}

	@Override
	public void update(ShaderProgram program, int index) {

	}
}
