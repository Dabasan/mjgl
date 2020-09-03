package com.github.dabasan.mjgl.input;

import com.jogamp.newt.event.MouseEvent;

/**
 * Mouse
 * 
 * @author Daba
 *
 */
public class Mouse {
	private InputReservoir reservoir;

	private int lastCursorPosX;
	private int lastCursorPosY;
	private int currentCursorPosX;
	private int currentCursorPosY;
	private int cursorDiffX;
	private int cursorDiffY;
	private float[] wheelRotations;

	public Mouse() {
		int numCodes = MouseCode.values().length;
		reservoir = new InputReservoir(numCodes);

		lastCursorPosX = 0;
		lastCursorPosY = 0;
		currentCursorPosX = 0;
		currentCursorPosY = 0;
		cursorDiffX = 0;
		cursorDiffY = 0;
		wheelRotations = new float[3];
	}

	public void reset() {
		reservoir.reset();
	}

	public void setCurrentCursorPos(int x, int y) {
		lastCursorPosX = currentCursorPosX;
		lastCursorPosY = currentCursorPosY;

		currentCursorPosX = x;
		currentCursorPosY = y;
	}
	public void updateCursorDiff() {
		cursorDiffX = currentCursorPosX - lastCursorPosX;
		cursorDiffY = currentCursorPosY - lastCursorPosY;
	}

	public void setWheelRotations(float vertical, float horizontal, float z) {
		wheelRotations[0] = vertical;
		wheelRotations[1] = horizontal;
		wheelRotations[2] = z;
	}
	public void resetWheelRotations() {
		wheelRotations[0] = 0.0f;
		wheelRotations[1] = 0.0f;
		wheelRotations[2] = 0.0f;
	}

	public void mousePressed(short mouseButton) {
		switch (mouseButton) {
			case MouseEvent.BUTTON1 :
				reservoir.setFlagPressing(MouseCode.LEFT.ordinal(), true);
				break;
			case MouseEvent.BUTTON2 :
				reservoir.setFlagPressing(MouseCode.MIDDLE.ordinal(), true);
				break;
			case MouseEvent.BUTTON3 :
				reservoir.setFlagPressing(MouseCode.RIGHT.ordinal(), true);
				break;
			case MouseEvent.BUTTON4 :
				reservoir.setFlagPressing(MouseCode.BUTTON4.ordinal(), true);
				break;
			case MouseEvent.BUTTON5 :
				reservoir.setFlagPressing(MouseCode.BUTTON5.ordinal(), true);
				break;
			case MouseEvent.BUTTON6 :
				reservoir.setFlagPressing(MouseCode.BUTTON6.ordinal(), true);
				break;
			case MouseEvent.BUTTON7 :
				reservoir.setFlagPressing(MouseCode.BUTTON7.ordinal(), true);
				break;
			case MouseEvent.BUTTON8 :
				reservoir.setFlagPressing(MouseCode.BUTTON8.ordinal(), true);
				break;
			case MouseEvent.BUTTON9 :
				reservoir.setFlagPressing(MouseCode.BUTTON9.ordinal(), true);
				break;
		}
	}
	public void mouseReleased(short mouseButton) {
		switch (mouseButton) {
			case MouseEvent.BUTTON1 :
				reservoir.setFlagPressing(MouseCode.LEFT.ordinal(), false);
				break;
			case MouseEvent.BUTTON2 :
				reservoir.setFlagPressing(MouseCode.MIDDLE.ordinal(), false);
				break;
			case MouseEvent.BUTTON3 :
				reservoir.setFlagPressing(MouseCode.RIGHT.ordinal(), false);
				break;
			case MouseEvent.BUTTON4 :
				reservoir.setFlagPressing(MouseCode.BUTTON4.ordinal(), false);
				break;
			case MouseEvent.BUTTON5 :
				reservoir.setFlagPressing(MouseCode.BUTTON5.ordinal(), false);
				break;
			case MouseEvent.BUTTON6 :
				reservoir.setFlagPressing(MouseCode.BUTTON6.ordinal(), false);
				break;
			case MouseEvent.BUTTON7 :
				reservoir.setFlagPressing(MouseCode.BUTTON7.ordinal(), false);
				break;
			case MouseEvent.BUTTON8 :
				reservoir.setFlagPressing(MouseCode.BUTTON8.ordinal(), false);
				break;
			case MouseEvent.BUTTON9 :
				reservoir.setFlagPressing(MouseCode.BUTTON9.ordinal(), false);
				break;
		}
	}

	public void update() {
		reservoir.update();
	}

	public int getCountPressing(MouseCode code) {
		return reservoir.getCountPressing(code.ordinal());
	}
	public int getCountReleasing(MouseCode code) {
		return reservoir.getCountReleasing(code.ordinal());
	}

	public int getCursorPosX() {
		return currentCursorPosX;
	}
	public int getCursorPosY() {
		return currentCursorPosY;
	}
	public int getCursorDiffX() {
		return cursorDiffX;
	}
	public int getCursorDiffY() {
		return cursorDiffY;
	}
	public float getWheelRotationVertical() {
		return wheelRotations[0];
	}
	public float getWheelRotationHorizontal() {
		return wheelRotations[1];
	}
	public float getWheelRotationZ() {
		return wheelRotations[2];
	}
}
