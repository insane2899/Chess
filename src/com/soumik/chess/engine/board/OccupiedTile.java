package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public final class OccupiedTile extends Tile{
	
	private final Piece pieceOnTile;
	//this final again makes pieceOnTile to be unchangeable once its value is set
	//Thus this subclass of Tile is also immutable.
	
	protected OccupiedTile(final int coordinate,final Piece pieceOnTile){
		super(coordinate);
		this.pieceOnTile = pieceOnTile;
	}
	
	@Override
	public boolean isTileOccupied() {
		return true;
	}
	
	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}
	
	@Override
	public String toString() {
		return getPiece().getPieceAlliance().isBlack()? getPiece().toString().toLowerCase():getPiece().toString();
	}
}
