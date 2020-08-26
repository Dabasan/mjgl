package com.github.dabasan.mjgl.gl.renderer.shape;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Point 2D renderer
 * 
 * @author Daba
 *
 */
public class Point2DRenderer extends RendererBase<Point2D> {
	private int vao;
	private int vboPos;
	private int vboColor;

	public Point2DRenderer() {
		this.generateBuffers();
	}
	private void generateBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(1);
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(2);
		gl.glGenVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glGenBuffers(vboBuffers.capacity(), vboBuffers);
		vao = vaoBuffer.get(0);
		vboPos = vboBuffers.get(0);
		vboColor = vboBuffers.get(1);
	}

	@Override
	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffers = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(new int[]{vboPos, vboColor});
		gl.glDeleteVertexArrays(vaoBuffers.capacity(), vaoBuffers);
		gl.glDeleteBuffers(vboBuffers.capacity(), vboBuffers);
	}
	@Override
	public void update() {
		int numPoints = this.getShapes().size();

		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(numPoints * 2);
		FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(numPoints * 4);

		for (Point2D point : this.getShapes()) {
			float x = point.getX();
			float y = point.getY();
			Color color = point.getColor();

			posBuffer.put(x);
			posBuffer.put(y);
			colorBuffer.put(color.getR());
			colorBuffer.put(color.getG());
			colorBuffer.put(color.getB());
			colorBuffer.put(color.getA());
		}
		posBuffer.flip();
		colorBuffer.flip();

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
				posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColor);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * colorBuffer.capacity(),
				colorBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);

		gl.glBindVertexArray(vao);
		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(0, 2, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);
		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColor);
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 4, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindVertexArray(0);
	}
	@Override
	public void draw(ShaderProgram program) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numPoints = this.getShapes().size();

		program.enable();

		gl.glBindVertexArray(vao);
		gl.glEnable(GL3ES3.GL_BLEND);
		gl.glDrawArrays(GL3ES3.GL_POINTS, 0, numPoints);
		gl.glDisable(GL3ES3.GL_BLEND);
		gl.glBindVertexArray(0);
	}
}
