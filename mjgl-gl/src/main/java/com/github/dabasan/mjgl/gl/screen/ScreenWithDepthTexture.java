package com.github.dabasan.mjgl.gl.screen;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Screen with a depth texture
 * 
 * @author Daba
 *
 */
public class ScreenWithDepthTexture extends ScreenBase {
	private final Logger logger = LoggerFactory.getLogger(ScreenWithDepthTexture.class);

	private int depthTexture;

	public ScreenWithDepthTexture(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);

		this.setupDepthTexture();
		this.setupFramebuffer();
	}
	private void setupDepthTexture() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		final IntBuffer textures = Buffers.newDirectIntBuffer(1);
		gl.glGenTextures(1, textures);
		depthTexture = textures.get(0);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, depthTexture);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_DEPTH_COMPONENT, this.getScreenWidth(),
				this.getScreenHeight(), 0, GL3ES3.GL_DEPTH_COMPONENT, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, this.getFBO());
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_DEPTH_ATTACHMENT,
				GL3ES3.GL_TEXTURE_2D, depthTexture, 0);
		final int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
		if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
			logger.warn("Incomplete framebuffer. status={}", status);
		}
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	@Override
	public void dispose() {
		super.dispose();

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		IntBuffer textures = Buffers.newDirectIntBuffer(new int[]{depthTexture});
		gl.glDeleteTextures(textures.capacity(), textures);
	}

	public void bindDepthTexture() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, depthTexture);
	}
}
