package com.soumik.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.soumik.chess.engine.Alliance;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;
import com.soumik.chess.engine.board.MajorAttackMove;
import com.soumik.chess.engine.board.MajorMove;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.board.Tile;

public class King extends Piece {
	
	private static final int[] CANDIDATE_COORDINATE_MOVE= {-9,-8,-7,-1,1,7,8,9};

	public King(final int piecePosition,final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.KING,true);
	}
	
	public King(final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove) {
		super(piecePosition, pieceAlliance,PieceType.KING,isFirstMove);
	}

	
	@Override
	public String toString() {
		return PieceType.KING.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for(final int currentCandidateOffset: CANDIDATE_COORDINATE_MOVE) {
			int candidateDestinationCoordinate = this.piecePosition+currentCandidateOffset;
			if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)||
					(isEighthColumnExclusion(this.piecePosition,currentCandidateOffset))) {
				continue;
			}
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)){
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
				if(!candidateDestinationTile.isTileOccupied()) {  										//check if the tile has a piece or not
					legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
				}
				else {
					final Piece pieceAtDestination = candidateDestinationTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
					if(this.pieceAlliance!=pieceAlliance) {												//Check if presence is same colour or different
						legalMoves.add(new MajorAttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
					}
				}
			}
			
		}
		
		
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset==-1)||(candidateOffset==-9)||
				(candidateOffset==7));
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset==-7)||(candidateOffset==1)||
				(candidateOffset==9));
	}
	
	@Override
	public Piece movePiece(Move move) {
		return new King(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance(),false);
	}

}
