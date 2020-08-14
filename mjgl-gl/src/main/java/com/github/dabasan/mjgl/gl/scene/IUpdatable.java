package com.github.dabasan.mjgl.gl.scene;

import com.github.dabasan.mjgl.gl.shader.ShaderProgram;

import java.util.List;

/**
 * Interface for updatable objects
 * 
 * @author Daba
 *
 */
public interface IUpdatable {
	void update(List<ShaderProgram> programs);
}
