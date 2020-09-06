package com.github.dabasan.mjgl.gl;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Buffer to store factors
 * 
 * @author Daba
 *
 */
public class FactorBuffer {
	private Logger logger = LoggerFactory.getLogger(FactorBuffer.class);

	private int bufferWidth;
	private int bufferHeight;

	private int fbo;
	private int texture;

	public FactorBuffer(int bufferWidth, int bufferHeight) {
		this.bufferWidth = bufferWidth;
		this.bufferHeight = bufferHeight;

		this.setupTexture();
		this.setupFramebuffer();
	}
	private void setupTexture() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(1);
		gl.glGenTextures(textures.capacity(), textures);
		texture = textures.get(0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texture);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB16F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(1);
		gl.glGenFramebuffers(fbos.capacity(), fbos);
		fbo = fbos.get(0);

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_TEXTURE_2D, texture, 0);
		IntBuffer draw_buffers = Buffers.newDirectIntBuffer(new int[]{GL3ES3.GL_COLOR_ATTACHMENT0});
		gl.glDrawBuffers(1, draw_buffers);
		int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
		if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
			logger.error("Incomplete framebuffer. status={}", status);
		}
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(new int[]{fbo});
		IntBuffer textures = Buffers.newDirectIntBuffer(new int[]{texture});

		gl.glDeleteFramebuffers(fbos.capacity(), fbos);
		gl.glDeleteTextures(textures.capacity(), textures);
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
	public int getTexture() {
		return texture;
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
