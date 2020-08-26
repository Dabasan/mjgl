package com.github.dabasan.mjgl.gl.util.shader;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;
import com.github.dabasan.mjgl.gl.transferrer.QuadTransferrerWithUV;

/**
 * Program driver<br>
 * Used to test fragment shaders.
 * 
 * @author Daba
 *
 */
public class ProgramDriver {
	private ShaderProgram program;
	private QuadTransferrerWithUV transferrer;

	public ProgramDriver(ShaderProgram program) {
		this.program = program;
		transferrer = new QuadTransferrerWithUV(true);
	}

	public void transfer() {
		program.enable();
		transferrer.transfer();
	}
}
