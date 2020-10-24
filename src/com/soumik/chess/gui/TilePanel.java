package com.soumik.chess.gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;


class TilePanel extends JPanel{

	private final int tileID;
	private static final Color lightTileColor = new Color(232,237,140);
	private static final Color darkTileColor = new Color(39,41,4);
	private String pieceIconPath = "";
	public TilePanel(final BoardPanel boardPanel,final int tileId) {
		super(new GridBagLayout());
		this.tileID = tileId;
		setPreferredSize(Table.TILE_PANEL_DIMENSION);
		assignTileColor();
		validate();
	}
	
	private void assignTilePieceIcon(final Board board) {
		this.removeAll();
		if(board.getTile(this.tileID).isTileOccupied()) {
			try {
				final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getTile(this.tileID).getPiece().getPieceAlliance()
						.toString().charAt(0) + board.getTile(this.tileID).getPiece().toString()+".gif"));
				add(new JLabel(new ImageIcon(image)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void assignTileColor() {
		if(BoardUtils.FIRST_ROW[this.tileID]|| BoardUtils.THIRD_ROW[this.tileID]||
		   BoardUtils.FIFTH_ROW[this.tileID] || BoardUtils.SEVENTH_ROW[this.tileID]) {
			setBackground(this.tileID%2==0?lightTileColor:darkTileColor);
		}
		else if(BoardUtils.SECOND_ROW[this.tileID]|| BoardUtils.FOURTH_ROW[this.tileID]||
				BoardUtils.SIXTH_ROW[this.tileID]|| BoardUtils.EIGHTH_ROW[this.tileID]) {
			setBackground(this.tileID%2!=0?lightTileColor:darkTileColor);
			
		}
	}

}
