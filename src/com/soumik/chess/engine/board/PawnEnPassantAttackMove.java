package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class PawnEnPassantAttackMove extends PawnAttackMove {
	
	public PawnEnPassantAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate,attackedPiece);
	}

}
