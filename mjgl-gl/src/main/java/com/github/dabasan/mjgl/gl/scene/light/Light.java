package com.github.dabasan.mjgl.gl.scene.light;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.scene.IUpdatable;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Light
 * 
 * @author Daba
 *
 */
public class Light implements IUpdatable {
	private Vector direction;
	private Color colorAmbient;
	private Color colorDiffuse;
	private Color colorSpecular;
	private double powerAmbient;
	private double powerDiffuse;
	private double powerSpecular;

	public Light() {
		direction = new Vector(1.0, -1.0, 1.0).normalize();
		colorAmbient = Color.WHITE;
		colorDiffuse = Color.WHITE;
		colorSpecular = Color.WHITE;
		powerAmbient = 0.6;
		powerDiffuse = 0.3;
		powerSpecular = 0.1;
	}

	public Vector getDirection() {
		return direction;
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
	public double getPowerAmbient() {
		return powerAmbient;
	}
	public double getPowerDiffuse() {
		return powerDiffuse;
	}
	public double getPowerSpecular() {
		return powerSpecular;
	}

	public void setDirection(Vector direction) {
		this.direction = direction;
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
	public void setPowerAmbient(double powerAmbient) {
		this.powerAmbient = powerAmbient;
	}
	public void setPowerDiffuse(double powerDiffuse) {
		this.powerDiffuse = powerDiffuse;
	}
	public void setPowerSpecular(double powerSpecular) {
		this.powerSpecular = powerSpecular;
	}

	@Override
	public void update(ShaderProgram program) {
		program.enable();
		program.setUniform("light.direction", direction);
		program.setUniform("light.colorAmbient", colorAmbient);
		program.setUniform("light.colorDiffuse", colorDiffuse);
		program.setUniform("light.colorSpecular", colorSpecular);
		program.setUniform("light.powerAmbient", powerAmbient);
		program.setUniform("light.powerDiffuse", powerDiffuse);
		program.setUniform("light.powerSpecular", powerSpecular);
	}
}
