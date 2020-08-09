package com.github.dabasan.mjgl.app;

/**
 * Window settings
 * 
 * @author Daba
 *
 */
public class WindowSettings {
	private String title;
	private int windowWidth;
	private int windowHeight;
	private boolean exitWhenDestroyed;
	private boolean visible;
	private boolean pointerVisible;
	private boolean fullscreen;

	public WindowSettings() {
		title = "Window";
		windowWidth = 640;
		windowHeight = 480;
		exitWhenDestroyed = false;
		visible = true;
		pointerVisible = true;
		fullscreen = false;
	}

	@Override
	public String toString() {
		return "WindowSettings [title=" + title + ", windowWidth=" + windowWidth + ", windowHeight="
				+ windowHeight + ", exitWhenDestroyed=" + exitWhenDestroyed + ", visible=" + visible
				+ ", pointerVisible=" + pointerVisible + ", fullscreen=" + fullscreen + "]";
	}

	public String getTitle() {
		return title;
	}
	public int getWindowWidth() {
		return windowWidth;
	}
	public int getWindowHeight() {
		return windowHeight;
	}
	public boolean isExitWhenDestroyed() {
		return exitWhenDestroyed;
	}
	public boolean isVisible() {
		return visible;
	}
	public boolean isPointerVisible() {
		return pointerVisible;
	}
	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public void setWindowSize(int width, int height) {
		windowWidth = width;
		windowHeight = height;
	}
	public void setExitWhenDestroyed(boolean exitWhenDestroyed) {
		this.exitWhenDestroyed = exitWhenDestroyed;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public void setPointerVisible(boolean pointerVisible) {
		this.pointerVisible = pointerVisible;
	}
}
