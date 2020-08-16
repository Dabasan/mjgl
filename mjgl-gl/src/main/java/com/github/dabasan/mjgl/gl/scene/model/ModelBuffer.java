package com.github.dabasan.mjgl.gl.scene.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.github.dabasan.mjgl.gl.Color;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Model buffer
 * 
 * @author Daba
 *
 */
public class ModelBuffer {
	private IntBuffer indexBuffer;
	private FloatBuffer posBuffer;
	private FloatBuffer uvBuffer;
	private FloatBuffer normBuffer;

	private Color colorAmbient;
	private Color colorDiffuse;
	private Color colorSpecular;
	private float specularExponent;
	private float dissolve;

	private Texture texture;

	public ModelBuffer() {
		colorAmbient = Color.WHITE;
		colorDiffuse = Color.WHITE;
		colorSpecular = Color.BLACK;
		specularExponent = 10.0f;
		dissolve = 1.0f;
	}

	public int getCountIndices() {
		return indexBuffer.capacity();
	}
	public IntBuffer getIndexBuffer() {
		return indexBuffer;
	}
	public FloatBuffer getPosBuffer() {
		return posBuffer;
	}
	public FloatBuffer getUVBuffer() {
		return uvBuffer;
	}
	public FloatBuffer getNormBuffer() {
		return normBuffer;
	}
	public Color getColorAmbient() {
		return colorAmbient;
	}
	public Color getColorDiffuse() {
		return colorDiffuse;
	}
	public Color getColorSpecular() {
		return colorSpecular;
	}
	public float getSpecularExponent() {
		return specularExponent;
	}
	public float getDissolve() {
		return dissolve;
	}
	public Texture getTexture() {
		return texture;
	}

	public void setIndexBuffer(IntBuffer indexBuffer) {
		this.indexBuffer = indexBuffer;
	}
	public void setPosBuffer(FloatBuffer posBuffer) {
		this.posBuffer = posBuffer;
	}
	public void setUVBuffer(FloatBuffer uvBuffer) {
		this.uvBuffer = uvBuffer;
	}
	public void setNormBuffer(FloatBuffer normBuffer) {
		this.normBuffer = normBuffer;
	}
	public void setColorAmbient(Color colorAmbient) {
		this.colorAmbient = colorAmbient;
	}
	public void setColorDiffuse(Color colorDiffuse) {
		this.colorDiffuse = colorDiffuse;
	}
	public void setColorSpecular(Color colorSpecular) {
		this.colorSpecular = colorSpecular;
	}
	public void setSpecularExponent(float specularExponent) {
		this.specularExponent = specularExponent;
	}
	public void setDissolve(float dissolve) {
		this.dissolve = dissolve;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
