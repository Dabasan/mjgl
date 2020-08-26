package com.github.dabasan.mjgl.gl.transferrer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Quad transferrer with UV
 * 
 * @author Daba
 *
 */
public class QuadTransferrerWithUV implements ITransferrer {
	private int vao;
	private int vboIndices;
	private int vboPos;
	private int vboUV;

	/**
	 * Creates a fullscreen quad transferrer.
	 * 
	 * @param flipV
	 *            Flip V-coordinate if true
	 */
	public QuadTransferrerWithUV(boolean flipV) {
		this(-1.0f, -1.0f, 1.0f, 1.0f, flipV);
	}
	/**
	 * Creates a quad transferrer.<br>
	 * Specify X and Y coordinates in NDC.
	 * 
	 * @param left
	 *            Left
	 * @param bottom
	 *            Bottom
	 * @param right
	 *            Right
	 * @param top
	 *            Top
	 * @param flipV
	 *            Flip V-coordinate if true
	 */
	public QuadTransferrerWithUV(float left, float bottom, float right, float top, boolean flipV) {
		if (flipV == true) {
			this.constructorBase(left, bottom, right, top, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
					1.0f);
		} else {
			this.constructorBase(left, bottom, right, top, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f,
					0.0f);
		}
	}
	/**
	 * Creates a quad transferrer.<br>
	 * Specify X and Y coordinates in NDC.
	 * 
	 * @param left
	 *            Left
	 * @param bottom
	 *            Bottom
	 * @param right
	 *            Right
	 * @param top
	 *            Top
	 * @param bottomLeftU
	 *            Bottom-left U
	 * @param bottomLeftV
	 *            Bottom-left V
	 * @param bottomRightU
	 *            Bottom-right U
	 * @param bottomRightV
	 *            Bottom-right V
	 * @param topRightU
	 *            Top-right U
	 * @param topRightV
	 *            Top-right V
	 * @param topLeftU
	 *            Top-left U
	 * @param topLeftV
	 *            Top-left V
	 */
	public QuadTransferrerWithUV(float left, float bottom, float right, float top,
			float bottomLeftU, float bottomLeftV, float bottomRightU, float bottomRightV,
			float topRightU, float topRightV, float topLeftU, float topLeftV) {
		this.constructorBase(left, bottom, right, top, bottomLeftU, bottomLeftV, bottomRightU,
				bottomRightV, topRightU, topRightV, topLeftU, topLeftV);
	}
	private void constructorBase(float left, float bottom, float right, float top,
			float bottomLeftU, float bottomLeftV, float bottomRightU, float bottomRightV,
			float topRightU, float topRightV, float topLeftU, float topLeftV) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(1);
		IntBuffer vboBuffer = Buffers.newDirectIntBuffer(3);
		gl.glGenVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glGenBuffers(vboBuffer.capacity(), vboBuffer);
		vao = vaoBuffer.get(0);
		vboIndices = vboBuffer.get(0);
		vboPos = vboBuffer.get(1);
		vboUV = vboBuffer.get(2);

		IntBuffer indicesBuffer = Buffers.newDirectIntBuffer(6);
		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(8);
		FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(8);

		// First triangle
		indicesBuffer.put(0);
		indicesBuffer.put(1);
		indicesBuffer.put(2);
		// Second triangle
		indicesBuffer.put(2);
		indicesBuffer.put(3);
		indicesBuffer.put(0);
		indicesBuffer.flip();

		// Bottom left
		posBuffer.put(left);
		posBuffer.put(bottom);
		// Bottom right
		posBuffer.put(right);
		posBuffer.put(bottom);
		// Top right
		posBuffer.put(right);
		posBuffer.put(top);
		// Top left
		posBuffer.put(left);
		posBuffer.put(top);
		posBuffer.flip();

		// Bottom left
		uvBuffer.put(bottomLeftU);
		uvBuffer.put(bottomLeftV);
		// Bottom right
		uvBuffer.put(bottomRightU);
		uvBuffer.put(bottomRightV);
		// Top right
		uvBuffer.put(topRightU);
		uvBuffer.put(topRightV);
		// Top left
		uvBuffer.put(topLeftU);
		uvBuffer.put(topLeftV);

		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
				posBuffer, GL3ES3.GL_STATIC_DRAW);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUV);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uvBuffer.capacity(),
				uvBuffer, GL3ES3.GL_STATIC_DRAW);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);

		gl.glBindVertexArray(vao);
		// Indices
		gl.glBindBuffer(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, vboIndices);
		gl.glBufferData(GL3ES3.GL_ELEMENT_ARRAY_BUFFER,
				Buffers.SIZEOF_INT * indicesBuffer.capacity(), indicesBuffer,
				GL3ES3.GL_STATIC_DRAW);
		// Positions
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(0, 2, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		// UV
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUV);
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 2, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindVertexArray(0);
	}

	@Override
	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffer = Buffers.newDirectIntBuffer(new int[]{vboIndices, vboPos, vboUV});

		gl.glDeleteVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glDeleteBuffers(vboBuffer.capacity(), vboBuffer);
	}
	@Override
	public void transfer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		gl.glBindVertexArray(vao);
		gl.glDrawElements(GL3ES3.GL_TRIANGLES, 6, GL3ES3.GL_UNSIGNED_INT, 0);
		gl.glBindVertexArray(0);
	}
}
