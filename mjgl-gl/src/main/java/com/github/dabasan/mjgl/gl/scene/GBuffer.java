package com.github.dabasan.mjgl.gl.scene;

import java.nio.IntBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.mjgl.gl.IUpdatable;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * GBuffer
 * 
 * @author Daba
 *
 */
public class GBuffer implements IUpdatable {
	private Logger logger = LoggerFactory.getLogger(GBuffer.class);

	private int fbo;
	private int texColor;
	private int texPosition;
	private int texNormal;
	private int rboDepth;

	private int bufferWidth;
	private int bufferHeight;

	public GBuffer(int bufferWidth, int bufferHeight) {
		this.bufferWidth = bufferWidth;
		this.bufferHeight = bufferHeight;

		this.setupTextures();
		this.setupRenderbuffer();
		this.setupFramebuffer();
	}
	private void setupTextures() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer textures = Buffers.newDirectIntBuffer(3);
		gl.glGenTextures(textures.capacity(), textures);
		texColor = textures.get(0);
		texPosition = textures.get(1);
		texNormal = textures.get(2);

		// texColor
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texColor);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_UNSIGNED_BYTE, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_LINEAR);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		// texPosition
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texPosition);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB16F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);
		// texNormal
		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, texNormal);
		gl.glTexImage2D(GL3ES3.GL_TEXTURE_2D, 0, GL3ES3.GL_RGB16F, bufferWidth, bufferHeight, 0,
				GL3ES3.GL_RGB, GL3ES3.GL_FLOAT, null);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MAG_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_MIN_FILTER, GL3ES3.GL_NEAREST);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_S, GL3ES3.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL3ES3.GL_TEXTURE_2D, GL3ES3.GL_TEXTURE_WRAP_T, GL3ES3.GL_CLAMP_TO_EDGE);

		gl.glBindTexture(GL3ES3.GL_TEXTURE_2D, 0);
	}
	private void setupRenderbuffer() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer rbos = Buffers.newDirectIntBuffer(1);
		gl.glGenRenderbuffers(rbos.capacity(), rbos);
		rboDepth = rbos.get(0);

		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, rboDepth);
		gl.glRenderbufferStorage(GL3ES3.GL_RENDERBUFFER, GL3ES3.GL_DEPTH_COMPONENT, bufferWidth,
				bufferHeight);
		gl.glBindRenderbuffer(GL3ES3.GL_RENDERBUFFER, 0);
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
				GL3ES3.GL_TEXTURE_2D, texPosition, 0);
		gl.glFramebufferTexture2D(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_COLOR_ATTACHMENT2,
				GL3ES3.GL_TEXTURE_2D, texNormal, 0);
		gl.glFramebufferRenderbuffer(GL3ES3.GL_FRAMEBUFFER, GL3ES3.GL_DEPTH_ATTACHMENT,
				GL3ES3.GL_RENDERBUFFER, rboDepth);

		IntBuffer drawBuffers = Buffers.newDirectIntBuffer(new int[]{GL3ES3.GL_COLOR_ATTACHMENT0,
				GL3ES3.GL_COLOR_ATTACHMENT1, GL3ES3.GL_COLOR_ATTACHMENT2});
		gl.glDrawBuffers(drawBuffers.capacity(), drawBuffers);

		int status = gl.glCheckFramebufferStatus(GL3ES3.GL_FRAMEBUFFER);
		if (status != GL3ES3.GL_FRAMEBUFFER_COMPLETE) {
			logger.error("Incomplete framebuffer. status={}", status);
		}

		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
	}

	public void dispose() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer fbos = Buffers.newDirectIntBuffer(new int[]{fbo});
		IntBuffer textures = Buffers
				.newDirectIntBuffer(new int[]{texColor, texPosition, texNormal});
		IntBuffer rbos = Buffers.newDirectIntBuffer(new int[]{rboDepth});

		gl.glDeleteFramebuffers(fbos.capacity(), fbos);
		gl.glDeleteTextures(textures.capacity(), textures);
		gl.glDeleteRenderbuffers(rbos.capacity(), rbos);
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
	public int getTexColor() {
		return texColor;
	}
	public int getTexPosition() {
		return texPosition;
	}
	public int getTexNormal() {
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

	@Override
	public void update(ShaderProgram program, int index) {
		program.enable();
		if (index < 0) {
			program.setTexture("gBuffer.texColor", 0, GL3ES3.GL_TEXTURE_2D, texColor);
			program.setTexture("gBuffer.texPosition", 1, GL3ES3.GL_TEXTURE_2D, texPosition);
			program.setTexture("gBuffer.texNormal", 2, GL3ES3.GL_TEXTURE_2D, texNormal);
		} else {
			String strArray = "gBuffers" + "[" + index + "]";

			program.setTexture(strArray + ".texColor", 0, GL3ES3.GL_TEXTURE_2D, texColor);
			program.setTexture(strArray + ".texPosition", 1, GL3ES3.GL_TEXTURE_2D, texPosition);
			program.setTexture(strArray + ".texNormal", 2, GL3ES3.GL_TEXTURE_2D, texNormal);
		}
	}
}
