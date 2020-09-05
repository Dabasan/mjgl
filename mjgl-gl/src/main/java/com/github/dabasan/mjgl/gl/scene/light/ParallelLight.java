package com.github.dabasan.mjgl.gl.scene.light;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.scene.IUpdatable;
import com.github.dabasan.mjgl.gl.scene.Node;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Light
 * 
 * @author Daba
 *
 */
public class ParallelLight extends Node implements IUpdatable {
	private Vector target;
	private Color colorAmbient;
	private Color colorDiffuse;
	private Color colorSpecular;
	private double powerAmbient;
	private double powerDiffuse;
	private double powerSpecular;

	public ParallelLight() {
		target = new Vector(0.0, 0.0, 0.0);
		colorAmbient = Color.WHITE;
		colorDiffuse = Color.WHITE;
		colorSpecular = Color.WHITE;
		powerAmbient = 0.6;
		powerDiffuse = 0.3;
		powerSpecular = 0.1;
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
	public double getPowerAmbient() {
		return powerAmbient;
	}
	public double getPowerDiffuse() {
		return powerDiffuse;
	}
	public double getPowerSpecular() {
		return powerSpecular;
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
	public void update(ShaderProgram program, int index) {
		program.enable();
		if (index < 0) {
			program.setUniform("light.position", this.getPosition());
			program.setUniform("light.target", target);
			program.setUniform("light.colorAmbient", colorAmbient);
			program.setUniform("light.colorDiffuse", colorDiffuse);
			program.setUniform("light.colorSpecular", colorSpecular);
			program.setUniform("light.powerAmbient", powerAmbient);
			program.setUniform("light.powerDiffuse", powerDiffuse);
			program.setUniform("light.powerSpecular", powerSpecular);
		} else {
			String strArray = "lights" + "[" + index + "]";

			program.setUniform(strArray + ".position", this.getPosition());
			program.setUniform(strArray + ".target", target);
			program.setUniform(strArray + ".colorAmbient", colorAmbient);
			program.setUniform(strArray + ".colorDiffuse", colorDiffuse);
			program.setUniform(strArray + ".colorSpecular", colorSpecular);
			program.setUniform(strArray + ".powerAmbient", powerAmbient);
			program.setUniform(strArray + ".powerDiffuse", powerDiffuse);
			program.setUniform(strArray + ".powerSpecular", powerSpecular);
		}
	}
}
