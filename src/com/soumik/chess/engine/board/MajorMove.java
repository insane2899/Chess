package com.soumik.chess.engine.board;

import static com.soumik.chess.engine.board.Board.Builder;
import com.soumik.chess.engine.pieces.Piece;

public final class MajorMove extends Move {

	public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	}
}
