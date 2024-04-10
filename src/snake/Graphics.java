package snake;

import java.util.ArrayList;

public class Graphics implements Runnable{
	private ArrayList<Integer> colSnake = new ArrayList<>();
	private ArrayList<Integer> rowSnake = new ArrayList<>();
	private int colHead;
	private int rowHead;
	private int colSize = 15 + 2;
	private int rowSize = colSize*2 + 2;
	private char[][] table = new char[rowSize][colSize];
	private char hor = '_';
	private char ver = '|';
	private char apple = '\u25cc';
	private char snakeBody = '\u25a1';
	private char snakeHead = '\u25a3';
	int appleCounter = 0;
	private Thread printThread = null;
	public Input p = Input.getInstance();
	
	public Graphics() {
		setGame();
	}
	
	private void setGame() {
		allSpacesTable();
		setHorizontalLimit();
		setVerticalLimit();
		setBaseSnake();
		setBaseApple();
	}
	
	public void printGame() {
		System.out.println(apple + ": " + appleCounter);
		for(int i = 0; i < table[0].length; i++) {
			for(int j = 0; j < table.length; j++) {
				System.out.print(table[j][i]);
			}
			System.out.println();
		}
	}
	
	private void allSpacesTable() {
		for(int i = 0; i < table[0].length; i++) {
			for(int j = 0; j < table.length; j++) {
				table[j][i] = ' ';
			}
		}
	}
	
	private void setHorizontalLimit() {
		for(int i = 0; i < table.length; i++) {
			if(i%2 != 0) {
				table[i][0] = hor;
				table[i][table[0].length-1] = hor;
			}
		}
	}
	
	private void setVerticalLimit() {
		for(int i = 1; i < table[0].length - 1; i++) {
			table[0][i] = ver;
			table[table.length-1][i] = ver;
		}
	}
	
	private void setBaseSnake() {
		table[3][8] = snakeBody;
		rowSnake.add(3);
		colSnake.add(8);
		table[5][8] = snakeBody;
		rowSnake.add(5);
		colSnake.add(8);
		table[7][8] = snakeHead;
		rowSnake.add(7);
		colSnake.add(8);
		rowHead = 7;
		colHead = 8;
	}
	
	private void setBaseApple(){
		table[25][8] = apple;
	}
	
	public void generateApple() {
		int appleRow = 0;
		int appleCol = 1;
		while(table[appleRow][appleCol] != ' ') {
			appleRow = (int) (Math.random()*16)*2 + 1;
			appleCol = (int) (Math.random()*14 + 1);
		}
		table[appleRow][appleCol] = apple;
	}
	
	private boolean isMoveAvailable() {
		if(rowHead > 0) {
			if(table[rowHead][colHead] != ' ' && table[rowHead][colHead] != apple) {
				return false;
			}
		}
		else {
			rowHead = 0;
			return false;
		}
		return true;
	}
	
	private void updatePosition() {
		if(!isMoveAvailable()) {
			printThread = null;
			p.keyboardThread = null;
			System.out.print("Game over (sei scarso)");
		}
		if(table[rowHead][colHead] == apple) {
			appleCounter++;
			generateApple();
		}
		else {
			table[rowSnake.get(0)][colSnake.get(0)] = ' ';
			colSnake.remove(0);
			rowSnake.remove(0);
		}
		table[rowHead][colHead] = snakeHead;
		colSnake.add(colHead);
		rowSnake.add(rowHead);
	}
	
	public void move() {
		table[rowHead][colHead] = snakeBody;
		switch (p.currentDirection) {
		case 'w':
			colHead --;
			break;
		case 'a':
			rowHead -= 2;
			break;
		case 's':
			colHead++;
			break;
		case 'd':
			rowHead += 2;
			break;
		}
		updatePosition();
	}
	
	public void startGameThread() {
		printThread = new Thread(this);
		printThread.start();

	}
	
	@Override
	public void run() {
		while(printThread != null) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			printGame();
			move();
		}
	}
}
