package com.github.dabasan.mjgl.gl.scene.model;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;

import com.github.dabasan.mjgl.gl.GBuffer;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Model rendered with deferred rendering
 * 
 * @author Daba
 *
 */
public class DeferredModel extends ModelBase {
	private GBuffer[] gBuffers;

	private static int gBufferWidth = 1280;
	private static int gBufferHeight = 720;

	public static void setGBufferSize(int width, int height) {
		gBufferWidth = width;
		gBufferHeight = height;
	}

	public DeferredModel(List<ModelBuffer> buffers, FlipVOption option) {
		super(buffers, option);
		this.generateGBuffers();
	}
	public DeferredModel(String modelFilepath, FlipVOption option) throws IOException {
		super(modelFilepath, option);
		this.generateGBuffers();
	}
	private void generateGBuffers() {
		int numBuffers = this.getNumBuffers();

		gBuffers = new GBuffer[numBuffers];
		for (int i = 0; i < numBuffers; i++) {
			gBuffers[i] = new GBuffer(gBufferWidth, gBufferHeight);
		}
	}

	public void reshapeGBuffers(int width, int height) {
		for (var gBuffer : gBuffers) {
			if (gBuffer != null) {
				gBuffer.dispose();
			}
		}

		int numBuffers = this.getNumBuffers();

		gBuffers = new GBuffer[numBuffers];
		for (int i = 0; i < numBuffers; i++) {
			gBuffers[i] = new GBuffer(width, height);
		}
	}

	@Override
	public void draw(ShaderProgram program, String samplerName, int textureUnit) {
		if (this.isPropertyUpdated() == true) {
			this.update();
		}

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		List<ModelBuffer> buffers = this.getBuffers();
		IntBuffer vaoBuffers = this.getVAOBuffers();

		program.enable();
		for (int i = 0; i < buffers.size(); i++) {
			ModelBuffer buffer = buffers.get(i);
			Texture texture = buffer.getTexture();
			int countIndices = buffer.getCountIndices();

			if (texture != null) {
				program.setTexture(samplerName, textureUnit, texture);
			} else {
				program.setTexture(samplerName, textureUnit, getDefaultTexture());
			}

			gBuffers[i].enable();
			gBuffers[i].clear();
			gl.glBindVertexArray(vaoBuffers.get(i));
			gl.glEnable(GL3ES3.GL_BLEND);
			gl.glDrawElements(GL3ES3.GL_TRIANGLES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(vaoBuffers.get(0));
		}
	}
}
