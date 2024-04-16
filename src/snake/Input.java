package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class Input{
	public char currentKey = 'd';
	public char currentDirection = 'd';
	private static Input instance;
	public boolean isMoveAvailable = true;
	final JFrame frame = new JFrame();
	
	private Input() {}
	
	private boolean isDirectionChangeAvailable() {
		switch (currentDirection) {
		case 'w':
			if(instance.currentKey == 'a' || instance.currentKey == 'd') {
				return true;
			}
			break;
		case 'a':
			if(instance.currentKey == 'w' || instance.currentKey == 's') {
				return true;
			}
			break;
		case 's':
			if(instance.currentKey == 'a' || instance.currentKey == 'd') {
				return true;
			}
			break;
		case 'd':
			if(instance.currentKey == 'w' || instance.currentKey == 's') {
				return true;
			}
			break;
		}
		return false;
	}
	
	public void changeDirection() {
		if(isDirectionChangeAvailable()) {
			currentDirection = currentKey;
		}
	}
	
	public static Input getInstance(){
		if(instance == null) {
			instance = new Input();
		}
		return instance;
	}
	
	public void getCh() {
			frame.setUndecorated(true);
			frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
						currentKey = e.getKeyChar();
						changeDirection();
					}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyTyped(KeyEvent e) {
				}
			});
			frame.setVisible(true);
	}
	
	public void exit() {
		frame.setVisible(false);
		frame.dispose();
	}
}
