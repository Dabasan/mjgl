package com.github.dabasan.mjgl.input;

/**
 * Input reservoir
 * 
 * @author Daba
 *
 */
class InputReservoir {
	private int numElements;

	private int[] countPressing;
	private int[] countReleasing;
	private boolean[] flagPressing;

	public InputReservoir(int numElements) {
		this.numElements = numElements;

		countPressing = new int[numElements];
		countReleasing = new int[numElements];
		flagPressing = new boolean[numElements];

		this.reset();
	}

	public void reset() {
		for (int i = 0; i < numElements; i++) {
			countPressing[i] = 0;
			countReleasing[i] = 0;
			flagPressing[i] = false;
		}
	}

	public void setFlagPressing(int index, boolean flag) {
		flagPressing[index] = flag;
	}

	public int getCountPressing(int index) {
		return countPressing[index];
	}
	public int getCountReleasing(int index) {
		return countReleasing[index];
	}

	public void update() {
		for (int i = 0; i < numElements; i++) {
			if (flagPressing[i] == true) {
				countPressing[i]++;
				if (countReleasing[i] != 0) {
					countReleasing[i] = 0;
				}
			} else {
				countReleasing[i]++;
				if (countPressing[i] != 0) {
					countPressing[i] = 0;
				}
			}
		}
	}
}
