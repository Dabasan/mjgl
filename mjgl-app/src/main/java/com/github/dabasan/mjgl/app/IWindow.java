package com.github.dabasan.mjgl.app;

/**
 * Interface for windows
 * 
 * @author Daba
 *
 */
public interface IWindow {
	void init();
	void dispose();
	void reshape(int x, int y, int width, int height);
	void update();
}
