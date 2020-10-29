package com.soumik.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.google.common.primitives.Ints;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.pieces.Piece;
import static com.soumik.chess.gui.Table.MoveLog;

public class TakenPiecesPanel extends JPanel {
	
	private final JPanel northPanel;
	private final JPanel southPanel;
	private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40,80);
	private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED); 
	private static final Color PANEL_COLOR = Color.decode("0xFDFE6");
	
	public TakenPiecesPanel() {
		super(new BorderLayout());
		setBackground(PANEL_COLOR);
		setBorder(PANEL_BORDER);
		this.northPanel = new JPanel(new GridLayout(8,2));
		this.southPanel = new JPanel(new GridLayout(8,2));
		this.northPanel.setBackground(PANEL_COLOR);
		this.southPanel.setBackground(PANEL_COLOR);
		this.add(this.northPanel,BorderLayout.NORTH);
		this.add(this.southPanel,BorderLayout.SOUTH);
		setPreferredSize(TAKEN_PIECES_DIMENSION);
	}
	
	public void redo(final MoveLog moveLog) {
		southPanel.removeAll();
		northPanel.removeAll();
		final List<Piece> whiteTakenPieces = new ArrayList<>();
		final List<Piece> blackTakenPieces = new ArrayList<>();
		
		for(final Move move: moveLog.getMoves()) {
			if(move.isAttacked()) {
				final Piece takenPiece = move.getAttackedPiece();
				if(takenPiece.getPieceAlliance().isWhite()) {
					whiteTakenPieces.add(takenPiece);
				}
				else {
					blackTakenPieces.add(takenPiece);
				}
			}
		}
		Collections.sort(whiteTakenPieces,new Comparator<Piece>() {
			@Override
			public int compare(Piece p1,Piece p2) {
				return Ints.compare(p1.getPieceValue(), p2.getPieceValue());
			}
		});
		Collections.sort(blackTakenPieces,new Comparator<Piece>() {
			@Override
			public int compare(Piece p1,Piece p2) {
				return Ints.compare(p1.getPieceValue(), p2.getPieceValue());
			}
		});
		for(final Piece takenPiece:whiteTakenPieces) {
			try {
				final BufferedImage image = ImageIO.read(new File("chesspieces/plain/"+takenPiece.getPieceAlliance().toString().charAt(0)+
						takenPiece.toString().charAt(0)+".gif"));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-15,
						icon.getIconWidth()-15, Image.SCALE_SMOOTH)));
				this.southPanel.add(label);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		for(final Piece takenPiece:blackTakenPieces) {
			try {
				final BufferedImage image = ImageIO.read(new File("chesspieces/plain/"+takenPiece.getPieceAlliance().toString().charAt(0)+
						takenPiece.toString()+".gif"));
				final ImageIcon icon = new ImageIcon(image);
				final JLabel label = new JLabel(new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth()-15,
						icon.getIconWidth()-15, Image.SCALE_SMOOTH)));
				this.northPanel.add(label);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println(whiteTakenPieces+" "+blackTakenPieces);
		validate();
		
	}
	

}
