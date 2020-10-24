
package com.soumik.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.soumik.chess.engine.Alliance;
import com.soumik.chess.engine.board.AttackMove;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;
import com.soumik.chess.engine.board.MajorMove;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.pieces.Piece.PieceType;

public class Pawn extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16,7,9};

	public Pawn(final int piecePosition,final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.PAWN);
	}
	
	@Override
	public String toString() {
		return PieceType.PAWN.toString();
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition+ this.pieceAlliance.getDirection()*currentCandidateOffset;
			if(!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}
			
			if(currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {  	//Normal Move
				//TODO more work to be done 
				legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
			}
			else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack())||				//Pawn Jump
					(BoardUtils.SEVENTH_ROW[this.piecePosition]&&this.pieceAlliance.isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()*8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
				}
			}
			else if(currentCandidateOffset==7 &&
				!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
				(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
					if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) {
							legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
						}
					}
				}
			else if(currentCandidateOffset==9 &&
				!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
						(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
					if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) {
							legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
						}
					}
				}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public Piece movePiece(Move move) {
		return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance());
	}

}
