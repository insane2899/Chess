package com.soumik.chess.engine.board;

import com.soumik.chess.engine.pieces.Piece;

public class NullMove extends Move {

	public NullMove() {
		super(null, null,-1);
	}
	
	@Override
	public Board execute() {
		throw new RuntimeException("Cannot Execute the Null Move");
	}

}
