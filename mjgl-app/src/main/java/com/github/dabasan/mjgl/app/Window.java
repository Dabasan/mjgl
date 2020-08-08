package com.github.dabasan.mjgl.app;

import com.github.dabasan.mjgl.input.Mouse;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.AnimatorBase;

/**
 * Window
 * 
 * @author Daba
 *
 */
public class Window extends WindowBase
		implements
			IWindow,
			GLEventListener,
			KeyListener,
			MouseListener {
	private GLWindow window;

	public Window(GLCapabilities caps, AnimatorBase animator) {
		super(caps, animator);

		var settings = new WindowSettings();
		this.constructorBase(caps, animator, settings);
	}
	public Window(WindowSettings settings, GLCapabilities caps, AnimatorBase animator) {
		super(caps, animator);

		this.constructorBase(caps, animator, settings);
	}
	private void constructorBase(GLCapabilities caps, AnimatorBase animator,
			WindowSettings settings) {
		// Create a window.
		window = GLWindow.create(caps);
		window.setTitle(settings.getTitle());
		window.setSize(settings.getWindowWidth(), settings.getWindowHeight());

		if (settings.isExitWhenDestroyed()) {
			window.addWindowListener(new WindowAdapter() {
				@Override
				public void windowDestroyed(WindowEvent event) {
					System.exit(0);
				}
			});
		}

		window.addGLEventListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);

		animator.add(window);

		window.setPointerVisible(settings.isPointerVisible());
		window.setFullscreen(settings.isFullscreen());
		window.setVisible(settings.isVisible());
	}

	protected GLWindow getWindow() {
		return window;
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		this.init();
	}
	@Override
	public void dispose(GLAutoDrawable drawable) {
		this.dispose();
	}
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		this.reshape(x, y, width, height);
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		this.getKeyboard().update();
		this.getMouse().update();
		this.update();
		this.getMouse().resetWheelRotations();
	}

	@Override
	public void init() {

	}
	@Override
	public void dispose() {

	}
	@Override
	public void reshape(int x, int y, int width, int height) {

	}
	@Override
	public void update() {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.getKeyboard().keyPressed(e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		this.getKeyboard().keyReleased(e.getKeyCode());
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		this.mouseMoved(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		Mouse mouse = this.getMouse();

		mouse.setCurrentCursorPos(e.getX(), e.getY());
		mouse.updateCursorDiff();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		this.getMouse().mousePressed(e.getButton());
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		this.getMouse().mouseReleased(e.getButton());
	}
	@Override
	public void mouseWheelMoved(MouseEvent e) {
		float rotationScale = e.getRotationScale();
		float[] rotations = e.getRotation();
		for (int i = 0; i < 3; i++) {
			rotations[i] = rotations[i] * rotationScale;
		}

		this.getMouse().setWheelRotations(rotations[0], rotations[1], rotations[2]);
	}
}
