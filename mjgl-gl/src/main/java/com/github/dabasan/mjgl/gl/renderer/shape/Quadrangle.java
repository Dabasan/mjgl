package com.github.dabasan.mjgl.gl.renderer.shape;

/**
 * Quadrangle
 * 
 * @author Daba
 *
 */
public class Quadrangle {
	private Vertex[] vertices;

	public Quadrangle() {
		vertices = new Vertex[4];
		for (int i = 0; i < 4; i++) {
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
		if (vertices.length != 4) {
			return;
		}

		this.vertices = vertices;
	}
}
