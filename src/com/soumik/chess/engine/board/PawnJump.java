package com.soumik.chess.engine.board;

import com.soumik.chess.engine.board.Board.Builder;
import com.soumik.chess.engine.pieces.Pawn;
import com.soumik.chess.engine.pieces.Piece;


public class PawnJump extends Move {
	
	public PawnJump(final Board board,final Piece movedPiece,final int destinationCoordinate) {
		super(board, movedPiece, destinationCoordinate);
	}
	
	@Override
	public Board execute() {
		final Builder builder= new Builder();
		for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
			if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
		builder.setPiece(movedPawn);
		builder.setEnPassant(movedPawn);
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
	@Override
	public String toString() {
		return BoardUtils.getPositionAtCoordinate(destinationCoordinate);
	}

}
