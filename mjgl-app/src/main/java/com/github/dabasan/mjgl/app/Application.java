package com.github.dabasan.mjgl.app;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.util.AnimatorBase;

/**
 * Application
 * 
 * @author Daba
 *
 */
public class Application extends ApplicationBase {
	public Application() {

	}

	/**
	 * Embodies a window.
	 * 
	 * @param window
	 *            Window
	 */
	public void embodyWindow(Window window) {
		GLCapabilities caps = this.getGLCapabilities();
		AnimatorBase animator = this.getAnimator();

		window.embody(caps, animator);
	}
}
