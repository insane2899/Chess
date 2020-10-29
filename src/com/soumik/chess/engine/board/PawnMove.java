package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class PawnMove extends Move {
	
	public PawnMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	}
	
	public boolean equals(Object other) {
		return this==other || (other instanceof PawnMove && super.equals(other));
	}
	
	@Override
	public String toString() {
		return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}

}
