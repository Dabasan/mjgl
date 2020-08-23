package com.github.dabasan.mjgl.al;

import java.io.IOException;

import com.github.dabasan.mjgl.al.sound.Sound;
import com.jogamp.openal.util.ALut;

/**
 * Test class for Sound
 * 
 * @author Daba
 *
 */
public class SoundTest {
	public static void main(String[] args) {
		ALut.alutInit();

		Sound sound;
		try {
			sound = new Sound("./Data/test.wav");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		sound.play();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		sound.stop();

		ALut.alutExit();
	}
}
