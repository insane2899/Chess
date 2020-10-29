package com.soumik.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;
import com.soumik.chess.engine.board.Move.MoveFactory;
import com.soumik.chess.engine.player.MoveTransition;

public class TestCheckmate {
	
	@Test
    public void testFoolsMate() {

        final Board board = Board.createStandardChessBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createdMove(board, BoardUtils.getCoordinateAtPosition("f2"),
                                BoardUtils.getCoordinateAtPosition("f3")));

        assertTrue(t1.getMoveStatus().isDone());

        final MoveTransition t2 = t1.getTransitionBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createdMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                                BoardUtils.getCoordinateAtPosition("e5")));

        assertTrue(t2.getMoveStatus().isDone());

        final MoveTransition t3 = t2.getTransitionBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createdMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"),
                                BoardUtils.getCoordinateAtPosition("g4")));

        assertTrue(t3.getMoveStatus().isDone());

        final MoveTransition t4 = t3.getTransitionBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createdMove(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"),
                                BoardUtils.getCoordinateAtPosition("h4")));

        assertTrue(t4.getMoveStatus().isDone());

        assertTrue(t4.getTransitionBoard().currentPlayer().isInCheckMate());

    }
}
