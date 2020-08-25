package com.github.dabasan.mjgl.gl.effect;

import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Floating-point texture
 * 
 * @author Daba
 *
 */
public class FloatTexture {
	private int textureWidth;
	private int textureHeight;

	private int texture;

	public FloatTexture(int textureWidth, int textureHeight, int internalFormat, int format) {
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;

		this.setupTexture(internalFormat, format);
	}
	private void setupTexture(int internalFormat, int format) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(1);
		gl.glGenTextures(textures.capacity(), textures);
		texture = textures.get(0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texture);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, internalFormat, textureWidth, textureHeight, 0,
				format, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}

	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(new int[]{texture});
		gl.glDeleteTextures(textures.capacity(), textures);
	}

	public int getTextureWidth() {
		return textureWidth;
	}
	public int getTextureHeight() {
		return textureHeight;
	}

	protected int getTexture() {
		return texture;
	}

	public void bind() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texture);
	}
}
