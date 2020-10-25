package com.soumik.chess.engine.pieces;

import java.util.Collection;

import com.soumik.chess.engine.Alliance;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.Move;

public abstract class Piece {
	
	protected final PieceType pieceType;
	protected final int piecePosition;
	//Alliance is an enum
	protected final Alliance pieceAlliance;
	protected boolean isFirstMove;
	private final int cachedHashCode;
	
	Piece(final int piecePosition,final Alliance pieceAlliance,final PieceType pieceType,
				final boolean isFirstMove){
		this.pieceType = pieceType;
		this.pieceAlliance=pieceAlliance;
		this.piecePosition=piecePosition;
		this.isFirstMove = isFirstMove;
		cachedHashCode = computeHashCode();
	}
	
	@Override
	public boolean equals(final Object other) {
		if(this == other) {
			return true;
		}
		if(!(other instanceof Piece)) {
			return false;
		}
		final Piece otherPiece = (Piece)other;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
				pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
	}
	
	
	@Override
	public int hashCode() {
		return cachedHashCode;
	}
	
	
	public int computeHashCode() {
		int result = pieceType.hashCode();
		result = 31*result + pieceAlliance.hashCode();
		result = 31*result + piecePosition;
		result = 31*result + (isFirstMove?1:0);
		return result;
	}
	
	
	
	public int getPiecePosition() {
		return this.piecePosition;
	}
	
	public Alliance getPieceAlliance() {
		return this.pieceAlliance;
	}
	
	public boolean isFirstMove() {
		return this.isFirstMove;
	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}
	
	public int getPieceValue() {
		return this.pieceType.getPieceValue();
	}
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);
	public abstract Piece movePiece(Move move);
	
	public enum PieceType{
		
		PAWN("P",100) {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		ROOK("R",500) {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}
		},
		KNIGHT("N",300) {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		BISHOP("B",300) {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		QUEEN("Q",900) {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		},
		KING("K",1000) {
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		};
		
		private String pieceName;
		private int pieceValue;
		
		PieceType(final String pieceName,final int pieceValue){
			this.pieceName = pieceName;
			this.pieceValue = pieceValue;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		public int getPieceValue() {
			return this.pieceValue;
		}
		
		public abstract boolean isKing();
		public abstract boolean isRook();
	}
}
