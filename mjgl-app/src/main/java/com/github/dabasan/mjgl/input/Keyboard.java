package com.github.dabasan.mjgl.input;

import com.jogamp.newt.event.KeyEvent;

/**
 * Keyboard
 * 
 * @author Daba
 *
 */
public class Keyboard {
	private InputReservoir reservoir;

	public Keyboard() {
		int numCodes = KeyCode.values().length;
		reservoir = new InputReservoir(numCodes);
	}

	public void reset() {
		reservoir.reset();
	}

	public void keyPressed(short keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_CANCEL :
				reservoir.setFlagPressing(KeyCode.VK_CANCEL.ordinal(), true);
				break;
			case KeyEvent.VK_TAB :
				reservoir.setFlagPressing(KeyCode.VK_TAB.ordinal(), true);
				break;
			case KeyEvent.VK_CLEAR :
				reservoir.setFlagPressing(KeyCode.VK_CLEAR.ordinal(), true);
				break;
			case KeyEvent.VK_SHIFT :
				reservoir.setFlagPressing(KeyCode.VK_SHIFT.ordinal(), true);
				break;
			case KeyEvent.VK_CONTROL :
				reservoir.setFlagPressing(KeyCode.VK_CONTROL.ordinal(), true);
				break;
			case KeyEvent.VK_PAUSE :
				reservoir.setFlagPressing(KeyCode.VK_PAUSE.ordinal(), true);
				break;
			case KeyEvent.VK_FINAL :
				reservoir.setFlagPressing(KeyCode.VK_FINAL.ordinal(), true);
				break;
			case KeyEvent.VK_ESCAPE :
				reservoir.setFlagPressing(KeyCode.VK_ESCAPE.ordinal(), true);
				break;
			case KeyEvent.VK_CONVERT :
				reservoir.setFlagPressing(KeyCode.VK_CONVERT.ordinal(), true);
				break;
			case KeyEvent.VK_NONCONVERT :
				reservoir.setFlagPressing(KeyCode.VK_NONCONVERT.ordinal(), true);
				break;
			case KeyEvent.VK_ACCEPT :
				reservoir.setFlagPressing(KeyCode.VK_ACCEPT.ordinal(), true);
				break;
			case KeyEvent.VK_MODECHANGE :
				reservoir.setFlagPressing(KeyCode.VK_MODECHANGE.ordinal(), true);
				break;
			case KeyEvent.VK_SPACE :
				reservoir.setFlagPressing(KeyCode.VK_SPACE.ordinal(), true);
				break;
			case KeyEvent.VK_END :
				reservoir.setFlagPressing(KeyCode.VK_END.ordinal(), true);
				break;
			case KeyEvent.VK_HOME :
				reservoir.setFlagPressing(KeyCode.VK_HOME.ordinal(), true);
				break;
			case KeyEvent.VK_LEFT :
				reservoir.setFlagPressing(KeyCode.VK_LEFT.ordinal(), true);
				break;
			case KeyEvent.VK_UP :
				reservoir.setFlagPressing(KeyCode.VK_UP.ordinal(), true);
				break;
			case KeyEvent.VK_RIGHT :
				reservoir.setFlagPressing(KeyCode.VK_RIGHT.ordinal(), true);
				break;
			case KeyEvent.VK_DOWN :
				reservoir.setFlagPressing(KeyCode.VK_DOWN.ordinal(), true);
				break;
			case KeyEvent.VK_INSERT :
				reservoir.setFlagPressing(KeyCode.VK_INSERT.ordinal(), true);
				break;
			case KeyEvent.VK_DELETE :
				reservoir.setFlagPressing(KeyCode.VK_DELETE.ordinal(), true);
				break;
			case KeyEvent.VK_HELP :
				reservoir.setFlagPressing(KeyCode.VK_HELP.ordinal(), true);
				break;
			case KeyEvent.VK_0 :
				reservoir.setFlagPressing(KeyCode.VK_0.ordinal(), true);
				break;
			case KeyEvent.VK_1 :
				reservoir.setFlagPressing(KeyCode.VK_1.ordinal(), true);
				break;
			case KeyEvent.VK_2 :
				reservoir.setFlagPressing(KeyCode.VK_2.ordinal(), true);
				break;
			case KeyEvent.VK_3 :
				reservoir.setFlagPressing(KeyCode.VK_3.ordinal(), true);
				break;
			case KeyEvent.VK_4 :
				reservoir.setFlagPressing(KeyCode.VK_4.ordinal(), true);
				break;
			case KeyEvent.VK_5 :
				reservoir.setFlagPressing(KeyCode.VK_5.ordinal(), true);
				break;
			case KeyEvent.VK_6 :
				reservoir.setFlagPressing(KeyCode.VK_6.ordinal(), true);
				break;
			case KeyEvent.VK_7 :
				reservoir.setFlagPressing(KeyCode.VK_7.ordinal(), true);
				break;
			case KeyEvent.VK_8 :
				reservoir.setFlagPressing(KeyCode.VK_8.ordinal(), true);
				break;
			case KeyEvent.VK_9 :
				reservoir.setFlagPressing(KeyCode.VK_9.ordinal(), true);
				break;
			case KeyEvent.VK_A :
				reservoir.setFlagPressing(KeyCode.VK_A.ordinal(), true);
				break;
			case KeyEvent.VK_B :
				reservoir.setFlagPressing(KeyCode.VK_B.ordinal(), true);
				break;
			case KeyEvent.VK_C :
				reservoir.setFlagPressing(KeyCode.VK_C.ordinal(), true);
				break;
			case KeyEvent.VK_D :
				reservoir.setFlagPressing(KeyCode.VK_D.ordinal(), true);
				break;
			case KeyEvent.VK_E :
				reservoir.setFlagPressing(KeyCode.VK_E.ordinal(), true);
				break;
			case KeyEvent.VK_F :
				reservoir.setFlagPressing(KeyCode.VK_F.ordinal(), true);
				break;
			case KeyEvent.VK_G :
				reservoir.setFlagPressing(KeyCode.VK_G.ordinal(), true);
				break;
			case KeyEvent.VK_H :
				reservoir.setFlagPressing(KeyCode.VK_H.ordinal(), true);
				break;
			case KeyEvent.VK_I :
				reservoir.setFlagPressing(KeyCode.VK_I.ordinal(), true);
				break;
			case KeyEvent.VK_J :
				reservoir.setFlagPressing(KeyCode.VK_J.ordinal(), true);
				break;
			case KeyEvent.VK_K :
				reservoir.setFlagPressing(KeyCode.VK_K.ordinal(), true);
				break;
			case KeyEvent.VK_L :
				reservoir.setFlagPressing(KeyCode.VK_L.ordinal(), true);
				break;
			case KeyEvent.VK_M :
				reservoir.setFlagPressing(KeyCode.VK_M.ordinal(), true);
				break;
			case KeyEvent.VK_N :
				reservoir.setFlagPressing(KeyCode.VK_N.ordinal(), true);
				break;
			case KeyEvent.VK_O :
				reservoir.setFlagPressing(KeyCode.VK_O.ordinal(), true);
				break;
			case KeyEvent.VK_P :
				reservoir.setFlagPressing(KeyCode.VK_P.ordinal(), true);
				break;
			case KeyEvent.VK_Q :
				reservoir.setFlagPressing(KeyCode.VK_Q.ordinal(), true);
				break;
			case KeyEvent.VK_R :
				reservoir.setFlagPressing(KeyCode.VK_R.ordinal(), true);
				break;
			case KeyEvent.VK_S :
				reservoir.setFlagPressing(KeyCode.VK_S.ordinal(), true);
				break;
			case KeyEvent.VK_T :
				reservoir.setFlagPressing(KeyCode.VK_T.ordinal(), true);
				break;
			case KeyEvent.VK_U :
				reservoir.setFlagPressing(KeyCode.VK_U.ordinal(), true);
				break;
			case KeyEvent.VK_V :
				reservoir.setFlagPressing(KeyCode.VK_V.ordinal(), true);
				break;
			case KeyEvent.VK_W :
				reservoir.setFlagPressing(KeyCode.VK_W.ordinal(), true);
				break;
			case KeyEvent.VK_X :
				reservoir.setFlagPressing(KeyCode.VK_X.ordinal(), true);
				break;
			case KeyEvent.VK_Y :
				reservoir.setFlagPressing(KeyCode.VK_Y.ordinal(), true);
				break;
			case KeyEvent.VK_Z :
				reservoir.setFlagPressing(KeyCode.VK_Z.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD0 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD0.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD1 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD1.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD2 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD2.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD3 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD3.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD4 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD4.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD5 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD5.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD6 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD6.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD7 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD7.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD8 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD8.ordinal(), true);
				break;
			case KeyEvent.VK_NUMPAD9 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD9.ordinal(), true);
				break;
			case KeyEvent.VK_MULTIPLY :
				reservoir.setFlagPressing(KeyCode.VK_MULTIPLY.ordinal(), true);
				break;
			case KeyEvent.VK_ADD :
				reservoir.setFlagPressing(KeyCode.VK_ADD.ordinal(), true);
				break;
			case KeyEvent.VK_SEPARATOR :
				reservoir.setFlagPressing(KeyCode.VK_SEPARATOR.ordinal(), true);
				break;
			case KeyEvent.VK_SUBTRACT :
				reservoir.setFlagPressing(KeyCode.VK_SUBTRACT.ordinal(), true);
				break;
			case KeyEvent.VK_DECIMAL :
				reservoir.setFlagPressing(KeyCode.VK_DECIMAL.ordinal(), true);
				break;
			case KeyEvent.VK_DIVIDE :
				reservoir.setFlagPressing(KeyCode.VK_DIVIDE.ordinal(), true);
				break;
			case KeyEvent.VK_F1 :
				reservoir.setFlagPressing(KeyCode.VK_F1.ordinal(), true);
				break;
			case KeyEvent.VK_F2 :
				reservoir.setFlagPressing(KeyCode.VK_F2.ordinal(), true);
				break;
			case KeyEvent.VK_F3 :
				reservoir.setFlagPressing(KeyCode.VK_F3.ordinal(), true);
				break;
			case KeyEvent.VK_F4 :
				reservoir.setFlagPressing(KeyCode.VK_F4.ordinal(), true);
				break;
			case KeyEvent.VK_F5 :
				reservoir.setFlagPressing(KeyCode.VK_F5.ordinal(), true);
				break;
			case KeyEvent.VK_F6 :
				reservoir.setFlagPressing(KeyCode.VK_F6.ordinal(), true);
				break;
			case KeyEvent.VK_F7 :
				reservoir.setFlagPressing(KeyCode.VK_F7.ordinal(), true);
				break;
			case KeyEvent.VK_F8 :
				reservoir.setFlagPressing(KeyCode.VK_F8.ordinal(), true);
				break;
			case KeyEvent.VK_F9 :
				reservoir.setFlagPressing(KeyCode.VK_F9.ordinal(), true);
				break;
			case KeyEvent.VK_F10 :
				reservoir.setFlagPressing(KeyCode.VK_F10.ordinal(), true);
				break;
			case KeyEvent.VK_F11 :
				reservoir.setFlagPressing(KeyCode.VK_F11.ordinal(), true);
				break;
			case KeyEvent.VK_F12 :
				reservoir.setFlagPressing(KeyCode.VK_F12.ordinal(), true);
				break;
			case KeyEvent.VK_F13 :
				reservoir.setFlagPressing(KeyCode.VK_F13.ordinal(), true);
				break;
			case KeyEvent.VK_F14 :
				reservoir.setFlagPressing(KeyCode.VK_F14.ordinal(), true);
				break;
			case KeyEvent.VK_F15 :
				reservoir.setFlagPressing(KeyCode.VK_F15.ordinal(), true);
				break;
			case KeyEvent.VK_F16 :
				reservoir.setFlagPressing(KeyCode.VK_F16.ordinal(), true);
				break;
			case KeyEvent.VK_F17 :
				reservoir.setFlagPressing(KeyCode.VK_F17.ordinal(), true);
				break;
			case KeyEvent.VK_F18 :
				reservoir.setFlagPressing(KeyCode.VK_F18.ordinal(), true);
				break;
			case KeyEvent.VK_F19 :
				reservoir.setFlagPressing(KeyCode.VK_F19.ordinal(), true);
				break;
			case KeyEvent.VK_F20 :
				reservoir.setFlagPressing(KeyCode.VK_F20.ordinal(), true);
				break;
			case KeyEvent.VK_F21 :
				reservoir.setFlagPressing(KeyCode.VK_F21.ordinal(), true);
				break;
			case KeyEvent.VK_F22 :
				reservoir.setFlagPressing(KeyCode.VK_F22.ordinal(), true);
				break;
			case KeyEvent.VK_F23 :
				reservoir.setFlagPressing(KeyCode.VK_F23.ordinal(), true);
				break;
			case KeyEvent.VK_F24 :
				reservoir.setFlagPressing(KeyCode.VK_F24.ordinal(), true);
				break;
			case KeyEvent.VK_NUM_LOCK :
				reservoir.setFlagPressing(KeyCode.VK_NUMLOCK.ordinal(), true);
				break;
		}
	}
	public void keyReleased(short keyCode) {
		switch (keyCode) {
			case KeyEvent.VK_CANCEL :
				reservoir.setFlagPressing(KeyCode.VK_CANCEL.ordinal(), false);
				break;
			case KeyEvent.VK_TAB :
				reservoir.setFlagPressing(KeyCode.VK_TAB.ordinal(), false);
				break;
			case KeyEvent.VK_CLEAR :
				reservoir.setFlagPressing(KeyCode.VK_CLEAR.ordinal(), false);
				break;
			case KeyEvent.VK_SHIFT :
				reservoir.setFlagPressing(KeyCode.VK_SHIFT.ordinal(), false);
				break;
			case KeyEvent.VK_CONTROL :
				reservoir.setFlagPressing(KeyCode.VK_CONTROL.ordinal(), false);
				break;
			case KeyEvent.VK_PAUSE :
				reservoir.setFlagPressing(KeyCode.VK_PAUSE.ordinal(), false);
				break;
			case KeyEvent.VK_FINAL :
				reservoir.setFlagPressing(KeyCode.VK_FINAL.ordinal(), false);
				break;
			case KeyEvent.VK_ESCAPE :
				reservoir.setFlagPressing(KeyCode.VK_ESCAPE.ordinal(), false);
				break;
			case KeyEvent.VK_CONVERT :
				reservoir.setFlagPressing(KeyCode.VK_CONVERT.ordinal(), false);
				break;
			case KeyEvent.VK_NONCONVERT :
				reservoir.setFlagPressing(KeyCode.VK_NONCONVERT.ordinal(), false);
				break;
			case KeyEvent.VK_ACCEPT :
				reservoir.setFlagPressing(KeyCode.VK_ACCEPT.ordinal(), false);
				break;
			case KeyEvent.VK_MODECHANGE :
				reservoir.setFlagPressing(KeyCode.VK_MODECHANGE.ordinal(), false);
				break;
			case KeyEvent.VK_SPACE :
				reservoir.setFlagPressing(KeyCode.VK_SPACE.ordinal(), false);
				break;
			case KeyEvent.VK_END :
				reservoir.setFlagPressing(KeyCode.VK_END.ordinal(), false);
				break;
			case KeyEvent.VK_HOME :
				reservoir.setFlagPressing(KeyCode.VK_HOME.ordinal(), false);
				break;
			case KeyEvent.VK_LEFT :
				reservoir.setFlagPressing(KeyCode.VK_LEFT.ordinal(), false);
				break;
			case KeyEvent.VK_UP :
				reservoir.setFlagPressing(KeyCode.VK_UP.ordinal(), false);
				break;
			case KeyEvent.VK_RIGHT :
				reservoir.setFlagPressing(KeyCode.VK_RIGHT.ordinal(), false);
				break;
			case KeyEvent.VK_DOWN :
				reservoir.setFlagPressing(KeyCode.VK_DOWN.ordinal(), false);
				break;
			case KeyEvent.VK_INSERT :
				reservoir.setFlagPressing(KeyCode.VK_INSERT.ordinal(), false);
				break;
			case KeyEvent.VK_DELETE :
				reservoir.setFlagPressing(KeyCode.VK_DELETE.ordinal(), false);
				break;
			case KeyEvent.VK_HELP :
				reservoir.setFlagPressing(KeyCode.VK_HELP.ordinal(), false);
				break;
			case KeyEvent.VK_0 :
				reservoir.setFlagPressing(KeyCode.VK_0.ordinal(), false);
				break;
			case KeyEvent.VK_1 :
				reservoir.setFlagPressing(KeyCode.VK_1.ordinal(), false);
				break;
			case KeyEvent.VK_2 :
				reservoir.setFlagPressing(KeyCode.VK_2.ordinal(), false);
				break;
			case KeyEvent.VK_3 :
				reservoir.setFlagPressing(KeyCode.VK_3.ordinal(), false);
				break;
			case KeyEvent.VK_4 :
				reservoir.setFlagPressing(KeyCode.VK_4.ordinal(), false);
				break;
			case KeyEvent.VK_5 :
				reservoir.setFlagPressing(KeyCode.VK_5.ordinal(), false);
				break;
			case KeyEvent.VK_6 :
				reservoir.setFlagPressing(KeyCode.VK_6.ordinal(), false);
				break;
			case KeyEvent.VK_7 :
				reservoir.setFlagPressing(KeyCode.VK_7.ordinal(), false);
				break;
			case KeyEvent.VK_8 :
				reservoir.setFlagPressing(KeyCode.VK_8.ordinal(), false);
				break;
			case KeyEvent.VK_9 :
				reservoir.setFlagPressing(KeyCode.VK_9.ordinal(), false);
				break;
			case KeyEvent.VK_A :
				reservoir.setFlagPressing(KeyCode.VK_A.ordinal(), false);
				break;
			case KeyEvent.VK_B :
				reservoir.setFlagPressing(KeyCode.VK_B.ordinal(), false);
				break;
			case KeyEvent.VK_C :
				reservoir.setFlagPressing(KeyCode.VK_C.ordinal(), false);
				break;
			case KeyEvent.VK_D :
				reservoir.setFlagPressing(KeyCode.VK_D.ordinal(), false);
				break;
			case KeyEvent.VK_E :
				reservoir.setFlagPressing(KeyCode.VK_E.ordinal(), false);
				break;
			case KeyEvent.VK_F :
				reservoir.setFlagPressing(KeyCode.VK_F.ordinal(), false);
				break;
			case KeyEvent.VK_G :
				reservoir.setFlagPressing(KeyCode.VK_G.ordinal(), false);
				break;
			case KeyEvent.VK_H :
				reservoir.setFlagPressing(KeyCode.VK_H.ordinal(), false);
				break;
			case KeyEvent.VK_I :
				reservoir.setFlagPressing(KeyCode.VK_I.ordinal(), false);
				break;
			case KeyEvent.VK_J :
				reservoir.setFlagPressing(KeyCode.VK_J.ordinal(), false);
				break;
			case KeyEvent.VK_K :
				reservoir.setFlagPressing(KeyCode.VK_K.ordinal(), false);
				break;
			case KeyEvent.VK_L :
				reservoir.setFlagPressing(KeyCode.VK_L.ordinal(), false);
				break;
			case KeyEvent.VK_M :
				reservoir.setFlagPressing(KeyCode.VK_M.ordinal(), false);
				break;
			case KeyEvent.VK_N :
				reservoir.setFlagPressing(KeyCode.VK_N.ordinal(), false);
				break;
			case KeyEvent.VK_O :
				reservoir.setFlagPressing(KeyCode.VK_O.ordinal(), false);
				break;
			case KeyEvent.VK_P :
				reservoir.setFlagPressing(KeyCode.VK_P.ordinal(), false);
				break;
			case KeyEvent.VK_Q :
				reservoir.setFlagPressing(KeyCode.VK_Q.ordinal(), false);
				break;
			case KeyEvent.VK_R :
				reservoir.setFlagPressing(KeyCode.VK_R.ordinal(), false);
				break;
			case KeyEvent.VK_S :
				reservoir.setFlagPressing(KeyCode.VK_S.ordinal(), false);
				break;
			case KeyEvent.VK_T :
				reservoir.setFlagPressing(KeyCode.VK_T.ordinal(), false);
				break;
			case KeyEvent.VK_U :
				reservoir.setFlagPressing(KeyCode.VK_U.ordinal(), false);
				break;
			case KeyEvent.VK_V :
				reservoir.setFlagPressing(KeyCode.VK_V.ordinal(), false);
				break;
			case KeyEvent.VK_W :
				reservoir.setFlagPressing(KeyCode.VK_W.ordinal(), false);
				break;
			case KeyEvent.VK_X :
				reservoir.setFlagPressing(KeyCode.VK_X.ordinal(), false);
				break;
			case KeyEvent.VK_Y :
				reservoir.setFlagPressing(KeyCode.VK_Y.ordinal(), false);
				break;
			case KeyEvent.VK_Z :
				reservoir.setFlagPressing(KeyCode.VK_Z.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD0 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD0.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD1 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD1.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD2 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD2.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD3 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD3.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD4 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD4.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD5 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD5.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD6 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD6.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD7 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD7.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD8 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD8.ordinal(), false);
				break;
			case KeyEvent.VK_NUMPAD9 :
				reservoir.setFlagPressing(KeyCode.VK_NUMPAD9.ordinal(), false);
				break;
			case KeyEvent.VK_MULTIPLY :
				reservoir.setFlagPressing(KeyCode.VK_MULTIPLY.ordinal(), false);
				break;
			case KeyEvent.VK_ADD :
				reservoir.setFlagPressing(KeyCode.VK_ADD.ordinal(), false);
				break;
			case KeyEvent.VK_SEPARATOR :
				reservoir.setFlagPressing(KeyCode.VK_SEPARATOR.ordinal(), false);
				break;
			case KeyEvent.VK_SUBTRACT :
				reservoir.setFlagPressing(KeyCode.VK_SUBTRACT.ordinal(), false);
				break;
			case KeyEvent.VK_DECIMAL :
				reservoir.setFlagPressing(KeyCode.VK_DECIMAL.ordinal(), false);
				break;
			case KeyEvent.VK_DIVIDE :
				reservoir.setFlagPressing(KeyCode.VK_DIVIDE.ordinal(), false);
				break;
			case KeyEvent.VK_F1 :
				reservoir.setFlagPressing(KeyCode.VK_F1.ordinal(), false);
				break;
			case KeyEvent.VK_F2 :
				reservoir.setFlagPressing(KeyCode.VK_F2.ordinal(), false);
				break;
			case KeyEvent.VK_F3 :
				reservoir.setFlagPressing(KeyCode.VK_F3.ordinal(), false);
				break;
			case KeyEvent.VK_F4 :
				reservoir.setFlagPressing(KeyCode.VK_F4.ordinal(), false);
				break;
			case KeyEvent.VK_F5 :
				reservoir.setFlagPressing(KeyCode.VK_F5.ordinal(), false);
				break;
			case KeyEvent.VK_F6 :
				reservoir.setFlagPressing(KeyCode.VK_F6.ordinal(), false);
				break;
			case KeyEvent.VK_F7 :
				reservoir.setFlagPressing(KeyCode.VK_F7.ordinal(), false);
				break;
			case KeyEvent.VK_F8 :
				reservoir.setFlagPressing(KeyCode.VK_F8.ordinal(), false);
				break;
			case KeyEvent.VK_F9 :
				reservoir.setFlagPressing(KeyCode.VK_F9.ordinal(), false);
				break;
			case KeyEvent.VK_F10 :
				reservoir.setFlagPressing(KeyCode.VK_F10.ordinal(), false);
				break;
			case KeyEvent.VK_F11 :
				reservoir.setFlagPressing(KeyCode.VK_F11.ordinal(), false);
				break;
			case KeyEvent.VK_F12 :
				reservoir.setFlagPressing(KeyCode.VK_F12.ordinal(), false);
				break;
			case KeyEvent.VK_F13 :
				reservoir.setFlagPressing(KeyCode.VK_F13.ordinal(), false);
				break;
			case KeyEvent.VK_F14 :
				reservoir.setFlagPressing(KeyCode.VK_F14.ordinal(), false);
				break;
			case KeyEvent.VK_F15 :
				reservoir.setFlagPressing(KeyCode.VK_F15.ordinal(), false);
				break;
			case KeyEvent.VK_F16 :
				reservoir.setFlagPressing(KeyCode.VK_F16.ordinal(), false);
				break;
			case KeyEvent.VK_F17 :
				reservoir.setFlagPressing(KeyCode.VK_F17.ordinal(), false);
				break;
			case KeyEvent.VK_F18 :
				reservoir.setFlagPressing(KeyCode.VK_F18.ordinal(), false);
				break;
			case KeyEvent.VK_F19 :
				reservoir.setFlagPressing(KeyCode.VK_F19.ordinal(), false);
				break;
			case KeyEvent.VK_F20 :
				reservoir.setFlagPressing(KeyCode.VK_F20.ordinal(), false);
				break;
			case KeyEvent.VK_F21 :
				reservoir.setFlagPressing(KeyCode.VK_F21.ordinal(), false);
				break;
			case KeyEvent.VK_F22 :
				reservoir.setFlagPressing(KeyCode.VK_F22.ordinal(), false);
				break;
			case KeyEvent.VK_F23 :
				reservoir.setFlagPressing(KeyCode.VK_F23.ordinal(), false);
				break;
			case KeyEvent.VK_F24 :
				reservoir.setFlagPressing(KeyCode.VK_F24.ordinal(), false);
				break;
			case KeyEvent.VK_NUM_LOCK :
				reservoir.setFlagPressing(KeyCode.VK_NUMLOCK.ordinal(), false);
				break;
		}
	}

	public void update() {
		reservoir.update();
	}

	public int getCountPressing(KeyCode code) {
		return reservoir.getCountPressing(code.ordinal());
	}
	public int getCountReleasing(KeyCode code) {
		return reservoir.getCountReleasing(code.ordinal());
	}
}
