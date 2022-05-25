package com.soumik.chess.engine.player.ai;

import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.Move;

public interface MoveStrategy {
	public Move execute(Board board);
}
