package com.soumik.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.soumik.chess.engine.Alliance;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.KingSideCastleMove;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.board.QueenSideCastleMove;
import com.soumik.chess.engine.board.Tile;
import com.soumik.chess.engine.pieces.Piece;
import com.soumik.chess.engine.pieces.Rook;

public class WhitePlayer extends Player{

	public WhitePlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves){
		super(board,whiteStandardLegalMoves,blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	} 
	
	@Override 
	public Alliance getAlliance() {
		return Alliance.WHITE;
	}
	
	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		if(this.playerKing.isFirstMove()&&!this.isInCheck()) {
			if(!this.board.getTile(61).isTileOccupied() && !this.board.getTile(62).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(63);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
					if(this.calculateAttacksOnTile(61, opponentsLegals).isEmpty() && 
							this.calculateAttacksOnTile(62, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(63,opponentsLegals).isEmpty()) {
						kingCastles.add(new KingSideCastleMove(this.board,(Piece)this.playerKing,62,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),61));
					}
				}
			}
			if(!this.board.getTile(59).isTileOccupied() && !this.board.getTile(58).isTileOccupied() &&
					!this.board.getTile(57).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(56);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
					if(this.calculateAttacksOnTile(59, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(58,opponentsLegals).isEmpty() &&
							this.calculateAttacksOnTile(57, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(56,opponentsLegals).isEmpty()) {
						kingCastles.add(new QueenSideCastleMove(this.board,(Piece)this.playerKing,58,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),59));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
	
	@Override
	public String toString() {
		return "White";
	}
}
