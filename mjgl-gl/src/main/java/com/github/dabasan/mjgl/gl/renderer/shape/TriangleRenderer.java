package com.github.dabasan.mjgl.gl.renderer.shape;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Triangle renderer
 * 
 * @author Daba
 *
 */
public class TriangleRenderer extends RendererBase<Triangle> {
	private int vao;
	private int vboPos;
	private int vboUV;
	private int vboNorm;

	private String samplerName;
	private int textureUnit;
	private Texture texture;

	public TriangleRenderer(String samplerName, int textureUnit, Texture texture) {
		this.constructorBase(samplerName, textureUnit, texture);
	}
	public TriangleRenderer(Texture texture) {
		this.constructorBase("textureSampler", 0, texture);
	}
	private void constructorBase(String samplerName, int textureUnit, Texture texture) {
		this.generateBuffers();

		this.samplerName = samplerName;
		this.textureUnit = textureUnit;
		this.texture = texture;
	}
	private void generateBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(1);
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(3);
		gl.glGenVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glGenBuffers(vboBuffers.capacity(), vboBuffers);
		vao = vaoBuffer.get(0);
		vboPos = vboBuffers.get(0);
		vboUV = vboBuffers.get(1);
		vboNorm = vboBuffers.get(2);
	}

	public void setSamplerName(String samplerName) {
		this.samplerName = samplerName;
	}
	public void setTextureUnit(int textureUnit) {
		this.textureUnit = textureUnit;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	@Override
	public void deleteBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffers = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(new int[]{vboPos, vboUV, vboNorm});
		gl.glDeleteVertexArrays(vaoBuffers.capacity(), vaoBuffers);
		gl.glDeleteBuffers(vboBuffers.capacity(), vboBuffers);
	}
	@Override
	public void updateBuffers() {
		int numVertices = this.getShapes().size() * 3;

		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(numVertices * 3);
		FloatBuffer uvBuffer = Buffers.newDirectFloatBuffer(numVertices * 2);
		FloatBuffer normBuffer = Buffers.newDirectFloatBuffer(numVertices * 3);

		for (Triangle triangle : this.getShapes()) {
			for (int i = 0; i < 3; i++) {
				Vertex vertex = triangle.getVertex(i);

				Vector position = vertex.getPosition();
				UV uv = vertex.getUV();
				Vector normal = vertex.getNormal();

				posBuffer.put(position.getXFloat());
				posBuffer.put(position.getYFloat());
				posBuffer.put(position.getZFloat());
				uvBuffer.put(uv.getU());
				uvBuffer.put(uv.getV());
				normBuffer.put(normal.getXFloat());
				normBuffer.put(normal.getYFloat());
				normBuffer.put(normal.getZFloat());
			}
		}
		posBuffer.flip();
		uvBuffer.flip();
		normBuffer.flip();

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
				posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		// UV
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUV);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * uvBuffer.capacity(),
				uvBuffer, GL3ES3.GL_STATIC_DRAW);
		// Normal
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboNorm);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * normBuffer.capacity(),
				normBuffer, GL3ES3.GL_DYNAMIC_DRAW);

		gl.glBindVertexArray(vao);
		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(0, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
		// UV
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboUV);
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 2, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 2, 0);
		// Normal
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboNorm);
		gl.glEnableVertexAttribArray(2);
		gl.glVertexAttribPointer(2, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindVertexArray(0);
	}

	@Override
	public void draw(ShaderProgram program) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		int numVertices = this.getShapes().size() * 3;

		program.enable();
		program.setTexture(samplerName, textureUnit, texture);

		gl.glBindVertexArray(vao);
		gl.glEnable(GL3ES3.GL_BLEND);
		gl.glDrawArrays(GL3ES3.GL_TRIANGLES, 0, numVertices);
		gl.glDisable(GL3ES3.GL_BLEND);
		gl.glBindVertexArray(0);
	}
}
