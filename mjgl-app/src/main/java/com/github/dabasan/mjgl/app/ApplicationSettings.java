package com.github.dabasan.mjgl.app;

import com.jogamp.opengl.GLProfile;

/**
 * Application settings
 * 
 * @author Daba
 *
 */
public class ApplicationSettings {
	private String profile;
	private int fps;

	public ApplicationSettings() {
		profile = GLProfile.GL3;
		fps = 30;
	}

	@Override
	public String toString() {
		return "ApplicationSettings [profile=" + profile + ", fps=" + fps + "]";
	}

	public String getProfile() {
		return profile;
	}
	public int getFPS() {
		return fps;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	public void setFPS(int fps) {
		this.fps = fps;
	}
}
