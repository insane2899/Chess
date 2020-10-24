package com.soumik.chess.engine.board;

import com.soumik.chess.engine.board.Board.Builder;
import com.soumik.chess.engine.pieces.Piece;
import com.soumik.chess.engine.pieces.Rook;

public abstract class CastleMove extends Move {
	
	protected final Rook castleRook;
	protected final int castleRookStart;
	protected final int castleRookDestination;
	
	public CastleMove(final Board board,final Piece movedPiece,final int destinationCoordinate,
			final Rook castleRook,final int castleRookStart,final int castleRookDestination) {
		super(board, movedPiece, destinationCoordinate);
		this.castleRookStart = castleRookStart;
		this.castleRookDestination=castleRookDestination;
		this.castleRook = castleRook;
	}
	
	public Rook getCastleRook() {
		return this.castleRook;
	}
	
	@Override
	public boolean isCastlingMove() {
		return true;
	}
	
	@Override
	public Board execute() {
		final Builder builder = new Builder();
		for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
			if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setPiece(new Rook(this.castleRookDestination,this.castleRook.getPieceAlliance()));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
	
}
