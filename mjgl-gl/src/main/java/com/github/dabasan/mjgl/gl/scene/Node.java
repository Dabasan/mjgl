package com.github.dabasan.mjgl.gl.scene;

import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;

/**
 * Node
 * 
 * @author Daba
 *
 */
public class Node {
	private Vector position;
	private List<Node> nodes;

	public Node() {
		position = new Vector();
		nodes = new ArrayList<>();
	}

	public void setPosition(Vector position) {
		Vector vTranslation = position.sub(this.position);
		for (var node : nodes) {
			node.translate(vTranslation);
		}

		this.position = position;
	}
	public Vector getPosition() {
		return position;
	}

	public void attachChild(Node node) {
		nodes.add(node);
	}
	public void detachChild(Node node) {
		nodes.remove(node);
	}

	public void translate(Vector translation) {
		position = position.add(translation);
		for (var node : nodes) {
			node.translate(translation);
		}
	}
	public void rescale(Vector scale) {
		var mScaling = Matrix.createScalingMatrix(scale.getX(), scale.getY(), scale.getZ());

		position = position.transform(mScaling);
		for (var node : nodes) {
			node.rescale(scale);
		}
	}
	public void rotX(double th) {
		position = position.rotX(th);
		for (var node : nodes) {
			node.rotX(th);
		}
	}
	public void rotY(double th) {
		position = position.rotY(th);
		for (var node : nodes) {
			node.rotY(th);
		}
	}
	public void rotZ(double th) {
		position = position.rotZ(th);
		for (var node : nodes) {
			node.rotZ(th);
		}
	}
	public void rot(Vector axis, double th) {
		position = position.rot(axis.getX(), axis.getY(), axis.getZ(), th);
		for (var node : nodes) {
			node.rot(axis, th);
		}
	}
	public void transform(Matrix m) {
		position = position.transform(m);
		for (var node : nodes) {
			node.transform(m);
		}
	}
	public void transformSR(Matrix m) {
		position = position.transformSR(m);
		for (var node : nodes) {
			node.transformSR(m);
		}
	}
}
