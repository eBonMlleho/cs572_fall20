/**
 * @author Zhanghao Wen
 * @Date 10/16/2020
 */
package edu.iastate.cs472.proj1;

import java.util.ArrayList;

/**
 * An object of this class holds data about a game of checkers. It knows what
 * kind of piece is on each square of the checkerboard. Note that RED moves "up"
 * the board (i.e. row number decreases) while BLACK moves "down" the board
 * (i.e. row number increases). Methods are provided to return lists of
 * available legal moves.
 */
public class CheckersData {

	/*
	 * The following constants represent the possible contents of a square on the
	 * board. The constants RED and BLACK also represent players in the game.
	 */

	static final int EMPTY = 0, RED = 1, RED_KING = 2, BLACK = 3, BLACK_KING = 4;

	int[][] board; // board[r][c] is the contents of row r, column c.

	/**
	 * Constructor. Create the board and set it up for a new game.
	 */
	CheckersData() {
		board = new int[8][8];
		setUpGame();
	}

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < board.length; i++) {
			int[] row = board[i];
			sb.append(8 - i).append(" ");
			for (int n : row) {
				if (n == 0) {
					sb.append(" ");
				} else if (n == 1) {
					sb.append(ANSI_RED + "R" + ANSI_RESET);
				} else if (n == 2) {
					sb.append(ANSI_RED + "K" + ANSI_RESET);
				} else if (n == 3) {
					sb.append(ANSI_YELLOW + "B" + ANSI_RESET);
				} else if (n == 4) {
					sb.append(ANSI_YELLOW + "K" + ANSI_RESET);
				}
				sb.append(" ");
			}
			sb.append(System.lineSeparator());
		}
		sb.append("  a b c d e f g h");

		return sb.toString();
	}

	/**
	 * Set up the board with checkers in position for the beginning of a game. Note
	 * that checkers can only be found in squares that satisfy row % 2 == col % 2.
	 * At the start of the game, all such squares in the first three rows contain
	 * black squares and all such squares in the last three rows contain red
	 * squares.
	 */
	void setUpGame() {
		// Todo: setup the board with pieces BLACK, RED, and EMPTY

		// set up all squares empty in the beginning
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = EMPTY;
			}
		}

		// set up red checkers
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + 1) % 2 == j % 2) {
					board[i][j] = RED;
				}

			}
		}
		// set up black checkers
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + 1) % 2 == j % 2) {
					board[i][j] = BLACK;
				}

			}
		}
	}

	/**
	 * Return the contents of the square in the specified row and column.
	 */
	int pieceAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Make the specified move. It is assumed that move is non-null and that the
	 * move it represents is legal.
	 * 
	 * @return true if the piece becomes a king, otherwise false
	 */
	boolean makeMove(CheckersMove move) {

		return makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);
	}

	/**
	 * Make the move from (fromRow,fromCol) to (toRow,toCol). It is assumed that
	 * this move is legal. If the move is a jump, the jumped piece is removed from
	 * the board. If a piece moves to the last row on the opponent's side of the
	 * board, the piece becomes a king.
	 *
	 * @param fromRow row index of the from square
	 * @param fromCol column index of the from square
	 * @param toRow   row index of the to square
	 * @param toCol   column index of the to square
	 * @return true if the piece becomes a king, otherwise false
	 */
	boolean makeMove(int fromRow, int fromCol, int toRow, int toCol) {
		// Todo: update the board for the given move.
		// You need to take care of the following situations:

		// 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
		int checkColor = pieceAt(fromRow, fromCol);
		board[toRow][toCol] = checkColor;
		board[fromRow][fromCol] = EMPTY;

		// 2. if this move is a jump, remove the captured piece
		// now only considered one jump, consecutive jump is not considered yet, and it
		// totally depends on player

		if (Math.abs(fromRow - toRow) > 1) { // everytime move happened, row must be diff, everytime jump happened, row
												// diff is at least 2
			int killedCheckerRow = (fromRow + toRow) / 2;
			int killedCheckerCol = (fromCol + toCol) / 2;
			board[killedCheckerRow][killedCheckerCol] = EMPTY;
		}

		// 3. if the piece moves into the kings row on the opponent's side of the board,
		// crowned it as a king
		if (checkColor == BLACK && toRow == 7) {
			board[toRow][toCol] = BLACK_KING;
			return true;

		} else if (checkColor == RED && toRow == 0) {
			board[toRow][toCol] = RED_KING;
			return true;
		} else {
			return false;
		}

	}

	CheckersData getResultFromMove(CheckersMove move) {
		// Todo: update the board for the given move.
		// You need to take care of the following situations:
		int fromRow = move.fromRow;
		int fromCol = move.fromCol;
		int toRow = move.toRow;
		int toCol = move.toCol;

		CheckersData returnedCheckersData = new CheckersData();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				returnedCheckersData.board[x][y] = board[x][y];
			}
		}

		// 1. move the piece from (fromRow,fromCol) to (toRow,toCol)
		int checkColor = pieceAt(fromRow, fromCol);
		returnedCheckersData.board[toRow][toCol] = checkColor;
		returnedCheckersData.board[fromRow][fromCol] = EMPTY;

		// 2. if this move is a jump, remove the captured piece
		// now only considered one jump, consecutive jump is not considered yet, and it
		// totally depends on player

		if (Math.abs(fromRow - toRow) > 1) { // everytime move happened, row must be diff, everytime jump happened, row
												// diff is at least 2
			int killedCheckerRow = (fromRow + toRow) / 2;
			int killedCheckerCol = (fromCol + toCol) / 2;
			returnedCheckersData.board[killedCheckerRow][killedCheckerCol] = EMPTY;
		}

		if (checkColor == BLACK && toRow == 7) {
			returnedCheckersData.board[toRow][toCol] = BLACK_KING;

		} else if (checkColor == RED && toRow == 0) {
			returnedCheckersData.board[toRow][toCol] = RED_KING;
		}

		return returnedCheckersData;

	}

	/**
	 * Return an array containing all the legal CheckersMoves for the specified
	 * player on the current board. If the player has no legal moves, null is
	 * returned. The value of player should be one of the constants RED or BLACK; if
	 * not, null is returned. If the returned value is non-null, it consists
	 * entirely of jump moves or entirely of regular moves, since if the player can
	 * jump, only jumps are legal moves.
	 *
	 * @param player color of the player, RED or BLACK
	 */
	CheckersMove[] getLegalMoves(int player) {
		// Todo: Implement your getLegalMoves here.
		ArrayList<CheckersMove> allCheckersAllMoves = new ArrayList<CheckersMove>();
		boolean hadjump = false;
		if (player == BLACK) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) { // iterate through all squares in boards to find black/_king
					if (pieceAt(x, y) == BLACK || pieceAt(x, y) == BLACK_KING) {

						if (getLegalJumpsFrom(BLACK, x, y) != null) { // jump exists
							for (CheckersMove move : getLegalJumpsFrom(BLACK, x, y)) { // add all jump moves from (x,y)
																						// to list
								allCheckersAllMoves.add(move);
								hadjump = true;
							}
						}
					}

				}
			}
			if (!hadjump) { // no jump exists. find regular moves
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						if (pieceAt(x, y) == BLACK_KING) {
							if (moveCanland(x + 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y + 1));
							}
							if (moveCanland(x + 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y - 1));
							}
							if (moveCanland(x - 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y + 1));
							}
							if (moveCanland(x - 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y - 1));
							}
						} else if (pieceAt(x, y) == BLACK) {
							if (moveCanland(x + 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y + 1));
							}
							if (moveCanland(x + 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y - 1));
							}
						}
					}
				}
			}

		} else if (player == RED) {
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) { // iterate through all squares in boards to find black/_king
					if (pieceAt(x, y) == RED || pieceAt(x, y) == RED_KING) {
						if (getLegalJumpsFrom(RED, x, y) != null) { // jump exists
							for (CheckersMove move : getLegalJumpsFrom(RED, x, y)) { // add all jump moves from (x,y)
																						// to list
								allCheckersAllMoves.add(move);
								hadjump = true;
							}
						}
					}

				}
			}
			if (!hadjump) { // no jump exists. find regular moves
				for (int x = 0; x < 8; x++) {
					for (int y = 0; y < 8; y++) {
						if (pieceAt(x, y) == RED_KING) {
							if (moveCanland(x + 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y + 1));
							}
							if (moveCanland(x + 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x + 1, y - 1));
							}
							if (moveCanland(x - 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y + 1));
							}
							if (moveCanland(x - 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y - 1));
							}
						} else if (pieceAt(x, y) == RED) {
							if (moveCanland(x - 1, y + 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y + 1));
							}
							if (moveCanland(x - 1, y - 1)) {
								allCheckersAllMoves.add(new CheckersMove(x, y, x - 1, y - 1));
							}
						}

					}
				}

			}
		}

		if (allCheckersAllMoves.size() == 0) {
			return null;
		} else {
			CheckersMove[] resultCheckersMoves = new CheckersMove[allCheckersAllMoves.size()];
			for (int i = 0; i < allCheckersAllMoves.size(); i++) {
				resultCheckersMoves[i] = allCheckersAllMoves.get(i);
			}
			return resultCheckersMoves;
		}
	}

	/**
	 * Return a list of the legal jumps that the specified player can make starting
	 * from the specified row and column. If no such jumps are possible, null is
	 * returned. The logic is similar to the logic of the getLegalMoves() method.
	 *
	 * @param player The player of the current jump, either RED or BLACK.
	 * @param row    row index of the start square.
	 * @param col    col index of the start square.
	 */
	CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
		// Todo: Implement your getLegalJumpsFrom here.
		ArrayList<CheckersMove> singleCheckerJumpMoves = new ArrayList<CheckersMove>();

		if (player == BLACK) {
			if (pieceAt(row, col) == BLACK_KING) { // 4 options
				if (moveCanland(row + 2, col + 2) && jumpOverColor(BLACK, row + 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col + 2));
				}
				if (moveCanland(row + 2, col - 2) && jumpOverColor(BLACK, row + 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col - 2));
				}
				if (moveCanland(row - 2, col + 2) && jumpOverColor(BLACK, row - 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col + 2));
				}
				if (moveCanland(row - 2, col - 2) && jumpOverColor(BLACK, row - 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col - 2));
				}
			} else if (pieceAt(row, col) == BLACK) { // 2 options only can move downward
				if (moveCanland(row + 2, col + 2) && jumpOverColor(BLACK, row + 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col + 2));
				}
				if (moveCanland(row + 2, col - 2) && jumpOverColor(BLACK, row + 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col - 2));
				}
			}

		} else if (player == RED) {
			if (pieceAt(row, col) == RED_KING) { // 4 options
				if (moveCanland(row + 2, col + 2) && jumpOverColor(RED, row + 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col + 2));
				}
				if (moveCanland(row + 2, col - 2) && jumpOverColor(RED, row + 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row + 2, col - 2));
				}
				if (moveCanland(row - 2, col + 2) && jumpOverColor(RED, row - 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col + 2));
				}
				if (moveCanland(row - 2, col - 2) && jumpOverColor(RED, row - 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col - 2));
				}
			} else if (pieceAt(row, col) == RED) { // 2 options only can move upward
				if (moveCanland(row - 2, col + 2) && jumpOverColor(RED, row - 1, col + 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col + 2));
				}
				if (moveCanland(row - 2, col - 2) && jumpOverColor(RED, row - 1, col - 1)) {
					singleCheckerJumpMoves.add(new CheckersMove(row, col, row - 2, col - 2));
				}
			}
		}
		if (singleCheckerJumpMoves.size() == 0) { // If no such jumps are possible
			return null;
		} else {
			CheckersMove[] resultCheckersMoves = new CheckersMove[singleCheckerJumpMoves.size()];
			for (int i = 0; i < singleCheckerJumpMoves.size(); i++) {
				resultCheckersMoves[i] = singleCheckerJumpMoves.get(i);
			}
			return resultCheckersMoves;

		}
	}

	/**
	 * checker after regular or jump action is still in range [0,7] and also target
	 * square is empty
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	boolean moveCanland(int x, int y) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7 && pieceAt(x, y) == EMPTY) {
			return true;
		}
		return false;
	}

	/**
	 * check after jump can land, whether the square in the middle is different
	 * color
	 * 
	 * @param color
	 * @param x
	 * @param y
	 * @return
	 */
	boolean jumpOverColor(int color, int x, int y) { // since if jumpCanland in range, this must in range
		if (color == BLACK) {
			if (pieceAt(x, y) == RED || pieceAt(x, y) == RED_KING) {
				return true;
			}

		} else if (color == RED) {
			if (pieceAt(x, y) == BLACK || pieceAt(x, y) == BLACK_KING) {
				return true;
			}
		}
		return false;
	}

}