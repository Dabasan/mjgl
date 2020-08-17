package com.github.dabasan.mjgl.gl.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLContext;

/**
 * Capsule renderer
 * 
 * @author Daba
 *
 */
public class CapsuleRenderer extends RendererBase<Capsule> {
	private int vao;
	private int vboIndex;
	private int vboPos;
	private int vboColor;

	private int countIndices;

	public CapsuleRenderer() {
		this.generateBuffers();
		countIndices = 0;
	}
	private void generateBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffer = Buffers.newDirectIntBuffer(1);
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(3);
		gl.glGenVertexArrays(vaoBuffer.capacity(), vaoBuffer);
		gl.glGenBuffers(vboBuffers.capacity(), vboBuffers);
		vao = vaoBuffer.get(0);
		vboIndex = vboBuffers.get(0);
		vboPos = vboBuffers.get(1);
		vboColor = vboBuffers.get(2);
	}

	@Override
	public void deleteBuffers() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		IntBuffer vaoBuffers = Buffers.newDirectIntBuffer(new int[]{vao});
		IntBuffer vboBuffers = Buffers.newDirectIntBuffer(new int[]{vboIndex, vboPos, vboColor});
		gl.glDeleteVertexArrays(vaoBuffers.capacity(), vaoBuffers);
		gl.glDeleteBuffers(vboBuffers.capacity(), vboBuffers);
	}
	@Override
	public void updateBuffers() {
		var indexValues = new ArrayList<Integer>();
		var posValues = new ArrayList<Float>();
		var colorValues = new ArrayList<Float>();

		for (Capsule capsule : this.getShapes()) {
			List<Integer> indices = this.generateIndices(capsule);
			int baseIndex = indexValues.size();
			for (int index : indices) {
				indexValues.add(baseIndex + index);
			}

			List<Vector> vertices = this.generateVertices(capsule);
			Color color = capsule.getColor();
			for (int i = 0; i < vertices.size(); i++) {
				Vector vertex = vertices.get(i);
				posValues.add(vertex.getXFloat());
				posValues.add(vertex.getYFloat());
				posValues.add(vertex.getZFloat());

				colorValues.add(color.getR());
				colorValues.add(color.getG());
				colorValues.add(color.getB());
				colorValues.add(color.getA());
			}
		}

		countIndices = indexValues.size();

		IntBuffer indexBuffer = Buffers.newDirectIntBuffer(indexValues.size());
		FloatBuffer posBuffer = Buffers.newDirectFloatBuffer(posValues.size());
		FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(colorValues.size());

		for (var indexValue : indexValues) {
			indexBuffer.put(indexValue);
		}
		for (var posValue : posValues) {
			posBuffer.put(posValue);
		}
		for (var colorValue : colorValues) {
			colorBuffer.put(colorValue);
		}
		indexBuffer.flip();
		posBuffer.flip();
		colorBuffer.flip();

		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * posBuffer.capacity(),
				posBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColor);
		gl.glBufferData(GL3ES3.GL_ARRAY_BUFFER, Buffers.SIZEOF_FLOAT * colorBuffer.capacity(),
				colorBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);

		gl.glBindVertexArray(vao);
		// Index
		gl.glBindBuffer(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, vboIndex);
		gl.glBufferData(GL3ES3.GL_ELEMENT_ARRAY_BUFFER, Buffers.SIZEOF_INT * indexBuffer.capacity(),
				indexBuffer, GL3ES3.GL_DYNAMIC_DRAW);
		// Position
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboPos);
		gl.glEnableVertexAttribArray(0);
		gl.glVertexAttribPointer(0, 3, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 3, 0);
		// Color
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, vboColor);
		gl.glEnableVertexAttribArray(1);
		gl.glVertexAttribPointer(1, 4, GL3ES3.GL_FLOAT, false, Buffers.SIZEOF_FLOAT * 4, 0);
		gl.glBindBuffer(GL3ES3.GL_ARRAY_BUFFER, 0);
		gl.glBindVertexArray(0);
	}
	private List<Integer> generateIndices(Capsule capsule) {
		var indices = new ArrayList<Integer>();

		int numSlices = capsule.getNumSlices();
		int numStacks = capsule.getNumStacks();

		int numVertices = numSlices * (numStacks - 1) + 2;

		// Ridgelines around the north pole
		for (int i = 1; i <= numSlices; i++) {
			indices.add(0);
			indices.add(i);
		}

		// Ridgelines in the middle
		int count = 1;
		for (int i = 2; i < numStacks; i++) {
			for (int j = 1; j < numSlices; j++) {
				indices.add(count);
				indices.add(count + 1);

				indices.add(count);
				indices.add(count + numSlices);

				count++;
			}

			indices.add(count);
			indices.add(count - numSlices + 1);

			indices.add(count);
			indices.add(count + numSlices);

			count++;
		}

		// Ridgelines in the bottom
		for (int i = 1; i < numSlices; i++) {
			indices.add(count);
			indices.add(count + 1);

			indices.add(count);
			indices.add(numVertices - 1);

			count++;
		}

		indices.add(count);
		indices.add(count - numSlices + 1);

		indices.add(count);
		indices.add(numVertices - 1);

		return indices;
	}
	private List<Vector> generateVertices(Capsule capsule) {
		var vertices = new ArrayList<Vector>();

		Vector position1 = capsule.getPosition1();
		Vector position2 = capsule.getPosition2();
		float radius = capsule.getRadius();
		int numSlices = capsule.getNumSlices();
		int numStacks = capsule.getNumStacks();

		var capsuleAxis = position2.sub(position1);
		double d = capsuleAxis.getSize();
		double halfD = d / 2.0;

		// North pole
		vertices.add(new Vector(0.0, radius + halfD, 0.0));

		// Middle
		for (int i = 1; i < numStacks / 2; i++) {
			double ph = Math.PI * i / numStacks;
			double y = radius * Math.cos(ph) + halfD;
			double r = radius * Math.sin(ph);

			for (int j = 0; j < numSlices; j++) {
				double th = 2.0 * Math.PI * j / numSlices;
				double x = r * Math.cos(th);
				double z = r * Math.sin(th);

				vertices.add(new Vector(x, y, z));
			}
		}
		for (int i = numStacks / 2; i < numStacks; i++) {
			double ph = Math.PI * i / numStacks;
			double y = radius * Math.cos(ph) - halfD;
			double r = radius * Math.sin(ph);

			for (int j = 0; j < numSlices; j++) {
				double th = 2.0 * Math.PI * j / numSlices;
				double x = r * Math.cos(th);
				double z = r * Math.sin(th);

				vertices.add(new Vector(x, y, z));
			}
		}

		// South pole
		vertices.add(new Vector(0.0, -radius - halfD, 0.0));

		// Transformation
		double thV = capsuleAxis.getAngleV();
		double thH = capsuleAxis.getAngleH();

		Vector centerPos = position1.add(position2).scale(0.5);
		var rotZ = Matrix.createRotationZMatrix(thV - Math.PI / 2.0);
		var rotY = Matrix.createRotationYMatrix(-thH);

		for (var vertex : vertices) {
			vertex = vertex.transform(rotZ).transform(rotY).add(centerPos);
		}

		return vertices;
	}

	@Override
	public void draw(ShaderProgram program) {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();

		program.enable();

		gl.glBindVertexArray(vao);
		gl.glEnable(GL3ES3.GL_BLEND);
		gl.glDrawElements(GL3ES3.GL_LINES, countIndices, GL3ES3.GL_UNSIGNED_INT, 0);
		gl.glDisable(GL3ES3.GL_BLEND);
		gl.glBindVertexArray(0);
	}
}
