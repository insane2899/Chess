package com.soumik.chess.engine.board;

import com.soumik.chess.engine.board.Board.Builder;
import com.soumik.chess.engine.pieces.Piece;

public abstract class Move {
	
	protected final Board board;
	protected final Piece movedPiece;
	protected final int destinationCoordinate;
	protected final boolean isFirstMove;
	public static final Move NULL_MOVE = new NullMove();
	
	Move(){
		this.board = null;
		this.movedPiece=null;
		this.destinationCoordinate=0;
		this.isFirstMove = false;
	}
	
	Move(final Board board,final Piece movedPiece,final int destinationCoordinate){
		this.board = board;
		this.movedPiece=movedPiece;
		this.destinationCoordinate=destinationCoordinate;
		this.isFirstMove=movedPiece.isFirstMove();
	}
	
	Move(final Board board,final int destinationCoordinate) {
		this.board = board;
		this.destinationCoordinate=destinationCoordinate;
		this.movedPiece = null;
		this.isFirstMove=false;
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this==other) {
			return true;
		}
		if(!(other instanceof Move)) {
			return false;
		}
		final Move otherMove = (Move)other;
		return this.getDestinationCoordinate()==otherMove.getDestinationCoordinate() &&
				this.getMovedPiece()==otherMove.getMovedPiece() && getCurrentCoordinate() == otherMove.getCurrentCoordinate();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + this.destinationCoordinate;
		result = prime*result+this.movedPiece.hashCode();
		result = prime*result + this.movedPiece.getPiecePosition();
		return result;
	}
	
	public int getCurrentCoordinate() {
		return this.movedPiece.getPiecePosition();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public boolean isAttacked() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public Piece getAttackedPiece() {
		return null;
	}

	public Board execute(){
		final Builder builder = new Builder();
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
			if(!this.movedPiece.equals(piece)) {		//This sees if the piece moved equals the piece asked to move
				builder.setPiece(piece);
			}
		}
		for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		//move the moved piece
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}
	
	public static class MoveFactory{
		private MoveFactory() {
			throw new RuntimeException("Not instantiable");
		}
		
		public static Move createdMove(final Board board, final int currentCoordinate,final int destinationCoordinate) {
			for(final Move move : board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate && move.getDestinationCoordinate()==destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}
}
