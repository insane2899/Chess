package com.soumik.chess.engine.board;

import static com.soumik.chess.engine.board.Board.Builder;
import com.soumik.chess.engine.pieces.Piece;

public final class MajorMove extends Move {

	public MajorMove(final Board board,final Piece movedPiece,final int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	}
	
	@Override
	public boolean equals(final Object other) {
		return this==other || (other instanceof MajorMove && super.equals(other));
	}
	
	@Override
	public String toString() {
		return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
	}
}
