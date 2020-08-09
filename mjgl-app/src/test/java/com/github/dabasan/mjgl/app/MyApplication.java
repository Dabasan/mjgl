package com.github.dabasan.mjgl.app;

import com.jogamp.opengl.GLProfile;

public class MyApplication extends Application {
	public static void main(String[] args) {
		new MyApplication();
	}
	public MyApplication() {
		var appSettings = new ApplicationSettings();
		appSettings.setProfile(GLProfile.GL4);
		appSettings.setFPS(30);

		this.setup(appSettings);

		var windowSettings = new WindowSettings();
		windowSettings.setTitle("MyApplication");

		var window = new Window(windowSettings);
		this.embodyWindow(window);

		this.start();
	}
}
