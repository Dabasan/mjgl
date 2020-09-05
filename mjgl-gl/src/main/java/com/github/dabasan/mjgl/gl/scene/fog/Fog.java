package com.github.dabasan.mjgl.gl.scene.fog;

import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.scene.IUpdatable;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Fog
 * 
 * @author Daba
 *
 */
public class Fog implements IUpdatable {
	private double start;
	private double end;
	private Color color;

	public Fog() {
		start = 100.0;
		end = 200.0;
		color = Color.BLACK;
	}

	public double getStart() {
		return start;
	}
	public double getEnd() {
		return end;
	}
	public Color getColor() {
		return color;
	}

	public void setStart(double start) {
		this.start = start;
	}
	public void setEnd(double end) {
		this.end = end;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void update(ShaderProgram program, int index) {
		program.enable();
		if (index < 0) {
			program.setUniform("fog.start", start);
			program.setUniform("fog.end", end);
			program.setUniform("fog.color", color);
		} else {
			String strArray = "fogs" + "[" + index + "]";

			program.setUniform(strArray + ".start", start);
			program.setUniform(strArray + ".end", end);
			program.setUniform(strArray + ".color", color);
		}
	}
}
