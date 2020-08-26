package com.github.dabasan.mjgl.gl.renderer.shape;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Base class for renderers
 * 
 * @author Daba
 *
 * @param <T>
 *            Shape
 */
public abstract class RendererBase<T> {
	private List<T> shapes;

	public RendererBase() {
		shapes = new ArrayList<>();
	}

	protected List<T> getShapes() {
		return shapes;
	}

	public void add(T shape) {
		shapes.add(shape);
	}
	public void remove(T shape) {
		shapes.remove(shape);
	}
	public void clear() {
		shapes.clear();
	}

	public abstract void dispose();
	public abstract void update();
	public abstract void draw(ShaderProgram program);
}
