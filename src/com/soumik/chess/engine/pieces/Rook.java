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

public final class Rook extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATES = {-8,-1,1,8};

	public Rook(final int piecePosition,final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.ROOK,true);
	}
	
	public Rook(final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove) {
		super(piecePosition, pieceAlliance,PieceType.ROOK,isFirstMove);
	}
	
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for(final int candidateCoordinateOffset: CANDIDATE_MOVE_VECTOR_COORDINATES) {
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				
				if(isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)||
						isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)) {
					break;
				}
				candidateDestinationCoordinate+=candidateCoordinateOffset;
				if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
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
						break;  //once we encounter a piece whether friendly or enemy the loop will break as rook will have to stop there
					}
				}
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset==-1));
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset==1));
	}
	
	@Override
	public Piece movePiece(Move move) {
		return new Rook(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance(),false);
	}


}
