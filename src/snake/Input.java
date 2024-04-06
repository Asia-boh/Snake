package snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JRootPane;

public class Input implements Runnable {
	public char currentKey = 'd';
	public char currentDirection = 'd';
	private static Input instance;
	public Thread keyboardThread;
	
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
	
	public static void getCh() {
		final JFrame frame = new JFrame();
		synchronized (frame) {
			frame.setUndecorated(true);
			frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
			frame.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e) {
					synchronized (frame) {
						frame.setVisible(false);
						instance.currentKey = e.getKeyChar();
						instance.changeDirection();
						frame.dispose();
						frame.notify();
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
				}

				@Override
				public void keyTyped(KeyEvent e) {
				}
			});
			frame.setVisible(true);
			try {
				frame.wait();
			} catch (InterruptedException e1) {
			}
		}
	}

	public void startGameThread() {
		keyboardThread = new Thread(this);
		keyboardThread.start();

	}
	
	@Override
	public void run() {
		while (keyboardThread != null) {
			getCh();
		}
	}
}
