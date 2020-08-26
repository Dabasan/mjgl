package com.github.dabasan.mjgl.gl.renderer.shape;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Filled circle 2D renderer
 * 
 * @author Daba
 *
 */
public class FilledCircle2DRenderer extends RendererBase<Circle2D> {
	private int vao;
	private int vboIndex;
	private int vboPos;
	private int vboColor;

	private int countIndices;

	public FilledCircle2DRenderer() {
		this.generateBuffers();
		countIndices = 0;
	}
	private void generateBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(1);
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(3);
		gl.glGenVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glGenBuffers(vboBuffers.capacity(), vboBuffers);
		vao = vaoBuffer.get(0);
		vboIndex = vboBuffers.get(0);
		vboPos = vboBuffers.get(1);
		vboColor = vboBuffers.get(2);
	}

	@Override
	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffers = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(new int[]{vboIndex, vboPos, vboColor});
		gl.glDeleteVertexArrays(vaoBuffers.capacity(), vaoBuffers);
		gl.glDeleteBuffers(vboBuffers.capacity(), vboBuffers);
	}
	@Override
	public void update() {
		var indexValues = new ArrayList<Integer>();
		var points = new ArrayList<Point2D>();

		for (Circle2D circle : this.getShapes()) {
			List<Integer> indices = this.generateIndices(circle);
			int baseIndex = indexValues.size();
			for (int index : indices) {
				indexValues.add(baseIndex + index);
			}

			List<Point2D> circlePoints = this.generatePoints(circle);
			for (var point : circlePoints) {
				points.add(point);
			}
		}

		countIndices = indexValues.size();

		IntBuffer indexBuffer = Buffers.newDirectIntBuffer(indexValues.size());
		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(points.size() * 2);
		FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(points.size() * 4);

		for (var indexValue : indexValues) {
			indexBuffer.put(indexValue);
		}
		for (var point : points) {
			posBuffer.put(point.getX());
			posBuffer.put(point.getY());

			Color color = point.getColor();
			colorBuffer.put(color.getR());
			colorBuffer.put(color.getG());
			colorBuffer.put(color.getB());
			colorBuffer.put(color.getA());
		}
		indexBuffer.flip();
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
		// Index
		gl.glBindBuffer(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, vboIndex);
		gl.glBufferData(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, Buffers.SIZEOF_INT * indexBuffer.capacity(),
				indexBuffer, GL3ES3.GL_DYNAMIC_DRAW);
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
	private List<Integer> generateIndices(Circle2D circle) {
		var indices = new ArrayList<Integer>();

		int numDivisions = circle.getNumDivisions();
		for (int i = 1; i < numDivisions; i++) {
			indices.add(i);
			indices.add(i + 1);
			indices.add(0);
		}
		indices.add(numDivisions);
		indices.add(1);
		indices.add(0);

		return indices;
	}
	private List<Point2D> generatePoints(Circle2D circle) {
		var points = new ArrayList<Point2D>();

		Point2D center = circle.getCenter();
		float radius = circle.getRadius();
		int numDivisions = circle.getNumDivisions();
		Color color = circle.getColor();

		points.add(center);

		for (int i = 0; i < numDivisions; i++) {
			double th = Math.PI * 2.0 / numDivisions * i;
			double x = radius * Math.cos(th) + center.getX();
			double y = radius * Math.sin(th) + center.getY();

			var point = new Point2D();
			point.setX((float) x);
			point.setY((float) y);
			point.setColor(color);
			points.add(point);
		}

		return points;
	}

	@Override
	public void draw(ShaderProgram program) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		program.enable();

		gl.glBindVertexArray(vao);
		gl.glEnable(GL3ES3.GL_BLEND);
		gl.glDrawElements(GL3ES3.GL_TRIANGLES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
		gl.glDisable(GL3ES3.GL_BLEND);
		gl.glBindVertexArray(0);
	}
}
