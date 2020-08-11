package com.github.dabasan.mjgl.gl.scene.camera;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.scene.Node;

/**
 * Base class for cameras
 * 
 * @author Daba
 *
 */
public class CameraNode extends Node {
	private double near;
	private double far;
	private double aspect;

	private Vector target;
	private Vector up;

	public CameraNode() {
		near = 1.0;
		far = 1000.0;
		aspect = (double) 640 / 480;

		target = new Vector(0.0, 0.0, 0.0);
		up = new Vector(0.0, 1.0, 0.0);
	}

	public void setNearFar(double near, double far) {
		this.near = near;
		this.far = far;
	}
	public void setTarget(Vector target) {
		this.target = target;
	}
	public void setUp(Vector up) {
		this.up = up;
	}

	public double getNear() {
		return near;
	}
	public double getFar() {
		return far;
	}
	public double getAspect() {
		return aspect;
	}
	public Vector getTarget() {
		return target;
	}
	public Vector getUp() {
		return up;
	}
}
