package com.soumik.chess.engine.player.ai;

import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.pieces.Piece;
import com.soumik.chess.engine.player.Player;

public final class StandardBoardEvaluator implements BoardEvaluator {

	private static final int CHECK_BONUS = 50;
	private static final int CHECKMATE_BONUS = 10000;
	private static final int DEPTH_BONUS = 100;
	private static final int CASTLE_BONUS = 60;
	
	@Override
	public int evaluate(final Board board,final  int depth) {
		return scorePlayer(board,board.whitePlayer(),depth)-scorePlayer(board,board.blackPlayer(),depth);
	}
	
	int scorePlayer(final Board board,final Player player,final int depth) {
		return pieceValue(player)+mobility(player)+check(player)+checkMate(player,depth);
	}
	
	private final static int pieceValue(final Player player) {
		int pieceValueScore = 0;
		for(final Piece piece: player.getActivePieces()) {
			pieceValueScore += piece.getPieceValue();
		}
		return pieceValueScore;
	}
	
	private final static int mobility(final Player player) {
		return player.getLegalMoves().size();
	}
	
	private final static int check(final Player player) {
		return player.getOpponent().isInCheck()?CHECK_BONUS:0;
	}
	
	private final static int checkMate(final Player player,final int depth) {
		return player.getOpponent().isInCheckMate()?CHECKMATE_BONUS*depthBonus(depth):0;
	}

	private static int depthBonus(int depth) {
		return depth==0?1:DEPTH_BONUS*depth;
	}
	
	private static int castled(Player player) {
		return player.isCastled()?CASTLE_BONUS:0;
	}

}
