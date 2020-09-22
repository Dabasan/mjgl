package com.github.dabasan.mjgl.gl.scene.light;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.math.MathFunctions;

/**
 * Spotlight
 * 
 * @author Daba
 *
 */
public class SpotLight extends LightBase {
	private float k0;
	private float k1;
	private float k2;
	private float phi;
	private float theta;
	private float falloff;
	private float colorClampMin;
	private float colorClampMax;

	public SpotLight() {
		k0 = 0.0f;
		k1 = 0.01f;
		k2 = 0.0f;
		phi = (float) MathFunctions.convDegToRad(50.0);
		theta = (float) MathFunctions.convDegToRad(30.0);
		falloff = 1.0f;
		colorClampMin = 0.0f;
		colorClampMax = 1.0f;
	}

	public float getK0() {
		return k0;
	}
	public float getK1() {
		return k1;
	}
	public float getK2() {
		return k2;
	}
	public float getPhi() {
		return phi;
	}
	public float getTheta() {
		return theta;
	}
	public float getFalloff() {
		return falloff;
	}
	public float getColorClampMin() {
		return colorClampMin;
	}
	public float getColorClampMax() {
		return colorClampMax;
	}

	public void setK0(float k0) {
		this.k0 = k0;
	}
	public void setK1(float k1) {
		this.k1 = k1;
	}
	public void setK2(float k2) {
		this.k2 = k2;
	}
	public void setPhi(float phi) {
		this.phi = phi;
	}
	public void setTheta(float theta) {
		this.theta = theta;
	}
	public void setFalloff(float falloff) {
		this.falloff = falloff;
	}
	public void setColorClampMin(float colorClampMin) {
		this.colorClampMin = colorClampMin;
	}
	public void setColorClampMax(float colorClampMax) {
		this.colorClampMax = colorClampMax;
	}

	@Override
	public void update(ShaderProgram program, int index) {
		Vector target = this.getTarget();
		Color colorDiffuse = this.getColorDiffuse();
		Color colorSpecular = this.getColorSpecular();

		program.enable();
		if (index < 0) {
			program.setUniform("spotLight.position", this.getPosition());
			program.setUniform("spotLight.target", target);
			program.setUniform("spotLight.k0", k0);
			program.setUniform("spotLight.k1", k1);
			program.setUniform("spotLight.k2", k2);
			program.setUniform("spotLight.phi", phi);
			program.setUniform("spotLight.theta", theta);
			program.setUniform("spotLight.falloff", falloff);
			program.setUniform("spotLight.colorDiffuse", colorDiffuse);
			program.setUniform("spotLight.colorSpecular", colorSpecular);
			program.setUniform("spotLight.colorClampMin", colorClampMin);
			program.setUniform("spotLight.colorClampMax", colorClampMax);
		} else {
			String strArray = "spotLights" + "[" + index + "]";

			program.setUniform(strArray + ".position", this.getPosition());
			program.setUniform(strArray + ".target", target);
			program.setUniform(strArray + ".k0", k0);
			program.setUniform(strArray + ".k1", k1);
			program.setUniform(strArray + ".k2", k2);
			program.setUniform(strArray + ".phi", phi);
			program.setUniform(strArray + ".theta", theta);
			program.setUniform(strArray + ".falloff", falloff);
			program.setUniform(strArray + ".colorDiffuse", colorDiffuse);
			program.setUniform(strArray + ".colorSpecular", colorSpecular);
			program.setUniform(strArray + ".colorClampMin", colorClampMin);
			program.setUniform(strArray + ".colorClampMax", colorClampMax);
		}
	}
}
