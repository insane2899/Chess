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

public class Knight extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATES = {-17,-15,-10,-6,6,10,15,17};
	

	public Knight(final int piecePosition,final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.KNIGHT,true);
	}
	
	public Knight(final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove) {
		super(piecePosition, pieceAlliance,PieceType.KNIGHT,isFirstMove);
	}
	
	@Override
	public String toString() {
		return PieceType.KNIGHT.toString();
	}
	
	//If chessboard is visualized as this: 
	//SSSSSSSS
	//SSSSSSSS
	//SSESESSS
	//SESSSESS
	//SSSkSSSS
	//SESSSESS
	//SSESESSS
	//SSSSSSSS
	//Then position k-6 is E, k+6 is E,k-17 is E and so on.

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		
		final List<Move> legalMoves = new ArrayList<>();
		
		for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES) {
			int candidateDestinationCoordinate=this.piecePosition+currentCandidateOffset;
			if(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {  					//Check if the tile is valid
				if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)||
						isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)||
						isSeventhColumnExclusion(this.piecePosition,currentCandidateOffset)||
						isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					
					continue;
				}
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
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset==-17)||(candidateOffset==-10)||
				(candidateOffset==6)||(candidateOffset==15));
	}
	
	private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset==-10)||(candidateOffset==6));
	}
	
	private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset==-6)||(candidateOffset==10));
	}
	
	private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset==-15)||(candidateOffset==-6)||
				(candidateOffset==10)||(candidateOffset==17));
	}
	
	@Override
	public Piece movePiece(Move move) {
		return new Knight(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance(),false);
	}
}
