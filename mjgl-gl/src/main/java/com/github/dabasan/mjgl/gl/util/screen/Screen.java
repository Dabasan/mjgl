package com.github.dabasan.mjgl.gl.util.screen;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Screen
 * 
 * @author Daba
 *
 */
public class Screen extends ScreenBase {
	private Logger logger = LoggerFactory.getLogger(Screen.class);

	private int rbo;

	public Screen(int screenWidth, int screenHeight) {
		super(screenWidth, screenHeight);

		this.setupRenderbuffer();
		this.setupFramebuffer();
	}
	private void setupRenderbuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer rbos = Buffers.newDirectIntBuffer(1);
		gl.glGenRenderbuffers(rbos.capacity(), rbos);
		rbo = rbos.get(0);

		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, rbo);
		gl.glRenderbufferStorage(GL3ES3.GL_RENDERBUFFER, GL3ES3.GL_DEPTH_COMPONENT,
				this.getScreenWidth(), this.getScreenHeight());
		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, 0);
	}
	private void setupFramebuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, this.getFBO());
		gl.glFramebufferRenderbuffer(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_DEPTH_ATTACHMENT,
				GL3ES3.GL_RENDERBUFFER, rbo);
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
		IntBuffer rbos = Buffers.newDirectIntBuffer(new int[]{rbo});
		gl.glDeleteRenderbuffers(rbos.capacity(), rbos);
	}
}
