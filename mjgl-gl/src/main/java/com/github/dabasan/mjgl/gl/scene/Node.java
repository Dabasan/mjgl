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

	public Node translate(Vector translation) {
		position = position.add(translation);
		for (var node : nodes) {
			node.translate(translation);
		}

		return this;
	}
	public Node rescale(Vector scale) {
		var mScaling = Matrix.createScalingMatrix(scale.getX(), scale.getY(), scale.getZ());

		position = position.transform(mScaling);
		for (var node : nodes) {
			node.rescale(scale);
		}

		return this;
	}
	public Node rotX(double th) {
		position = position.rotX(th);
		for (var node : nodes) {
			node.rotX(th);
		}

		return this;
	}
	public Node rotY(double th) {
		position = position.rotY(th);
		for (var node : nodes) {
			node.rotY(th);
		}

		return this;
	}
	public Node rotZ(double th) {
		position = position.rotZ(th);
		for (var node : nodes) {
			node.rotZ(th);
		}

		return this;
	}
	public Node rot(Vector axis, double th) {
		position = position.rot(axis.getX(), axis.getY(), axis.getZ(), th);
		for (var node : nodes) {
			node.rot(axis, th);
		}

		return this;
	}
	public Node transform(Matrix m) {
		position = position.transform(m);
		for (var node : nodes) {
			node.transform(m);
		}

		return this;
	}
	public Node transformSR(Matrix m) {
		position = position.transformSR(m);
		for (var node : nodes) {
			node.transformSR(m);
		}

		return this;
	}
}
