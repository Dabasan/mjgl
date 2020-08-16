package com.github.dabasan.mjgl.gl.shader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;

/**
 * Shader functions
 * 
 * @author Daba
 *
 */
class ShaderFunctions {
	private static Logger logger = LoggerFactory.getLogger(ShaderFunctions.class);

	public static int createProgram(InputStream isVShader, InputStream isFShader)
			throws IOException {
		GL2ES2 gl = GLContext.getCurrentGL().getGL2ES2();

		int vShaderID = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
		int fShaderID = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);

		// Load vertex shader.
		var vShaderCode = new ArrayList<String>();
		try (var brVShader = new BufferedReader(new InputStreamReader(isVShader, "UTF-8"))) {
			while (true) {
				String line = brVShader.readLine();
				if (line == null) {
					break;
				}

				vShaderCode.add(line);
			}
		}

		// Load fragment shader.
		var fShaderCode = new ArrayList<String>();
		try (var brFShader = new BufferedReader(new InputStreamReader(isFShader, "UTF-8"))) {
			while (true) {
				String line = brFShader.readLine();
				if (line == null) {
					break;
				}

				fShaderCode.add(line);
			}
		}

		String[] vShaderLines = new String[vShaderCode.size()];
		vShaderCode.toArray(vShaderLines);

		String[] fShaderLines = new String[fShaderCode.size()];
		fShaderCode.toArray(fShaderLines);

		// Append LF to every line.
		for (int i = 0; i < vShaderLines.length; i++) {
			vShaderLines[i] += "\n";
		}
		for (int i = 0; i < fShaderLines.length; i++) {
			fShaderLines[i] += "\n";
		}

		IntBuffer result = Buffers.newDirectIntBuffer(1);

		// Compile vertex shader.
		gl.glShaderSource(vShaderID, vShaderLines.length, vShaderLines, null);
		gl.glCompileShader(vShaderID);

		// Check vertex shader.
		gl.glGetShaderiv(vShaderID, GL2ES2.GL_COMPILE_STATUS, result);
		if (result.get(0) == GL2ES2.GL_FALSE) {
			String errMessage = getShaderInfoLog(gl, vShaderID);

			logger.error("Vertex shader compilation failed.");
			logger.error(errMessage);

			return -1;
		}

		// Compile fragment shader.
		gl.glShaderSource(fShaderID, fShaderLines.length, fShaderLines, null);
		gl.glCompileShader(fShaderID);

		// Check fragment shader.
		gl.glGetShaderiv(fShaderID, GL2ES2.GL_COMPILE_STATUS, result);
		if (result.get(0) == GL2ES2.GL_FALSE) {
			String errMessage = getShaderInfoLog(gl, fShaderID);

			logger.error("Fragment shader compilation failed.");
			logger.error(errMessage);

			return -1;
		}

		// Link program.
		int programID = gl.glCreateProgram();
		gl.glAttachShader(programID, vShaderID);
		gl.glAttachShader(programID, fShaderID);
		gl.glLinkProgram(programID);

		// Check program.
		gl.glGetProgramiv(programID, GL2ES2.GL_LINK_STATUS, result);
		if (result.get(0) == GL2ES2.GL_FALSE) {
			String errMessage = getProgramInfoLog(gl, programID);

			logger.error("Program link failed.");
			logger.error(errMessage);

			return -1;
		}

		gl.glDeleteShader(vShaderID);
		gl.glDeleteShader(fShaderID);

		logger.info("Successfully created a program. programID={}", programID);

		return programID;
	}
	private static String getShaderInfoLog(GL2ES2 gl, int shaderID) {
		IntBuffer infoLogLength = Buffers.newDirectIntBuffer(1);
		gl.glGetShaderiv(shaderID, GL2ES2.GL_INFO_LOG_LENGTH, infoLogLength);

		ByteBuffer buffer = Buffers.newDirectByteBuffer(infoLogLength.get(0));
		gl.glGetShaderInfoLog(shaderID, infoLogLength.get(0), null, buffer);

		var arrBytes = new byte[buffer.remaining()];
		buffer.get(arrBytes);
		buffer.flip();

		return new String(arrBytes);
	}
	private static String getProgramInfoLog(GL2ES2 gl, int programID) {
		IntBuffer infoLogLength = Buffers.newDirectIntBuffer(1);
		gl.glGetProgramiv(programID, GL2ES2.GL_INFO_LOG_LENGTH, infoLogLength);

		ByteBuffer buffer = Buffers.newDirectByteBuffer(infoLogLength.get(0));
		gl.glGetProgramInfoLog(programID, infoLogLength.get(0), null, buffer);

		var arrBytes = new byte[buffer.remaining()];
		buffer.get(arrBytes);
		buffer.flip();

		return new String(arrBytes);
	}
}
