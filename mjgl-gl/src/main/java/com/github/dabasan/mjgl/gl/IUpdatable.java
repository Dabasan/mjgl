package com.github.dabasan.mjgl.gl;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

/**
 * Interface for updatable objects
 * 
 * @author Daba
 *
 */
public interface IUpdatable {
	void update(ShaderProgram program, int index);
}
