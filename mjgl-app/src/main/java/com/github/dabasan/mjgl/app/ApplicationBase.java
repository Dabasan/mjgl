package com.github.dabasan.mjgl.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.AnimatorBase;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Base class for applications
 * 
 * @author Daba
 *
 */
public class ApplicationBase {
	private Logger logger = LoggerFactory.getLogger(ApplicationBase.class);

	private GLCapabilities caps;
	private AnimatorBase animator;

	public ApplicationBase() {
		caps = new GLCapabilities(GLProfile.get(GLProfile.GL3));
		animator = new FPSAnimator(30);
	}

	public void setup(ApplicationSettings settings) {
		caps = new GLCapabilities(GLProfile.get(settings.getProfile()));
		animator = new FPSAnimator(settings.getFPS());

		logger.info("Application set up with the following settings.");
		logger.info("{}", settings.toString());
	}

	public void start() {
		animator.start();
	}
	public void pause() {
		animator.pause();
	}
	public void resume() {
		animator.resume();
	}
	public void stop() {
		animator.stop();
	}

	protected GLCapabilities getGLCapabilities() {
		return caps;
	}
	protected AnimatorBase getAnimator() {
		return animator;
	}
}
