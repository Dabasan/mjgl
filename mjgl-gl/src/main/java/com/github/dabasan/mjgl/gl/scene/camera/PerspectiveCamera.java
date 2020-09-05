package com.github.dabasan.mjgl.gl.scene.camera;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.math.MathFunctions;
import com.github.dabasan.mjgl.math.MatrixFunctions;

/**
 * Perspective camera
 * 
 * @author Daba
 *
 */
public class PerspectiveCamera extends Camera {
	private double fov;

	public PerspectiveCamera() {
		fov = MathFunctions.convDegToRad(60.0);
	}

	public void setFOV(double fov) {
		this.fov = fov;
	}
	public double getFOV() {
		return fov;
	}

	@Override
	public void update(ShaderProgram program, int index) {
		super.update(program, index);

		Matrix viewTransformation = MatrixFunctions.getViewTransformationMatrix(this.getPosition(),
				this.getTarget(), this.getUp());
		Matrix projection = MatrixFunctions.getPerspectiveMatrix(fov, this.getAspect(),
				this.getNear(), this.getFar());

		Matrix vp = projection.mult(viewTransformation);

		program.enable();
		if (index < 0) {
			program.setUniform("camera.near", this.getNear());
			program.setUniform("camera.far", this.getFar());
			program.setUniform("camera.position", this.getPosition());
			program.setUniform("camera.target", this.getTarget());
			program.setUniform("camera.vp", vp, true);
		} else {
			String strArray = "cameras" + "[" + index + "]";

			program.setUniform(strArray + ".near", this.getNear());
			program.setUniform(strArray + ".far", this.getFar());
			program.setUniform(strArray + ".position", this.getPosition());
			program.setUniform(strArray + ".target", this.getTarget());
			program.setUniform(strArray + ".vp", vp, true);
		}
	}
}
