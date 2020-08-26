package com.github.dabasan.mjgl.gl.scene.model;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.GBuffer;
import com.github.dabasan.mjgl.gl.scene.Node;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Model
 * 
 * @author Daba
 *
 */
public class Model extends Node {
	private List<ModelBuffer> buffers;
	private boolean propertyUpdated;

	private IntBuffer vaoBuffers;
	private IntBuffer vboIndexBuffers;
	private IntBuffer vboPosBuffers;
	private IntBuffer vboUVBuffers;
	private IntBuffer vboNormBuffers;

	private static Texture defaultTexture;

	public static void setDefaultTexture(Texture texture) {
		defaultTexture = texture;
	}

	public Model(List<ModelBuffer> buffers, FlipVOption option) {
		this.buffers = buffers;
		propertyUpdated = false;

		this.setTexureParameters();
		this.generateBuffers(option);
	}
	public Model(String modelFilepath, FlipVOption option) throws IOException {
		// Get the file extension.
		int lastIndexOfDot = modelFilepath.lastIndexOf('.');
		if (lastIndexOfDot == -1) {
			String errMessage = String.format(
					"Failed to detect the file extension from the filepath. filepath=%s",
					modelFilepath);
			throw new IOException(errMessage);
		}

		String extension = modelFilepath.substring(lastIndexOfDot + 1).toLowerCase();

		// Load the model.
		List<ModelBuffer> buffers;
		switch (extension) {
			case "bd1" :
				buffers = ModelLoader.loadBD1(modelFilepath, true, false);
				break;
			case "obj" :
				buffers = ModelLoader.loadOBJ(modelFilepath);
				break;
			default :
				buffers = ModelLoader.loadModel(modelFilepath);
				break;
		}

		this.buffers = buffers;
		propertyUpdated = false;

		this.setTexureParameters();
		this.generateBuffers(option);
	}
	private void setTexureParameters() {
		for (var buffer : buffers) {
			Texture texture = buffer.getTexture();
			if (texture == null) {
				continue;
			}

			GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();
			texture.bind(gl);
			gl.glGenerateMipmap(texture.getTarget());
			texture.setTexParameteri(gl, GL2ES2.GL_TEXTURE_MIN_FILTER,
					GL2ES2.GL_LINEAR_MIPMAP_LINEAR);
			texture.setTexParameteri(gl, GL2ES2.GL_TEXTURE_MAG_FILTER, GL2ES2.GL_LINEAR);
			texture.setTexParameteri(gl, GL2ES2.GL_TEXTURE_WRAP_S, GL2ES2.GL_REPEAT);
			texture.setTexParameteri(gl, GL2ES2.GL_TEXTURE_WRAP_T, GL2ES2.GL_REPEAT);
		}
	}
	private void generateBuffers(FlipVOption option) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();
		vaoBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboIndexBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboPosBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboUVBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboNormBuffers = Buffers.newDirectIntBuffer(numBuffers);

		gl.glGenVertexArrays(numBuffers, vaoBuffers);
		gl.glGenBuffers(numBuffers, vboIndexBuffers);
		gl.glGenBuffers(numBuffers, vboPosBuffers);
		gl.glGenBuffers(numBuffers, vboUVBuffers);
		gl.glGenBuffers(numBuffers, vboNormBuffers);

		for (int i = 0; i < numBuffers; i++) {
			ModelBuffer buffer = buffers.get(i);

			// Flip V-coordinate of the UVs.
			FloatBuffer uvBuffer = buffer.getUVBuffer();
			FloatBuffer transformedUVBuffer;
			if (option == FlipVOption.NONE) {
				transformedUVBuffer = uvBuffer.duplicate();
			} else if (option == FlipVOption.MUST_FLIP_VERTICALLY) {
				Texture texture = buffer.getTexture();
				if (texture.getMustFlipVertically()) {
					transformedUVBuffer = Buffers.newDirectFloatBuffer(uvBuffer.capacity());

					for (int j = 0; j < uvBuffer.capacity(); j += 2) {
						float u = uvBuffer.get(j);
						float v = uvBuffer.get(j + 1);

						transformedUVBuffer.put(j, u);
						transformedUVBuffer.put(j + 1, v * (-1.0f));
					}
				} else {
					transformedUVBuffer = uvBuffer.duplicate();
				}
			} else {
				transformedUVBuffer = Buffers.newDirectFloatBuffer(uvBuffer.capacity());

				for (int j = 0; j < uvBuffer.capacity(); j += 2) {
					float u = uvBuffer.get(j);
					float v = uvBuffer.get(j + 1);

					transformedUVBuffer.put(j, u);
					transformedUVBuffer.put(j + 1, v * (-1.0f));
				}
			}

			FloatBuffer posBuffer = buffer.getPosBuffer();
			FloatBuffer normBuffer = buffer.getNormBuffer();

			// Position
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPosBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
					posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
			// UV
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUVBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER,
					Buffers.SIZEOF_FLOAT * transformedUVBuffer.capacity(), transformedUVBuffer,
					GL3ES3.GL_STATIC_DRAW);
			// Norm
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboNormBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * normBuffer.capacity(),
					normBuffer, GL3ES3.GL_DYNAMIC_DRAW);

			IntBuffer indexBuffer = buffer.getIndexBuffer();

			gl.glBindVertexArray(vaoBuffers.get(i));
			// Index
			gl.glBindBuffer(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, vboIndexBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ELEMENT_ARRAY_BUFFER,
					Buffers.SIZEOF_INT * indexBuffer.capacity(), indexBuffer,
					GL3ES3.GL_STATIC_DRAW);
			// Position
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPosBuffers.get(i));
			gl.glEnableVertexAttribArray(0);
			gl.glVertexAttribPointer(0, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
			// UV
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUVBuffers.get(i));
			gl.glEnableVertexAttribArray(1);
			gl.glVertexAttribPointer(1, 2, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);
			// Norm
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboNormBuffers.get(i));
			gl.glEnableVertexAttribArray(2);
			gl.glVertexAttribPointer(2, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
			gl.glBindVertexArray(0);
		}
	}
	public void update() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();
		for (int i = 0; i < numBuffers; i++) {
			ModelBuffer buffer = buffers.get(i);

			FloatBuffer posBuffer = buffer.getPosBuffer();
			FloatBuffer normBuffer = buffer.getNormBuffer();

			gl.glBindVertexArray(vaoBuffers.get(i));
			// Position
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPosBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
					posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
			// Norm
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboNormBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * normBuffer.capacity(),
					normBuffer, GL3ES3.GL_DYNAMIC_DRAW);
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
			gl.glBindVertexArray(0);
		}

		propertyUpdated = false;
	}
	public void dispose(boolean deleteTextures) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();
		gl.glDeleteVertexArrays(numBuffers, vaoBuffers);
		gl.glDeleteBuffers(numBuffers, vboIndexBuffers);
		gl.glDeleteBuffers(numBuffers, vboPosBuffers);
		gl.glDeleteBuffers(numBuffers, vboUVBuffers);
		gl.glDeleteBuffers(numBuffers, vboNormBuffers);

		if (deleteTextures == true) {
			for (var buffer : buffers) {
				buffer.getTexture().destroy(gl);
			}
		}
	}

	public int getNumBuffers() {
		return buffers.size();
	}

	public void draw(ShaderProgram program, GBuffer gBuffer, String samplerName, int textureUnit) {
		if (propertyUpdated == true) {
			this.update();
		}

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();

		if (gBuffer != null) {
			gBuffer.enable();
		}
		program.enable();
		for (int i = 0; i < numBuffers; i++) {
			ModelBuffer buffer = buffers.get(i);
			Texture texture = buffer.getTexture();
			int countIndices = buffer.getCountIndices();

			if (texture != null) {
				program.setTexture(samplerName, textureUnit, texture);
			} else {
				program.setTexture(samplerName, textureUnit, defaultTexture);
			}

			gl.glBindVertexArray(vaoBuffers.get(i));
			gl.glEnable(GL3ES3.GL_BLEND);
			gl.glDrawElements(GL3ES3.GL_TRIANGLES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(vaoBuffers.get(0));
		}
	}
	public void draw(ShaderProgram program, GBuffer gBuffer) {
		this.draw(program, gBuffer, "textureSampler", 0);
	}

	public void transfer(ShaderProgram program) {
		if (propertyUpdated == true) {
			this.update();
		}

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();

		program.enable();
		for (int i = 0; i < numBuffers; i++) {
			ModelBuffer buffer = buffers.get(i);
			int countIndices = buffer.getCountIndices();

			gl.glBindVertexArray(vaoBuffers.get(i));
			gl.glEnable(GL3ES3.GL_BLEND);
			gl.glDrawElements(GL3ES3.GL_TRIANGLES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(vaoBuffers.get(0));
		}
	}

	public void transform(Matrix m) {
		super.transform(m);

		for (var buffer : buffers) {
			FloatBuffer posBuffer = buffer.getPosBuffer();
			FloatBuffer normBuffer = buffer.getNormBuffer();

			for (int i = 0; i < posBuffer.capacity(); i += 3) {
				// Position
				var position = new Vector(posBuffer.get(i), posBuffer.get(i + 1),
						posBuffer.get(i + 2));
				position = position.transform(m);

				posBuffer.put(i, position.getXFloat());
				posBuffer.put(i + 1, position.getYFloat());
				posBuffer.put(i + 2, position.getZFloat());

				// Normal
				var norm = new Vector(normBuffer.get(i), normBuffer.get(i + 1),
						normBuffer.get(i + 2));
				norm = norm.transformSR(m).normalize();

				normBuffer.put(i, norm.getXFloat());
				normBuffer.put(i + 1, norm.getYFloat());
				normBuffer.put(i + 2, norm.getZFloat());
			}

			buffer.setPosBuffer(posBuffer);
			buffer.setNormBuffer(normBuffer);
		}

		propertyUpdated = true;
	}
	public void translate(Vector translation) {
		super.translate(translation);

		var mTranslation = Matrix.createTranslationMatrix(translation.getX(), translation.getY(),
				translation.getZ());
		this.transform(mTranslation);
	}
	public void rescale(Vector scale) {
		super.rescale(scale);

		var mScaling = Matrix.createScalingMatrix(scale.getX(), scale.getY(), scale.getZ());
		this.transform(mScaling);
	}
	public void rotX(double th) {
		super.rotX(th);

		var mRotX = Matrix.createRotationXMatrix(th);
		this.transform(mRotX);
	}
	public void rotY(double th) {
		super.rotY(th);

		var mRotY = Matrix.createRotationYMatrix(th);
		this.transform(mRotY);
	}
	public void rotZ(double th) {
		super.rotZ(th);

		var mRotZ = Matrix.createRotationZMatrix(th);
		this.transform(mRotZ);
	}
	public void rot(Vector axis, double th) {
		super.rot(axis, th);

		var mRot = Matrix.createRotationMatrix(axis.getX(), axis.getY(), axis.getZ(), th);
		this.transform(mRot);
	}

	public int setTexture(int indexMaterial, Texture texture) {
		if (!(0 <= indexMaterial && indexMaterial < buffers.size())) {
			return -1;
		}

		ModelBuffer buffer = buffers.get(indexMaterial);
		buffer.setTexture(texture);

		return 0;
	}

	public List<ModelBuffer> getBuffers() {
		return buffers;
	}
}
