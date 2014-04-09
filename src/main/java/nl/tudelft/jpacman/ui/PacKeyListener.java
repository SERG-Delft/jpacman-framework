package nl.tudelft.jpacman.ui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * A key listener based on a set of keyCode-action pairs.
 * 
 * @author Jeroen Roosen 
 */
class PacKeyListener implements KeyListener {

	/**
	 * The mappings of keyCode to action.
	 */
	private final Map<Integer, Action> mappings;
	
	/**
	 * Create a new key listener based on a set of keyCode-action pairs.
	 * @param keyMappings The mappings of keyCode to action.
	 */
	PacKeyListener(Map<Integer, Action> keyMappings) {
		assert keyMappings != null;
		this.mappings = keyMappings;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		assert e != null;
		Action action = mappings.get(e.getKeyCode());
		if (action != null) {
			action.doAction();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}
}