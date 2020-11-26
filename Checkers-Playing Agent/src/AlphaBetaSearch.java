/**
 * @author Zhanghao Wen
 * @Date 10/16/2020
 */
package edu.iastate.cs472.proj1;

public class AlphaBetaSearch {
	private CheckersMove nextMove;
	private CheckersData board;
	private CheckersMove[] legalMoves;
	static final int EMPTY = 0, RED = 1, RED_KING = 2, BLACK = 3, BLACK_KING = 4;

	// An instance of this class will be created in the Checkers.Board
	// It would be better to keep the default constructor.

	public void setCheckersData(CheckersData board) {
		this.board = board;
	}

	// Todo: You can implement your helper methods here

	/**
	 * You need to implement the Alpha-Beta pruning algorithm here to find the best
	 * move at current stage. The input parameter legalMoves contains all the
	 * possible moves. It contains four integers: fromRow, fromCol, toRow, toCol
	 * which represents a move from (fromRow, fromCol) to (toRow, toCol). It also
	 * provides a utility method `isJump` to see whether this move is a jump or a
	 * simple move.
	 *
	 * @param legalMoves All the legal moves for the agent at current step.
	 */
	public CheckersMove makeMove(CheckersMove[] legalMoves) {
		// The checker board state can be obtained from this.board,
		// which is a int 2D array. The numbers in the `board` are
		// defined as
		// 0 - empty square,
		// 1 - red man
		// 2 - red king
		// 3 - black man
		// 4 - black king
		System.out.println(board);
		System.out.println();

		// Todo: return the move for the current state

		if (legalMoves != null) {
			this.legalMoves = new CheckersMove[legalMoves.length];
			for (int i = 0; i < legalMoves.length; i++) {
				this.legalMoves[i] = legalMoves[i];
			}
		} else {
			return null;
		}

		int scoreForSuggestedMove = MaxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		// System.out.println(scoreForSuggestedMove);

		// nextMove.printout();
		// System.out.println();
		return nextMove;
	}

	// helper function
	/**
	 * 
	 * @param board
	 * @param alpha
	 * @param beta
	 * @return
	 */
	public int MaxValue(CheckersData board, int alpha, int beta, int dep) {

		int depth = dep + 1;
		// System.out.println(depth);

		int currentValue = Integer.MIN_VALUE;
		CheckersData newBoard;
		CheckersMove[] newLegalMoves;

		if (depth == 1) {
			newLegalMoves = legalMoves;
		} else {
			newLegalMoves = board.getLegalMoves(BLACK);// agent black
		} // to look for the children

		if (depth > 5 || newLegalMoves == null)
			return roundScore(board);

		for (CheckersMove move : newLegalMoves) {

			newBoard = board.getResultFromMove(move); // RESULT(s,a)//modification

			// currentValue = Math.max(currentValue,
			// MinValue(board, alpha, beta));

			if (currentValue < MinValue(newBoard, alpha, beta, depth)) {

				currentValue = MinValue(newBoard, alpha, beta, depth);

				if (depth == 1)
					nextMove = move;
			}

			if (currentValue >= beta) {

				return currentValue;
			}

			alpha = Math.max(alpha, currentValue);

		}

		return currentValue;
	}

	public int MinValue(CheckersData board, int alpha, int beta, int dep) {

		int depth = dep;
		int currentValue = Integer.MAX_VALUE;
		CheckersData newBoard;
		CheckersMove[] newLegalMoves;

		if (depth == 1) {
			newLegalMoves = legalMoves;
		} else {
			newLegalMoves = board.getLegalMoves(RED);// agent black

		} // to look for the children

		if (depth > 5 || newLegalMoves == null)
			return roundScore(board);

		for (CheckersMove move : newLegalMoves) {
			newBoard = board.getResultFromMove(move); // RESULT(s,a)//modification

			currentValue = Math.min(currentValue, MaxValue(newBoard, alpha, beta, dep));

			if (currentValue <= alpha)
				return currentValue;

			beta = Math.min(beta, currentValue);

		}

		return currentValue;
	}

	public int roundScore(CheckersData board) { // evaluation function
		int B = 0;
		int BK = 0;
		int R = 0;
		int RK = 0;
		int BAtR6 = 0;
		int RAtR1 = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board.board[i][j] == BLACK)
					B++;
				if (board.board[i][j] == BLACK_KING)
					BK++;
				if (board.board[i][j] == RED)
					R++;
				if (board.board[i][j] == RED_KING)
					RK++;

			}
		}
		int EQ1 = 2 * (B + 2 * BK - R - 2 * RK);
		for (int j = 0; j < 8; j++) {
			if (board.board[6][j] == BLACK)
				BAtR6++;
			if (board.board[1][j] == RED)
				RAtR1++;
		}
		int EQ2 = BAtR6 - RAtR1;

		return EQ1 + EQ2;
	}

}
