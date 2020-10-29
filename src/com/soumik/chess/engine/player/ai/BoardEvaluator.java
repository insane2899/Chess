package com.soumik.chess.engine.player.ai;

import com.soumik.chess.engine.board.Board;

public interface BoardEvaluator {
	int evaluate(Board board,int depth);
}
