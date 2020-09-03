package com.github.dabasan.mjgl.gl.scene.model;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.List;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Model
 * 
 * @author Daba
 *
 */
public class Model extends ModelBase {
	public Model(List<ModelBuffer> buffers, FlipVOption option) {
		super(buffers, option);
	}
	public Model(String modelFilepath, FlipVOption option) throws IOException {
		super(modelFilepath, option);
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

			gl.glBindVertexArray(vaoBuffers.get(i));
			gl.glEnable(GL3ES3.GL_BLEND);
			gl.glDrawElements(GL3ES3.GL_TRIANGLES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(vaoBuffers.get(0));
		}
	}
}
