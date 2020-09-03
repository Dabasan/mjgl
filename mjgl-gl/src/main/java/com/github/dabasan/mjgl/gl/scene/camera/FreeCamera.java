package com.github.dabasan.mjgl.gl.scene.camera;

import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.math.MathFunctions;

/**
 * Free camera
 * 
 * @author Daba
 *
 */
public class FreeCamera extends PerspectiveCamera {
	private float angleV;
	private float angleH;

	private float speedTranslation;
	private float speedRotation;

	private static final float VROT_MIN = (float) MathFunctions.convDegToRad(-80.0);
	private static final float VROT_MAX = (float) MathFunctions.convDegToRad(80.0);

	public FreeCamera() {
		angleV = 0.0f;
		angleH = 0.0f;

		speedTranslation = 0.3f;
		speedRotation = 0.01f;
	}

	public float getAngleV() {
		return angleV;
	}
	public float getAngleH() {
		return angleH;
	}
	public float getSpeedTranslation() {
		return speedTranslation;
	}
	public float getSpeedRotation() {
		return speedRotation;
	}

	public void setAngles(float angleV, float angleH) {
		this.angleV = angleV;
		this.angleH = angleH;
	}
	public void setAngleV(float angleV) {
		this.angleV = angleV;
	}
	public void setAngleH(float angleH) {
		this.angleH = angleH;
	}
	public void setSpeedTranslation(float speedTranslation) {
		this.speedTranslation = speedTranslation;
	}
	public void setSpeedRotation(float speedRotation) {
		this.speedRotation = speedRotation;
	}

	public void translate(boolean front, boolean back, boolean right, boolean left) {
		var translation = new Vector();

		var vecFront = new Vector(angleV, angleH);
		var vecRight = vecFront.cross(new Vector(0.0, 1.0, 0.0)).normalize();

		if (front) {
			translation = translation.add(vecFront);
		}
		if (back) {
			translation = translation.add(vecFront.scale(-1.0));
		}
		if (right) {
			translation = translation.add(vecRight);
		}
		if (left) {
			translation = translation.add(vecRight.scale(-1.0));
		}

		if (translation.getSquareSize() > 1.0E-16) {
			translation = translation.normalize().scale(speedTranslation);
			this.translate(translation);
		}
	}
	public void rotate(int amountV, int amountH) {
		angleV += speedRotation * amountV;
		angleH += speedRotation * amountH;

		if (angleV < VROT_MIN) {
			angleV = VROT_MIN;
		} else if (angleV > VROT_MAX) {
			angleV = VROT_MAX;
		}

		if (angleH < -Math.PI) {
			angleH += Math.PI * 2.0f;
		} else if (angleH > Math.PI) {
			angleH -= Math.PI * 2.0f;
		}
	}

	@Override
	public void update(ShaderProgram program) {
		var direction = new Vector(angleV, angleH);
		var target = this.getPosition().add(direction);
		this.setTarget(target);

		super.update(program);
	}
}
