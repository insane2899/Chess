
package com.soumik.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.soumik.chess.engine.Alliance;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.board.PawnAttackMove;
import com.soumik.chess.engine.board.PawnEnPassantAttackMove;
import com.soumik.chess.engine.board.PawnJump;
import com.soumik.chess.engine.board.PawnMove;
import com.soumik.chess.engine.board.PawnPromotion;

public class Pawn extends Piece {
	
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16,7,9};

	public Pawn(final int piecePosition,final Alliance pieceAlliance) {
		super(piecePosition, pieceAlliance,PieceType.PAWN,true);
	}
	
	public Pawn(final int piecePosition,final Alliance pieceAlliance,final boolean isFirstMove) {
		super(piecePosition, pieceAlliance,PieceType.PAWN,isFirstMove);
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
				if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
					legalMoves.add(new PawnPromotion(new PawnMove(board,this,candidateDestinationCoordinate)));
				}
				else {
					legalMoves.add(new PawnMove(board,this,candidateDestinationCoordinate));
				}
			}
			else if(currentCandidateOffset == 16 && this.isFirstMove() && 
					((BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack())||				//Pawn Jump
					(BoardUtils.SEVENTH_ROW[this.piecePosition]&&this.pieceAlliance.isWhite()))) {
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()*8);
				if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					legalMoves.add(new PawnJump(board,this,candidateDestinationCoordinate));
				}
			}
			else if(currentCandidateOffset==7 &&
				!((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
				(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
					if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate)));
							}
							else {
								legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
							}
						}
					}
					else if(board.getEnPassantPawn()!=null) {
						if(board.getEnPassantPawn().getPiecePosition()==(this.piecePosition + (this.pieceAlliance.getOppositeDirection()))) {
							final Piece pieceOnCandidate = board.getEnPassantPawn();
							if(this.pieceAlliance!= pieceOnCandidate.getPieceAlliance()) {
								legalMoves.add(new PawnEnPassantAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
							}
						}
					}
				}
			else if(currentCandidateOffset==9 &&
				!((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
						(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))){
					if(board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
						if(this.pieceAlliance!=pieceOnCandidate.getPieceAlliance()) {
							if(this.pieceAlliance.isPawnPromotionSquare(candidateDestinationCoordinate)) {
								legalMoves.add(new PawnPromotion(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate)));
							}
							else {
								legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
							}
						}
					}
					else if(board.getEnPassantPawn()!=null) {
						if(board.getEnPassantPawn().getPiecePosition()==(this.piecePosition - (this.pieceAlliance.getOppositeDirection()))) {
							final Piece pieceOnCandidate = board.getEnPassantPawn();
							if(this.pieceAlliance!= pieceOnCandidate.getPieceAlliance()) {
								legalMoves.add(new PawnEnPassantAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
							}
						}
					}
				}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public Piece movePiece(Move move) {
		return new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().getPieceAlliance(),false);
	}
	
	public Piece getPromotionPiece() {
		return new Queen(this.piecePosition,this.pieceAlliance,false);
	}

}
