package com.github.dabasan.mjgl.app;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides ReentrantLock to exclusively run OpenGL operations in multiple
 * windows.
 * 
 * @author Daba
 *
 */
class GLLock {
	private static Lock lock;

	static {
		lock = new ReentrantLock();
	}

	public static void lock() {
		lock.lock();
	}
	public static void unlock() {
		lock.unlock();
	}
}
