package com.github.dabasan.mjgl.gl.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Segment renderer
 * 
 * @author Daba
 *
 */
public class SegmentRenderer extends RendererBase<Segment> {
	private int vao;
	private int vboPos;
	private int vboColor;

	public SegmentRenderer() {
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
	public void deleteBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffers = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(new int[]{vboPos, vboColor});
		gl.glDeleteVertexArrays(vaoBuffers.capacity(), vaoBuffers);
		gl.glDeleteBuffers(vboBuffers.capacity(), vboBuffers);
	}
	@Override
	public void updateBuffers() {
		int numPoints = this.getShapes().size() * 2;

		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(numPoints * 3 * 2);
		FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(numPoints * 4 * 2);

		for (Segment segment : this.getShapes()) {
			Vector pos1 = segment.getPosition1();
			Vector pos2 = segment.getPosition2();
			Color color1 = segment.getColor1();
			Color color2 = segment.getColor2();

			posBuffer.put(pos1.getXFloat());
			posBuffer.put(pos1.getYFloat());
			posBuffer.put(pos1.getZFloat());
			posBuffer.put(pos2.getXFloat());
			posBuffer.put(pos2.getYFloat());
			posBuffer.put(pos2.getZFloat());
			colorBuffer.put(color1.getR());
			colorBuffer.put(color1.getG());
			colorBuffer.put(color1.getB());
			colorBuffer.put(color1.getA());
			colorBuffer.put(color2.getR());
			colorBuffer.put(color2.getG());
			colorBuffer.put(color2.getB());
			colorBuffer.put(color2.getA());
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
		gl.glVertexAttribPointer(0, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColor);
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 4, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindVertexArray(0);
	}
	@Override
	public void draw(List<ShaderProgram> programs) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numPoints = this.getShapes().size() * 2;

		for (var program : programs) {
			program.enable();

			gl.glBindVertexArray(vao);
			gl.glEnable(GL3ES3.GL_BLEND);
			gl.glDrawArrays(GL3ES3.GL_LINES, 0, numPoints);
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(0);
		}
	}
}
