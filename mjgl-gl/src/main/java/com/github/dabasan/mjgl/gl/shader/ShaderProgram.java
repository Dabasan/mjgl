package com.github.dabasan.mjgl.gl.shader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.ejml_3dtools.Matrix;
import com.github.dabasan.ejml_3dtools.Vector;
import com.github.dabasan.mjgl.gl.Color;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.texture.Texture;

/**
 * Shader program
 * 
 * @author Daba
 *
 */
public class ShaderProgram {
	private Logger logger = LoggerFactory.getLogger(ShaderProgram.class);

	private int programID;
	private String tag;

	public ShaderProgram(int programID) {
		this.programID = programID;
		tag = "";
	}
	public ShaderProgram(InputStream isVShader, InputStream isFShader) throws IOException {
		this.readConstructorBase(isVShader, isFShader);
	}
	public ShaderProgram(File fileVShader, File fileFShader) throws IOException {
		logger.info("Creates a program. vShader: {} fShader: {}", fileVShader.getPath(),
				fileFShader.getPath());

		try (var fisVShader = new FileInputStream(fileVShader);
				var fisFShader = new FileInputStream(fileFShader)) {
			this.readConstructorBase(fisVShader, fisFShader);
		}
	}
	public ShaderProgram(String filepathVShader, String filepathFShader) throws IOException {
		logger.info("Creates a program. vShader: {} fShader: {}", filepathVShader, filepathFShader);

		try (var fisVShader = new FileInputStream(filepathVShader);
				var fisFShader = new FileInputStream(filepathFShader)) {
			this.readConstructorBase(fisVShader, fisFShader);
		}
	}
	private void readConstructorBase(InputStream isVShader, InputStream isFShader)
			throws IOException {
		programID = ShaderFunctions.createProgram(isVShader, isFShader);
		tag = "";
	}

	public boolean isValid() {
		return (programID < 0) ? false : true;
	}

	public int getProgramID() {
		return programID;
	}

	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public void enable() {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();
		gl.glUseProgram(programID);
	}

	// int
	public void setUniform(String name, int value) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform1i(location, value);
	}
	public void setUniform(String name, int value0, int value1) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform2i(location, value0, value1);
	}
	public void setUniform(String name, int value0, int value1, int value2) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform3i(location, value0, value1, value2);
	}
	public void setUniform(String name, int value0, int value1, int value2, int value3) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform4i(location, value0, value1, value2, value3);
	}
	// float
	public void setUniform(String name, float value) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform1f(location, value);
	}
	public void setUniform(String name, float value0, float value1) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform2f(location, value0, value1);
	}
	public void setUniform(String name, float value0, float value1, float value2) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform3f(location, value0, value1, value2);
	}
	public void setUniform(String name, float value0, float value1, float value2, float value3) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform4f(location, value0, value1, value2, value3);
	}
	// double
	public void setUniform(String name, double value) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform1f(location, (float) value);
	}
	public void setUniform(String name, double value0, double value1) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform2f(location, (float) value0, (float) value1);
	}
	public void setUniform(String name, double value0, double value1, double value2) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform3f(location, (float) value0, (float) value1, (float) value2);
	}
	public void setUniform(String name, double value0, double value1, double value2,
			double value3) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform4f(location, (float) value0, (float) value1, (float) value2, (float) value3);
	}
	// Vector
	public void setUniform(String name, Vector value) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform3f(location, value.getXFloat(), value.getYFloat(), value.getZFloat());
	}
	// Color
	public void setUniform(String name, Color value) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniform4f(location, value.getR(), value.getG(), value.getB(), value.getA());
	}
	// Matrix
	public void setUniform(String name, Matrix value, boolean transpose) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		FloatBuffer buffer = Buffers.newDirectFloatBuffer(16);
		double[] arr = value.toArray();
		for (int i = 0; i < 16; i++) {
			buffer.put(i, (float) arr[i]);
		}

		int location = gl.glGetUniformLocation(programID, name);
		gl.glUniformMatrix4fv(location, 1, transpose, buffer);
	}

	// Texture
	public void setTexture(String name, int textureUnit, Texture texture) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glActiveTexture(GL2ES2.GL_TEXTURE0 + textureUnit);
		texture.bind(gl);
		gl.glUniform1i(location, textureUnit);
	}
	public void setTexture(String name, int textureUnit, int textureTarget, int texture) {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int location = gl.glGetUniformLocation(programID, name);
		gl.glActiveTexture(GL2ES2.GL_TEXTURE0 + textureUnit);
		gl.glBindTexture(textureTarget, texture);
		gl.glUniform1i(location, textureUnit);
	}
}
