package com.github.dabasan.mjgl.gl.scene.fog;

import java.util.List;

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
	public void update(List<ShaderProgram> programs) {
		for (var progam : programs) {
			progam.enable();
			progam.setUniform("fog.start", start);
			progam.setUniform("fog.end", end);
			progam.setUniform("fog.color", color);
		}
	}
}
