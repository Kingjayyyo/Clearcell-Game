package model;

import java.util.Random;

/**
 * This class extends GameModel and implements the logic of the clear cell game.
 * We define an empty cell as BoardCell.EMPTY. An empty row is defined as one
 * where every cell corresponds to BoardCell.EMPTY.
 * 
 * @author Department of Computer Science, UMCP
 */

public class ClearCellGame extends Game {
	private Random random;
	private int strategy, score;

	/**
	 * Defines a board with empty cells. It relies on the super class constructor to
	 * define the board. The random parameter is used for the generation of random
	 * cells. The strategy parameter defines which clearing cell strategy to use
	 * (for this project it will be 1). For fun, you can add your own strategy by
	 * using a value different that one.
	 * 
	 * @param maxRows
	 * @param maxCols
	 * @param random
	 * @param strategy
	 */
	public ClearCellGame(int maxRows, int maxCols, Random random, int strategy) {
		super(maxRows, maxCols);
		this.random = random;
		this.strategy = strategy;
		this.score = 0;
	}

	/**
	 * The game is over when the last board row (row with index board.length -1) is
	 * different from empty row.
	 */
	public boolean isGameOver() {
		return !isTheRowEmpty((board.length - 1));
	}

	public int getScore() {
		return this.score;
	}

	/**
	 * This method will attempt to insert a row of random BoardCell objects if the
	 * last board row (row with index board.length -1) corresponds to the empty row;
	 * otherwise no operation will take place.
	 */
	public void nextAnimationStep() {
		if (this.isGameOver() == false) {
			for (int row = this.getMaxRows() - 2; row >= 0; row--) {
				for (int col = 0; col < this.getMaxCols(); col++) {
					if (this.getBoardCell(row, col) != BoardCell.EMPTY)
						this.setBoardCell(row + 1, col, this.getBoardCell(row, col));
					if (row == 0)
						this.setBoardCell(row, col, BoardCell.getNonEmptyRandomBoardCell(this.random));
				}
			}
		}
	}

	/**
	 * Helper Method
	 * 
	 * @param row
	 * @return
	 */
	private boolean isTheRowEmpty(int row) {
		boolean rowEmpty = false;
		int emptyColumns = 0;
		for (int i = 0; i < this.getMaxCols(); i++) {
			if (this.getBoardCell(row, i) == BoardCell.EMPTY)
				emptyColumns++;
		}
		if (emptyColumns == this.getMaxCols())
			rowEmpty = true;

		return rowEmpty;
	}

	/**
	 * This method will turn to BoardCell.EMPTY the cell selected and any adjacent
	 * surrounding cells in the vertical, horizontal, and diagonal directions that
	 * have the same color. The clearing of adjacent cells will continue as long as
	 * cells have a color that corresponds to the selected cell. Notice that the
	 * clearing process does not clear every single cell that surrounds a cell
	 * selected (only those found in the vertical, horizontal or diagonal
	 * directions).
	 * 
	 * IMPORTANT: Clearing a cell adds one point to the game's score.<br />
	 * <br />
	 * 
	 * If after processing cells, any rows in the board are empty,those rows will
	 * collapse, moving non-empty rows upward. For example, if we have the following
	 * board (an * represents an empty cell):<br />
	 * <br />
	 * RRR<br />
	 * GGG<br />
	 * YYY<br />
	 * * * *<br/>
	 * <br />
	 * then processing each cell of the second row will generate the following
	 * board<br />
	 * <br />
	 * RRR<br />
	 * YYY<br />
	 * * * *<br/>
	 * * * *<br/>
	 * <br />
	 * IMPORTANT: If the game has ended no action will take place.
	 * 
	 * 
	 */
	public void processCell(int rowIndex, int colIndex) {

		if (this.board[rowIndex][colIndex] != BoardCell.EMPTY || this.isGameOver() == false
				|| rowIndex >= 0 && rowIndex < this.getMaxRows() && colIndex >= 0 && colIndex < this.getMaxCols()) {
			if (rowIndex - 1 >= 0) {
				if (colIndex - 1 >= 0) {
					for (int i = rowIndex; i > 0; i--) {
						for (int j = colIndex; j > 0; j--) {
							if (this.board[i - 1][j - 1] == this.board[rowIndex][colIndex]) {
								this.board[i - 1][j - 1] = BoardCell.EMPTY;
								this.score++;
							}
							if (this.board[i - 1][j - 1] != this.board[rowIndex][colIndex]
									&& this.board[i - 1][j - 1] != BoardCell.EMPTY)
								break;
						}
					}
				}
				if (colIndex + 1 < this.getMaxCols()) {
					for (int i = rowIndex; i > 0; i--) {
						for (int j = colIndex; j < this.getMaxCols(); j++) {
							if (this.board[i - 1][j + 1] == this.board[rowIndex][colIndex]) {
								this.board[i - 1][j + 1] = BoardCell.EMPTY;
								this.score++;
							}
							if (this.board[i - 1][j + 1] != this.board[rowIndex][colIndex]
									|| this.board[i - 1][j + 1] != BoardCell.EMPTY)
								break;
						}
					}
					// stopped here
				}
				for (int i = rowIndex; i > 0; i--) {
					for (int j = colIndex; j < this.getMaxCols(); j++) {
						if (this.board[i - 1][colIndex] == this.board[rowIndex][colIndex]) {
							this.board[i - 1][colIndex] = BoardCell.EMPTY;
							this.score++;
						}
						if (this.board[i - 1][j] != this.board[rowIndex][colIndex]
								|| this.board[i - 1][j - 1] != BoardCell.EMPTY)
							break;
					}
				}
				if (this.board[rowIndex - 1][colIndex] == this.board[rowIndex][colIndex]) {
					this.board[rowIndex - 1][colIndex] = BoardCell.EMPTY;
					this.score++;
				}
			}

			if (rowIndex + 1 < this.getMaxRows()) {
				if (colIndex - 1 >= 0) {

					if (this.board[rowIndex + 1][colIndex - 1] == this.board[rowIndex][colIndex]) {
						this.board[rowIndex + 1][colIndex - 1] = BoardCell.EMPTY;
						this.score++;
					}
				}
				if (colIndex + 1 < this.getMaxCols()) {
					if (this.board[rowIndex + 1][colIndex + 1] == this.board[rowIndex][colIndex]) {
						this.board[rowIndex + 1][colIndex + 1] = BoardCell.EMPTY;
						this.score++;
					}
				}
				if (this.board[rowIndex + 1][colIndex] == this.board[rowIndex][colIndex]) {
					this.board[rowIndex + 1][colIndex] = BoardCell.EMPTY;
					this.score++;
				}
			}

			if (colIndex - 1 >= 0) {
				for (int j = colIndex; j > 0; j--) {
					if (this.board[rowIndex][j - 1] == this.board[rowIndex][colIndex]) {
						this.board[rowIndex][j - 1] = BoardCell.EMPTY;
						this.score++;
					}
					if (this.board[rowIndex][j - 1] != this.board[rowIndex][colIndex]
							&& this.board[rowIndex][j - 1] != BoardCell.EMPTY)
						break;
				}
			}

			if (colIndex + 1 < this.getMaxCols()) {
				for (int j = colIndex; j < this.getMaxCols(); j++) {
					if (this.board[rowIndex][j + 1] == this.board[rowIndex][colIndex]) {
						this.board[rowIndex][j + 1] = BoardCell.EMPTY;
						this.score++;
					}
					if (this.board[rowIndex][j + 1] != this.board[rowIndex][colIndex]
							&& this.board[rowIndex][j + 1] != BoardCell.EMPTY)
						break;
				}
			}

			this.board[rowIndex][colIndex] = BoardCell.EMPTY;
			this.score++;

			BoardCell[][] copy = new BoardCell[this.board.length][this.board[0].length];

			for (int i = 0; i < this.board.length; i++) {
				for (int j = 0; j < this.board[0].length; j++) {
					copy[i][j] = this.board[i][j];
				}
			}

			int counter = 0, z = 0;
			for (int i = 0; i < this.board.length; i++) {
				z = i;
				if (isTheRowEmpty(i)) {
					counter++;
					if (i == this.board.length - 1)
						break;
					break;
				}

			}
			for (int i = 0; i < this.getMaxRows() - z - 1; i++) {
				for (int j = 0; j < this.getMaxCols(); j++) {
					this.board[i + z][j] = copy[i + z + 1][j];
				}
			}
		}

	}

}