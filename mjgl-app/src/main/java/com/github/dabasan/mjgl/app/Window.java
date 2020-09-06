package com.github.dabasan.mjgl.app;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3ES3;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.AnimatorBase;

/**
 * Window
 * 
 * @author Daba
 *
 */
public class Window extends WindowBase implements KeyListener, MouseListener {
	private GLWindow window;

	public Window() {
		super();
	}
	public Window(WindowSettings settings) {
		super(settings);
	}

	protected GLWindow getWindow() {
		return window;
	}

	public void embody(GLCapabilities caps, AnimatorBase animator) {
		WindowSettings settings = this.getWindowSettings();

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
	public void enable() {
		GL3ES3 gl = GLContext.getCurrentGL().getGL3ES3();
		gl.glBindFramebuffer(GL3ES3.GL_FRAMEBUFFER, 0);
		gl.glViewport(0, 0, this.getWindow().getWidth(), this.getWindow().getHeight());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			this.getKeyboard().keyPressed(e.getKeyCode());
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (!e.isAutoRepeat()) {
			this.getKeyboard().keyReleased(e.getKeyCode());
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// this.mouseMoved(e);
	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}
	@Override
	public void mouseExited(MouseEvent e) {

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		/*
		Mouse mouse = this.getMouse();
		
		mouse.setCurrentCursorPos(e.getX(), e.getY());
		mouse.updateCursorDiff();
		*/
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			this.getMouse().mousePressed(e.getButton());
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!e.isAutoRepeat()) {
			this.getMouse().mouseReleased(e.getButton());
		}
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
