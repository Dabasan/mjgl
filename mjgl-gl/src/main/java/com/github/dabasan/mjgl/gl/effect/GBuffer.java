package com.github.dabasan.mjgl.gl.effect;

import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * GBuffer
 * 
 * @author Daba
 *
 */
public class GBuffer {
	private int fbo;
	private int texColor;
	private int texNormal;
	private int texPosition;

	private int bufferWidth;
	private int bufferHeight;

	public GBuffer(int bufferWidth, int bufferHeight) {
		this.bufferWidth = bufferWidth;
		this.bufferHeight = bufferHeight;

		this.setupTextures();
		this.setupFramebuffer();
	}
	private void setupTextures() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(3);
		gl.glGenTextures(textures.capacity(), textures);
		texColor = textures.get(0);
		texNormal = textures.get(1);
		texPosition = textures.get(2);

		for (int i = 0; i < textures.capacity(); i++) {
			gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, textures.get(i));
			gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGBA32F, bufferWidth, bufferHeight,
					0, GL3ES3.GL_RGBA, GL3ES3.GL_FLOAT, null);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER,
					GL3ES3.GL_NEAREST);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER,
					GL3ES3.GL_NEAREST);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_REPEAT);
			gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_REPEAT);
			gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
		}
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(1);
		gl.glGenFramebuffers(fbos.capacity(), fbos);
		fbo = fbos.get(0);

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_TEXTURE_2D, texColor, 0);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT1,
				GL3ES3.GL_TEXTURE_2D, texNormal, 0);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT2,
				GL3ES3.GL_TEXTURE_2D, texPosition, 0);
		IntBuffer draw_buffers = Buffers.newDirectIntBuffer(new int[]{GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_COLOR_ATTACHMENT1, GL3ES3.GL_COLOR_ATTACHMENT2});
		gl.glDrawBuffers(1, draw_buffers);
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public int getBufferWidth() {
		return bufferWidth;
	}
	public int getBufferHeight() {
		return bufferHeight;
	}

	protected int getFBO() {
		return fbo;
	}
	protected int getTexColor() {
		return texColor;
	}
	protected int getTexNormal() {
		return texNormal;
	}
	protected int getTexPosition() {
		return texPosition;
	}

	public void enable() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glViewport(0, 0, bufferWidth, bufferHeight);
	}
	public void clear() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glClear(GL3ES3.GL_DEPTH_BUFFER_BIT | GL3ES3.GL_COLOR_BUFFER_BIT
				| GL3ES3.GL_STENCIL_BUFFER_BIT);
	}
}
