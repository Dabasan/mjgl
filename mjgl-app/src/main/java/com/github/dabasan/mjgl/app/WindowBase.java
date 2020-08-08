package com.github.dabasan.mjgl.app;

import com.github.dabasan.mjgl.input.KeyCode;
import com.github.dabasan.mjgl.input.Keyboard;
import com.github.dabasan.mjgl.input.Mouse;
import com.github.dabasan.mjgl.input.MouseCode;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.util.AnimatorBase;

/**
 * Base class for windows
 * 
 * @author Daba
 *
 */
public class WindowBase {
	private GLCapabilities caps;
	private AnimatorBase animator;

	private Keyboard keyboard;
	private Mouse mouse;

	public WindowBase(GLCapabilities caps, AnimatorBase animator) {
		this.caps = caps;
		this.animator = animator;

		keyboard = new Keyboard();
		mouse = new Mouse();
	}

	protected GLCapabilities getGLCapabilities() {
		return caps;
	}
	protected AnimatorBase getAnimator() {
		return animator;
	}

	protected Keyboard getKeyboard() {
		return keyboard;
	}
	protected Mouse getMouse() {
		return mouse;
	}

	public int getKeyboardCountPressing(KeyCode code) {
		return keyboard.getCountPressing(code);
	}
	public int getKeyboardCountReleasing(KeyCode code) {
		return keyboard.getCountReleasing(code);
	}
	public int getMouseCountPressing(MouseCode code) {
		return mouse.getCountPressing(code);
	}
	public int getMouseCountReleasing(MouseCode code) {
		return mouse.getCountReleasing(code);
	}
}
