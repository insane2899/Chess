package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class PawnMove extends Move {
	
	public PawnMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	}

}
