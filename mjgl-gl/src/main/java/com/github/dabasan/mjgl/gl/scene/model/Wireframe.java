package com.github.dabasan.mjgl.gl.scene.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.scene.Node;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Model wireframe
 * 
 * @author Daba
 *
 */
public class Wireframe extends Node {
	private List<ModelBuffer> buffers;
	private boolean propertyUpdated;

	private IntBuffer vaoBuffers;
	private IntBuffer vboIndexBuffers;
	private IntBuffer vboPosBuffers;
	private IntBuffer vboColorBuffers;

	public Wireframe(List<ModelBuffer> buffers) {
		this.buffers = buffers;
		propertyUpdated = false;

		this.generateBuffers();
	}
	private void generateBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numBuffers = buffers.size();
		vaoBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboIndexBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboPosBuffers = Buffers.newDirectIntBuffer(numBuffers);
		vboColorBuffers = Buffers.newDirectIntBuffer(numBuffers);

		gl.glGenVertexArrays(numBuffers, vaoBuffers);
		gl.glGenBuffers(numBuffers, vboIndexBuffers);
		gl.glGenBuffers(numBuffers, vboPosBuffers);
		gl.glGenBuffers(numBuffers, vboColorBuffers);

		for (int i = 0; i < numBuffers; i++) {
			ModelBuffer buffer = buffers.get(i);

			FloatBuffer posBuffer = buffer.getPosBuffer();
			// Position
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPosBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
					posBuffer, GL3ES3.GL_DYNAMIC_DRAW);

			// Generate a color buffer.
			Color colorDiffuse = buffer.getColorDiffuse();
			int numVertices = posBuffer.capacity() / 3;
			FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(numVertices * 4);
			for (int j = 0; j < numVertices; j++) {
				colorBuffer.put(colorDiffuse.getR());
				colorBuffer.put(colorDiffuse.getG());
				colorBuffer.put(colorDiffuse.getB());
				colorBuffer.put(colorDiffuse.getA());
			}
			colorBuffer.flip();

			// Color
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColorBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * colorBuffer.capacity(),
					colorBuffer, GL3ES3.GL_STATIC_DRAW);

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
			// Color
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColorBuffers.get(i));
			gl.glEnableVertexAttribArray(1);
			gl.glVertexAttribPointer(1, 4, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);
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

			gl.glBindVertexArray(vaoBuffers.get(i));
			// Position
			gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPosBuffers.get(i));
			gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
					posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
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
		gl.glDeleteBuffers(numBuffers, vboColorBuffers);

		if (deleteTextures == true) {
			for (var buffer : buffers) {
				buffer.getTexture().destroy(gl);
			}
		}
	}

	public int getNumBuffers() {
		return buffers.size();
	}

	public int setWireframeColor(int index, Color color) {
		if (!(0 <= index && index < buffers.size())) {
			return -1;
		}

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		ModelBuffer buffer = buffers.get(index);

		FloatBuffer posBuffer = buffer.getPosBuffer();
		int numVertices = posBuffer.capacity() / 3;

		FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(numVertices * 4);
		for (int j = 0; j < numVertices; j++) {
			colorBuffer.put(color.getR());
			colorBuffer.put(color.getG());
			colorBuffer.put(color.getB());
			colorBuffer.put(color.getA());
		}
		colorBuffer.flip();

		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColorBuffers.get(index));
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * colorBuffer.capacity(),
				colorBuffer, GL3ES3.GL_STATIC_DRAW);

		return 0;
	}

	public void draw(ShaderProgram program) {
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
			for (int j = 0; j < countIndices; j += 3) {
				gl.glDrawElements(GL3ES3.GL_LINE_LOOP, 3, GL3ES3.GL_UNSIGNED_INT,
						j * Buffers.SIZEOF_INT);
			}
			gl.glDisable(GL3ES3.GL_BLEND);
			gl.glBindVertexArray(vaoBuffers.get(0));
		}
	}

	public Wireframe transform(Matrix m) {
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

		return this;
	}
	public Wireframe translate(Vector translation) {
		super.translate(translation);

		var mTranslation = Matrix.createTranslationMatrix(translation.getX(), translation.getY(),
				translation.getZ());
		this.transform(mTranslation);

		return this;
	}
	public Wireframe rescale(Vector scale) {
		super.rescale(scale);

		var mScaling = Matrix.createScalingMatrix(scale.getX(), scale.getY(), scale.getZ());
		this.transform(mScaling);

		return this;
	}
	public Wireframe rotX(double th) {
		super.rotX(th);

		var mRotX = Matrix.createRotationXMatrix(th);
		this.transform(mRotX);

		return this;
	}
	public Wireframe rotY(double th) {
		super.rotY(th);

		var mRotY = Matrix.createRotationYMatrix(th);
		this.transform(mRotY);

		return this;
	}
	public Wireframe rotZ(double th) {
		super.rotZ(th);

		var mRotZ = Matrix.createRotationZMatrix(th);
		this.transform(mRotZ);

		return this;
	}
	public Wireframe rot(Vector axis, double th) {
		super.rot(axis, th);

		var mRot = Matrix.createRotationMatrix(axis.getX(), axis.getY(), axis.getZ(), th);
		this.transform(mRot);

		return this;
	}

	public List<ModelBuffer> getBuffers() {
		return buffers;
	}
}
