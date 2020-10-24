package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class PawnAttackMove extends AttackMove {

	public PawnAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate,attackedPiece);
	}

}
