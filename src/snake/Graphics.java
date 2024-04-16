package snake;

import java.util.ArrayList;

public class Graphics{
	private ArrayList<Integer> colSnake = new ArrayList<>();
	private ArrayList<Integer> rowSnake = new ArrayList<>();
	private int colHead;
	private int rowHead;
	private int colSize = 15;
	private int rowSize = colSize + 2;
	private char[][] table = new char[rowSize][colSize];
	private char apple = '\u25cc';
	private char snakeBody = '\u25a1';
	private char snakeHead = '\u25a3';
	int appleCounter = 0;
	public Input input = Input.getInstance();
	private boolean isMoveAvailable = true;
	
	public Graphics() {
		setGame();
	}
	
	private void setGame() {
		allSpacesTable();
		setBaseSnake();
		setBaseApple();
	}
	
	public void printGame() {
		System.out.println(apple + ": " + appleCounter);
		for(int i  = 0; i < 18; i++) {
			System.out.print(" _");
		}
		System.out.println();
		for(int i = 0; i < table[0].length; i++) {
			System.out.print("|");
			for(int j = 0; j < table.length; j++) {
				System.out.print(table[j][i] + " ");
			}
			System.out.print("|\n");
		}
		for(int i  = 0; i < 18; i++) {
			System.out.print(" _");
		}
		System.out.println();
	}
	
	private void allSpacesTable() {
		for(int i = 0; i < table[0].length; i++) {
			for(int j = 0; j < table.length; j++) {
				table[j][i] = ' ';
			}
		}
	}
	private void setBaseSnake() {
		table[1][8] = snakeBody;
		rowSnake.add(1);
		colSnake.add(8);
		table[2][8] = snakeBody;
		rowSnake.add(2);
		colSnake.add(8);
		table[3][8] = snakeHead;
		rowSnake.add(3);
		colSnake.add(8);
		rowHead = 3;
		colHead = 8;
	}
	
	private void setBaseApple(){
		table[12][8] = apple;
	}
	
	public void generateApple() {
		int appleRow = rowHead;
		int appleCol = colHead;
		while(table[appleRow][appleCol] != ' ') {
			appleRow = (int) (Math.random()*17);
			appleCol = (int) (Math.random()*15);
		}
		table[appleRow][appleCol] = apple;
	}
	
	
	private boolean isMoveAvailable() {
		if(rowHead < 0 || rowHead >= table.length || colHead < 0 || colHead >= table[0].length || (table[rowHead][colHead] != ' ' && table[rowHead][colHead] != apple)) {
			return false;
		}
		return true;
	}
	
	private void updatePosition() {
		if(!isMoveAvailable()) {
			rowHead = 0;
			colHead = 0;
			input.exit();
			isMoveAvailable = false;
			input.isMoveAvailable = false;
			
		}
		else {
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
	}
	
	public void move() {
		table[rowHead][colHead] = snakeBody;
		switch (input.currentDirection) {
		case 'w':
			colHead --;
			break;
		case 'a':
			rowHead --;
			break;
		case 's':
			colHead++;
			break;
		case 'd':
			rowHead ++;
			break;
		}
		updatePosition();
	}
	
	public void play() {
		printGame();
		while(isMoveAvailable) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			move();
			if(!isMoveAvailable()) {
				printGame();
			}
		}
		System.out.print("Game over (sei scarso)");
	}
}
