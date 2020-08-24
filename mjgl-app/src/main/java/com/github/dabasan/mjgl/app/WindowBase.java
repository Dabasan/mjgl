package com.github.dabasan.mjgl.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.mjgl.input.KeyCode;
import com.github.dabasan.mjgl.input.Keyboard;
import com.github.dabasan.mjgl.input.Mouse;
import com.github.dabasan.mjgl.input.MouseCode;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

/**
 * Base class for windows
 * 
 * @author Daba
 *
 */
public class WindowBase implements GLEventListener {
	private Logger logger = LoggerFactory.getLogger(WindowBase.class);

	private WindowSettings settings;

	private Keyboard keyboard;
	private Mouse mouse;

	public WindowBase() {
		settings = new WindowSettings();
		this.constructorBase();
	}
	public WindowBase(WindowSettings settings) {
		this.settings = settings;
		this.constructorBase();
	}
	private void constructorBase() {
		keyboard = new Keyboard();
		mouse = new Mouse();
	}

	protected WindowSettings getWindowSettings() {
		return settings;
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

	@Override
	public void init(GLAutoDrawable drawable) {
		GLLock.lock();

		GL2ES2 gl = drawable.getGL().getGL2ES2();

		// Depth test
		gl.glEnable(GL2ES2.GL_DEPTH_TEST);
		gl.glDepthFunc(GL2ES2.GL_LESS);
		// Cull face
		gl.glEnable(GL2ES2.GL_CULL_FACE);
		gl.glCullFace(GL2ES2.GL_BACK);
		// Blend
		gl.glEnable(GL2ES2.GL_BLEND);
		gl.glBlendFunc(GL2ES2.GL_SRC_ALPHA, GL2ES2.GL_ONE_MINUS_SRC_ALPHA);

		this.init();

		GLLock.unlock();

		logger.info("Init");
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		GLLock.lock();
		this.dispose();
		GLLock.unlock();

		logger.info("Dispose");
	}
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GLLock.lock();
		this.reshape(x, y, width, height);
		GLLock.unlock();
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		keyboard.update();
		mouse.update();

		GLLock.lock();

		GL2ES2 gl = drawable.getGL().getGL2ES2();
		gl.glClear(GL2ES2.GL_COLOR_BUFFER_BIT | GL2ES2.GL_DEPTH_BUFFER_BIT
				| GL2ES2.GL_STENCIL_BUFFER_BIT);
		this.update();

		GLLock.unlock();

		mouse.resetWheelRotations();
	}

	public void init() {

	}
	public void dispose() {

	}
	public void reshape(int x, int y, int width, int height) {

	}
	public void update() {

	}
}
