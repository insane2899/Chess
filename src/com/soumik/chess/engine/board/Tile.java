package com.soumik.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.soumik.chess.engine.pieces.Piece;

public abstract class Tile {
	
	protected final int tileCoordinate;
	//this tileCoordinate is not changeable. i.e once this tileCoordinate is set it cannot be changed again.
	//this will make Tile class immutable. i.e once this class is created it's values cannot be changed.
	
	private static final Map<Integer,EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
	//This is a cache of all possible EMPTY tiles i.e 64 empty tiles. If an empty tile is needed then we can
	//just take one from this cached empty tiles map using EMPTY_TILES.get(i).
	
	Tile(int tileCoordinate){
		this.tileCoordinate=tileCoordinate;
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Piece getPiece();
	
	public int getTileCoordinate() {
		return this.tileCoordinate;
	}
	
	private static Map<Integer,EmptyTile> createAllPossibleEmptyTiles(){
		final Map<Integer,EmptyTile> emptyTileMap = new HashMap<>();
		for(int i=0;i<BoardUtils.NUM_TILES;i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		
		// return Collections.unmodifiableMap(emptyTileMap);
		
		return ImmutableMap.copyOf(emptyTileMap);
	}
	
	//Only method anyone can use in this class
	public static Tile createTile(final int tileCoordinate,final Piece piece) {
		return piece!=null? new OccupiedTile(tileCoordinate,piece):EMPTY_TILES_CACHE.get(tileCoordinate);
	}
}
