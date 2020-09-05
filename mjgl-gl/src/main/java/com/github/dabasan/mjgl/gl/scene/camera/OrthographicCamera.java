package com.github.dabasan.mjgl.gl.scene.camera;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.math.MatrixFunctions;

/**
 * Orthographic camera
 * 
 * @author Daba
 *
 */
public class OrthographicCamera extends Camera {
	private double size;

	public OrthographicCamera() {
		size = 10.0;
	}

	public void setSize(double size) {
		this.size = size;
	}
	public double getSize() {
		return size;
	}

	@Override
	public void update(ShaderProgram program, int index) {
		super.update(program, index);

		Matrix viewTransformation = MatrixFunctions.getViewTransformationMatrix(this.getPosition(),
				this.getTarget(), this.getUp());
		Matrix projection = MatrixFunctions.getOrthogonalMatrix(-size, size, -size, size,
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
