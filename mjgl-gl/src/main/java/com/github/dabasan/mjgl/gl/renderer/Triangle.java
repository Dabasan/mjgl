package com.github.dabasan.mjgl.gl.renderer;

/**
 * Triangle
 * 
 * @author Daba
 *
 */
public class Triangle {
	private Vertex[] vertices;

	public Triangle() {
		vertices = new Vertex[3];
		for (int i = 0; i < 3; i++) {
			vertices[i] = new Vertex();
		}
	}

	public Vertex[] getVertices() {
		return vertices;
	}
	public Vertex getVertex(int index) {
		return vertices[index];
	}

	public void setVertices(Vertex[] vertices) {
		if (vertices.length != 3) {
			return;
		}

		this.vertices = vertices;
	}
}
