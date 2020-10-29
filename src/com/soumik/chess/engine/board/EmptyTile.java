package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public final class EmptyTile extends Tile {
	
	protected EmptyTile(final int coordinate){
		super(coordinate);
	}
	
	@Override
	public boolean isTileOccupied() {
		return false;
	}
	
	@Override
	public Piece getPiece() {
		return null;
	}
	
	@Override
	public String toString() {
		return "-";
	}
}
