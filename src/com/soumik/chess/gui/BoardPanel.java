package com.soumik.chess.gui;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.soumik.chess.engine.board.BoardUtils;

class BoardPanel extends JPanel {

	final List<TilePanel> boardTiles;
	
	public BoardPanel() {
		super(new GridLayout(8,8));
		this.boardTiles = new ArrayList<>();
		for(int i=0;i<BoardUtils.NUM_TILES;i++) {
			final TilePanel tilePanel = new TilePanel(this,i);
			this.boardTiles.add(tilePanel);
			add(tilePanel);
		}
		setPreferredSize(Table.BOARD_PANEL_DIMENSION);
		validate();
	}
}
