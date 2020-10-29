package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class MajorAttackMove extends AttackMove {

	public MajorAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate, attackedPiece);
	}
	
	@Override
	public boolean equals(final Object other) {
		return this==other || (other instanceof MajorAttackMove && super.equals(other));
	}
	
	@Override
	public String toString() {
		return movedPiece.getPieceType() +"x"+ BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}

}
