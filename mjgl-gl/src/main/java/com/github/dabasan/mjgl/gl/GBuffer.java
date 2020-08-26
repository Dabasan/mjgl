package com.github.dabasan.mjgl.gl;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private Logger logger = LoggerFactory.getLogger(GBuffer.class);

	private int fbo;
	private int texAlbedo;
	private int texPosition;
	private int texNormal;

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
		texAlbedo = textures.get(0);
		texPosition = textures.get(1);
		texNormal = textures.get(2);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texAlbedo);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGBA32F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGBA, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_REPEAT);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_REPEAT);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texPosition);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB32F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_REPEAT);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_REPEAT);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texNormal);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB32F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_REPEAT);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_REPEAT);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(1);
		gl.glGenFramebuffers(fbos.capacity(), fbos);
		fbo = fbos.get(0);

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, fbo);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_TEXTURE_2D, texAlbedo, 0);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT2,
				GL3ES3.GL_TEXTURE_2D, texPosition, 0);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT1,
				GL3ES3.GL_TEXTURE_2D, texNormal, 0);
		IntBuffer drawBuffers = Buffers.newDirectIntBuffer(new int[]{GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_COLOR_ATTACHMENT1, GL3ES3.GL_COLOR_ATTACHMENT2});
		gl.glDrawBuffers(drawBuffers.capacity(), drawBuffers);
		int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
		if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
			logger.error("Incomplete framebuffer. status={}", status);
		}
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
	protected int getTexAlbedo() {
		return texAlbedo;
	}
	protected int getTexPosition() {
		return texPosition;
	}
	protected int getTexNormal() {
		return texNormal;
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

	public void bindTexAlbedo() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texAlbedo);
	}
	public void bindTexPosition() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texPosition);
	}
	public void bindTexNormal() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texNormal);
	}
}
