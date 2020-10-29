package com.soumik.chess.engine.board;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardUtils {
	
	public static final boolean[] FIRST_COLUMN = initColumn(0);
	public static final boolean[] SECOND_COLUMN = initColumn(1);
	public static final boolean[] SEVENTH_COLUMN = initColumn(6);
	public static final boolean[] EIGHTH_COLUMN = initColumn(7);
	
	public static final boolean[] FIRST_ROW = initRow(0);
	public static final boolean[] SECOND_ROW =  initRow(8);   
	public static final boolean[] THIRD_ROW = initRow(16);
	public static final boolean[] FOURTH_ROW = initRow(24);
	public static final boolean[] FIFTH_ROW = initRow(32);
	public static final boolean[] SIXTH_ROW = initRow(40);
	public static final boolean[] SEVENTH_ROW = initRow(48);
	public static final boolean[] EIGHTH_ROW = initRow(56);
	
	public static final List<String> ALGEBRIC_NOTATION = initializeAlgebraicNotation();
	public static final Map<String,Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILES_PER_ROW=8;
	
	private static boolean[] initRow(int rowNumber) {				//argument is row Beginning tile number
		final boolean[] row = new boolean[NUM_TILES];
		do {
			row[rowNumber]=true;
			rowNumber++;
		}while (rowNumber%NUM_TILES_PER_ROW!=0);
		return row;
	}
	
	private static List<String> initializeAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

	private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < NUM_TILES; i++) {
            positionToCoordinate.put(ALGEBRIC_NOTATION.get(i), i);
        }
        return Collections.unmodifiableMap(positionToCoordinate);
    }

	private static boolean[] initColumn(int columnNumber) {
		final boolean[] column = new boolean[64];
		do {
			column[columnNumber]=true;
			columnNumber+=NUM_TILES_PER_ROW;
		}while(columnNumber<NUM_TILES);
		return column;
	}

	private BoardUtils() {
		throw new RuntimeException("You cannot instantiate this class");
	}
	
	public static boolean isValidTileCoordinate(int coordinate) {
		return coordinate>=0 && coordinate<NUM_TILES;
	}
	
	public static int getCoordinateAtPosition(final String position) {
		return POSITION_TO_COORDINATE.get(position);
	}

	public static String getPositionAtCoordinate(int destinationCoordinate) {
		return ALGEBRIC_NOTATION.get(destinationCoordinate);
	}
}
