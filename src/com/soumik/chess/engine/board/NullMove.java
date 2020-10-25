package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class NullMove extends Move {

	public NullMove() {
		super(null,65);
	}
	
	@Override
	public int getCurrentCoordinate() {
		return -1;
	}
	
	@Override
	public Board execute() {
		throw new RuntimeException("Cannot Execute the Null Move");
	}

}
