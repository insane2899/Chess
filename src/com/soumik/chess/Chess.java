package com.soumik.chess;

import com.soumik.chess.engine.board.Board;
import com.soumik.chess.gui.Table;

public class Chess {

	public static void main(String[] args) throws Exception {
		Board board  = Board.createStandardChessBoard();
		System.out.println(board.toString());
		Table.get().show();

	}

}
