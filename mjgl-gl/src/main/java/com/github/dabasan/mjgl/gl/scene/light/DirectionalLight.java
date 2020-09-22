package com.github.dabasan.mjgl.gl.scene.light;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Directional light
 * 
 * @author Daba
 *
 */
public class DirectionalLight extends LightBase {
	public DirectionalLight() {
		this.setPosition(new Vector(1000.0, 1000.0, 1000.0));
	}

	@Override
	public void update(ShaderProgram program, int index) {
		Vector target = this.getTarget();
		Color colorAmbient = this.getColorAmbient();
		Color colorDiffuse = this.getColorDiffuse();
		Color colorSpecular = this.getColorSpecular();

		program.enable();
		if (index < 0) {
			program.setUniform("directionalLight.position", this.getPosition());
			program.setUniform("directionalLight.target", target);
			program.setUniform("directionalLight.colorAmbient", colorAmbient);
			program.setUniform("directionalLight.colorDiffuse", colorDiffuse);
			program.setUniform("directionalLight.colorSpecular", colorSpecular);
		} else {
			String strArray = "directionalLights" + "[" + index + "]";

			program.setUniform(strArray + ".position", this.getPosition());
			program.setUniform(strArray + ".target", target);
			program.setUniform(strArray + ".colorAmbient", colorAmbient);
			program.setUniform(strArray + ".colorDiffuse", colorDiffuse);
			program.setUniform(strArray + ".colorSpecular", colorSpecular);
		}
	}
}
