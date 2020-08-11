package com.github.dabasan.mjgl.gl.scene.camera;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;

/**
 * Matrix functions
 * 
 * @author Daba
 *
 */
class MatrixFunctions {
	public static Matrix getViewTransformationMatrix(Vector position, Vector target, Vector up) {
		var mTranslation = Matrix.createTranslationMatrix(position.getX() * (-1.0),
				position.getY() * (-1.0), position.getZ() * (-1.0));

		Vector viewCoordZ = position.sub(target).normalize();
		Vector viewCoordX = up.cross(viewCoordZ).normalize();
		Vector viewCoordY = viewCoordZ.cross(viewCoordX);

		var mRot = new Matrix();
		mRot.set(0, 0, viewCoordX.getX());
		mRot.set(0, 1, viewCoordX.getY());
		mRot.set(0, 2, viewCoordX.getZ());
		mRot.set(0, 3, 0.0);
		mRot.set(1, 0, viewCoordY.getX());
		mRot.set(1, 1, viewCoordY.getY());
		mRot.set(1, 2, viewCoordY.getZ());
		mRot.set(1, 3, 0.0);
		mRot.set(2, 0, viewCoordZ.getX());
		mRot.set(2, 1, viewCoordZ.getY());
		mRot.set(2, 2, viewCoordZ.getZ());
		mRot.set(2, 3, 0.0);
		mRot.set(3, 0, 0.0);
		mRot.set(3, 1, 0.0);
		mRot.set(3, 2, 0.0);
		mRot.set(3, 3, 1.0);

		return mRot.mult(mTranslation);
	}

	public static Matrix getOrthogonalMatrix(double left, double right, double bottom, double top,
			double near, double far) {
		var mTranslation = Matrix.createTranslationMatrix(-(right + left) / 2.0,
				-(bottom + top) / 2.0, (far + near) / 2.0);
		var mScale = Matrix.createScalingMatrix(2.0 / (right - left), 2.0 / (top - bottom),
				-2.0 / (far - near));

		return mScale.mult(mTranslation);
	}

	public static Matrix getPerspectiveMatrix(double fov, double aspect, double near, double far) {
		double f = 1.0 / Math.tan(fov / 2.0);

		var ret = new Matrix();
		ret.set(0, 0, f / aspect);
		ret.set(0, 1, 0.0);
		ret.set(0, 2, 0.0);
		ret.set(0, 3, 0.0);
		ret.set(1, 0, 0.0);
		ret.set(1, 1, f);
		ret.set(1, 2, 0.0);
		ret.set(1, 3, 0.0);
		ret.set(2, 0, 0.0);
		ret.set(2, 1, 0.0);
		ret.set(2, 2, -(far + near) / (far - near));
		ret.set(2, 3, -2.0 * far * near / (far - near));
		ret.set(3, 0, 0.0);
		ret.set(3, 1, 0.0);
		ret.set(3, 2, -1.0);
		ret.set(3, 3, 0.0);

		return ret;
	}
}
