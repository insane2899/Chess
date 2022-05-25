package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class PawnAttackMove extends AttackMove {

	public PawnAttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate,attackedPiece);
	}
	
	@Override
	public boolean equals(final Object other) {
		return this==other || (other instanceof PawnAttackMove && super.equals(other));
	}
	
	@Override
	public String toString() {
		return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).charAt(0)+"x"+
				BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}

}
