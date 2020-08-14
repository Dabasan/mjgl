package com.github.dabasan.mjgl.gl.renderer;

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

	public abstract void deleteBuffers();
	public abstract void updateBuffers();
	public abstract void draw(List<ShaderProgram> programs);
}
