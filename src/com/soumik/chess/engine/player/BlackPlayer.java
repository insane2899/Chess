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

public class BlackPlayer extends Player {

	public BlackPlayer(final Board board,final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves){
		super(board,blackStandardLegalMoves,whiteStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}
	
	@Override
	protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,final Collection<Move> opponentsLegals) {
		final List<Move> kingCastles = new ArrayList<>();
		if(this.playerKing.isFirstMove()&&!this.isInCheck()) {
			if(!this.board.getTile(6).isTileOccupied() && !this.board.getTile(5).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(7);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
					if(this.calculateAttacksOnTile(6, opponentsLegals).isEmpty() && 
							this.calculateAttacksOnTile(5, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(7,opponentsLegals).isEmpty()) {
						kingCastles.add(new KingSideCastleMove(this.board,(Piece)this.playerKing,6,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),5));
					}
				}
			}
			if(!this.board.getTile(1).isTileOccupied() && !this.board.getTile(3).isTileOccupied() &&
					!this.board.getTile(2).isTileOccupied()) {
				final Tile rookTile = this.board.getTile(0);
				if(rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove() && rookTile.getPiece().getPieceType().isRook()) {
					if(this.calculateAttacksOnTile(2, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(3,opponentsLegals).isEmpty() &&
							this.calculateAttacksOnTile(1, opponentsLegals).isEmpty() && this.calculateAttacksOnTile(0,opponentsLegals).isEmpty()) {
						kingCastles.add(new QueenSideCastleMove(this.board,(Piece)this.playerKing,2,(Rook)rookTile.getPiece(),rookTile.getTileCoordinate(),3));
					}
				}
			}
		}
		return ImmutableList.copyOf(kingCastles);
	}
	
	@Override
	public String toString() {
		return "Black";
	}

}
