package com.github.dabasan.mjgl.al;

import java.io.IOException;

import com.github.dabasan.mjgl.al.player.MP3Player;

/**
 * Test class for MP3Player
 * 
 * @author Daba
 *
 */
public class MP3PlayerTest {
	public static void main(String[] args) {
		MP3Player player;
		try {
			player = new MP3Player("./Data/test.mp3");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		player.play();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}

		// player.stop();
		player.close();
	}
}
