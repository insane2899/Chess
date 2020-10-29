package com.soumik.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.google.common.collect.Lists;
import com.soumik.chess.engine.board.Board;
import com.soumik.chess.engine.board.BoardUtils;
import com.soumik.chess.engine.board.Move;
import com.soumik.chess.engine.board.Tile;
import com.soumik.chess.engine.pieces.Piece;
import com.soumik.chess.engine.player.MoveTransition;
import com.soumik.chess.engine.player.ai.MiniMax;
import com.soumik.chess.engine.player.ai.MoveStrategy;

public class Table extends Observable{
		
	private final JFrame gameFrame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
	protected final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	protected final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	protected final BoardPanel boardPanel;
	private Board chessBoard;
	private String pieceIconPath = "chesspieces/plain/";
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	private BoardDirection boardDirection;
	private final MoveLog moveLog;
	private boolean highlightLegalMoves;
	private final GameSetup gameSetup;
	private Move computerMove;
	
	private static final Table INSTANCE = new Table();
		
	private Table() {
		this.gameFrame = new JFrame("Chess");
		this.gameHistoryPanel=new GameHistoryPanel();
		this.takenPiecesPanel=new TakenPiecesPanel();
		this.highlightLegalMoves = false;
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar =createTableMenuBar();
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.chessBoard = Board.createStandardChessBoard();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.boardPanel= new BoardPanel();
		this.moveLog = new MoveLog();
		this.addObserver(new TableGameAIWatcher());
		this.gameSetup=new GameSetup(this.gameFrame,true);
		this.boardDirection=BoardDirection.NORMAL;
		this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
		this.gameFrame.add(this.takenPiecesPanel,BorderLayout.WEST);
		this.gameFrame.add(this.gameHistoryPanel,BorderLayout.EAST);
		this.gameFrame.setVisible(true);
	}
	
	public static Table get() {
		return INSTANCE;
	}
	
	public void show() {
		Table.get().getMoveLog().clear();
		Table.get().getGameHistoryPanel().redo(chessBoard, Table.get().getMoveLog());
		Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
		Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
	}
	
	private GameSetup getGameSetup() {
		return this.gameSetup;
	}

	private JMenuBar createTableMenuBar() {
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		tableMenuBar.add(createOptionsMenu());
		return tableMenuBar;
	}
	
	private Board getGameBoard() {
		return this.chessBoard;
	}
	
	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Open up the PGN file");
			}
		});
		fileMenu.add(openPGN);
		final JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitMenuItem);
		return fileMenu;
	}
	
	private JMenu createPreferencesMenu() {
		final JMenu preferencesMenu = new JMenu("Preferences");
		final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
		flipBoardMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardDirection = boardDirection.opposite();
				boardPanel.drawBoard(chessBoard);
			}
		});
		preferencesMenu.add(flipBoardMenuItem);
		preferencesMenu.addSeparator();
		final JCheckBoxMenuItem legalMoveHighlighterCheckbox = new JCheckBoxMenuItem("Highlight Legal Moves",false);
		legalMoveHighlighterCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				highlightLegalMoves = legalMoveHighlighterCheckbox.isSelected();
			}
		});
		preferencesMenu.add(legalMoveHighlighterCheckbox);
		return preferencesMenu;
	}
	
	private JMenu createOptionsMenu() {
		final JMenu optionsMenu = new JMenu("Options");
		final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
		setupGameMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Table.get().getGameSetup().promptUser();
				Table.get().setupUpdate(Table.get().getGameSetup());
			}
		});
		optionsMenu.add(setupGameMenuItem);
		return optionsMenu;
	}
	
	private void setupUpdate(final GameSetup gameSetup) {
		setChanged();
		notifyObservers(gameSetup);
	}
	
	private static class TableGameAIWatcher implements Observer{

		@Override
		public void update(final Observable arg0,final Object arg1) {
			if(Table.get().getGameSetup().isAIPlayer(Table.get().getGameBoard().currentPlayer()) &&
				!Table.get().getGameBoard().currentPlayer().isInCheckMate() &&
				!Table.get().getGameBoard().currentPlayer().isInStaleMate()){
					final AIThinkTank thinkTank = new AIThinkTank();
					thinkTank.execute();
				}
			if(Table.get().getGameBoard().currentPlayer().isInCheckMate()) {
				System.out.println("Game Over, "+Table.get().getGameBoard().currentPlayer()+" is in Checkmate");
			}
			if(Table.get().getGameBoard().currentPlayer().isInStaleMate()) {
				System.out.println("Game Over, "+Table.get().getGameBoard().currentPlayer()+" is in Stalemate");
			}
			
		}
		
	}
	
	private void updateGameBoard(final Board board) {
		this.chessBoard=board;
	}
	
	private void updateComputerMove(final Move move) {
		this.computerMove=move;
	}
	
	private MoveLog getMoveLog() {
		return this.moveLog;
	}
	
	private GameHistoryPanel getGameHistoryPanel() {
		return this.gameHistoryPanel;
	}
	
	private TakenPiecesPanel getTakenPiecesPanel() {
		return this.takenPiecesPanel;
	}
	
	private BoardPanel getBoardPanel() {
		return this.boardPanel;
	}
	
	private void moveMadeUpdate(final PlayerType playerType) {
		setChanged();
		notifyObservers(playerType);
	}
	
	private static class AIThinkTank extends SwingWorker<Move,String>{
		private AIThinkTank() {
			
		}
		
		@Override
		protected Move doInBackground() throws Exception{
			final MoveStrategy miniMax = new MiniMax(4);
			final Move bestMove = miniMax.execute(Table.get().getGameBoard());
			return bestMove;
		}
		
		@Override
		protected void done() {
			try {
				final Move bestMove = get();
				Table.get().updateComputerMove(bestMove);
				Table.get().updateGameBoard(Table.get().getGameBoard().currentPlayer().makeMove(bestMove)
						.getTransitionBoard());
				Table.get().getMoveLog().addMove(bestMove);
				Table.get().getGameHistoryPanel().redo(Table.get().getGameBoard(),Table.get().getMoveLog());
				Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
				Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());
				Table.get().moveMadeUpdate(PlayerType.COMPUTER);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private class BoardPanel extends JPanel {

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
		
		public void drawBoard(final Board board) {
			removeAll();
			for(final TilePanel tilePanel: boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(board);
				add(tilePanel);
			}
			validate();
			repaint();
		}
	}
	
	protected static class MoveLog{
		private final List<Move> moves;
		
		MoveLog(){
			this.moves = new ArrayList<>();
		}
		
		public List<Move> getMoves(){
			return this.moves;
		}
		
		public void addMove(final Move move) {
			this.moves.add(move);
		}
		
		public int size() {
			return this.moves.size();
		}
		
		public void clear() {
			this.moves.clear();
		}
		
		public Move removeMove(int index) {
			return this.moves.remove(index);
		}
		
		public boolean removeMove(final Move move) {
			return this.moves.remove(move);
		}
	}

	private class TilePanel extends JPanel{
	
		private final int tileID;
		private final Color lightTileColor = new Color(232,237,140);
		private final Color darkTileColor = new Color(39,41,4);
		
		public TilePanel(final BoardPanel boardPanel,final int tileId) {
			super(new GridBagLayout());
			this.tileID = tileId;
			setPreferredSize(Table.TILE_PANEL_DIMENSION);
			assignTilePieceIcon(chessBoard);
			assignTileColor();
			addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(final MouseEvent e) {
					if(e.getButton()==MouseEvent.BUTTON3) {
						sourceTile = null;
						destinationTile =null;
						humanMovedPiece = null;
					}
					else if(e.getButton() == MouseEvent.BUTTON1) {
						if(sourceTile == null) {   //first click
							sourceTile = chessBoard.getTile(tileID);
							humanMovedPiece = sourceTile.getPiece();
							if(humanMovedPiece==null) {
								sourceTile = null;
							}
						}
						else {
							destinationTile = chessBoard.getTile(tileID);
							//System.out.println(sourceTile.getTileCoordinate()+" "+destinationTile.getTileCoordinate());
							final Move move = Move.MoveFactory.createdMove(chessBoard, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							if(transition.getMoveStatus().isDone()) {
								chessBoard = transition.getTransitionBoard();
								moveLog.addMove(move);
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						}
						SwingUtilities.invokeLater(()->{
							gameHistoryPanel.redo(chessBoard,moveLog);
							takenPiecesPanel.redo(moveLog);
							if(gameSetup.isAIPlayer(chessBoard.currentPlayer())) {
								Table.get().moveMadeUpdate(PlayerType.HUMAN);
							}
							boardPanel.drawBoard(chessBoard);
						});
					}
					
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
			validate();
		}
		
		public void highlightLegals(final Board board) {
			if(highlightLegalMoves) {
				for(final Move move:pieceLegalMoves(board)) {
					if(move.getDestinationCoordinate()==this.tileID) {
						try {
							add(new JLabel(new ImageIcon(ImageIO.read(new File("chesspieces/misc/green_dot.png")))));
						}catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		private Collection<Move> pieceLegalMoves(final Board board){
			if(humanMovedPiece !=null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
				return humanMovedPiece.calculateLegalMoves(board);
			}
			return Collections.emptyList();
		}
		
		public void drawTile(final Board board) {
			assignTileColor();
			assignTilePieceIcon(board);
			highlightLegals(board);
			validate();
			repaint();
		}
		
		private void assignTilePieceIcon(final Board board) {
			this.removeAll();
			if(board.getTile(this.tileID).isTileOccupied()) {
				try {
					final BufferedImage image = ImageIO.read(new File(pieceIconPath + board.getTile(this.tileID).getPiece().getPieceAlliance()
							.toString().charAt(0) + board.getTile(this.tileID).getPiece().toString().charAt(0)+".gif"));
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
	
	public enum BoardDirection{
		NORMAL {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return boardTiles;
			}

			@Override
			BoardDirection opposite() {
				return FLIPPED;
			}
		},
		FLIPPED {
			@Override
			List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return Lists.reverse(boardTiles);
			}

			@Override
			BoardDirection opposite() {
				return NORMAL;
			}
		};
		
		abstract List<TilePanel> traverse(final List<TilePanel> boardTiles);
		abstract BoardDirection opposite();
	}
	
	public enum PlayerType{
		HUMAN,
		COMPUTER;
	}
}
