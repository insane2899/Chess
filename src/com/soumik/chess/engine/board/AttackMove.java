package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class AttackMove extends Move {
	
	final Piece attackedPiece;

	public AttackMove(final Board board,final Piece movedPiece,final int destinationCoordinate,final Piece attackedPiece) {
		super(board, movedPiece, destinationCoordinate);
		this.attackedPiece=attackedPiece;
	}
	
	@Override
	public boolean isAttacked() {
		return true;
	}
	
	@Override
	public int hashCode() {
		return this.attackedPiece.hashCode() + super.hashCode();
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this==other) {
			return true;
		}
		if(!(other instanceof AttackMove)) {
			return false;
		}
		final AttackMove otherAttackMove = (AttackMove)other;
		return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
	}
	
	@Override
	public Piece getAttackedPiece() {
		return this.attackedPiece;
	}
	
	@Override
	public String toString() {
		return "x"+BoardUtils.getPositionAtCoordinate(destinationCoordinate);
	}

}
