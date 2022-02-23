package gui;

import table.*;
import auxiliar.*;
import pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.SwingConstants.*;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

/**
 * Crearea tablei de joc.
 */
public class ChessTableView {
    /**
     * vor fi stocate toate mutarile in vector pentru a putea reveni la o varianta anterioara
     */
    private final ChessTable[] undoTable = new ChessTable[100];
    /**
     * va fi stocata starea fiecarui jucator pentru fiecare mutare
     */
    private final Player[] undoPlayer = new Player[100];
    private int undoNumber = 0;

    private final Color highlightColor = Color.cyan;
    private Color lightColor = Color.lightGray;
    private Color darkColor = Color.darkGray;
    private String modelsType = "default";

    private final PlayerTurn playerTurn;
    private final JLabel playerTurnLabel;

    private Player player;
    private final Player playerA = new Player("alb", new Coordinates(7, 4));
    private final Player playerB = new Player("negru", new Coordinates(0, 4));
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private ChessTable chessTable;

    private Coordinates sourceTile;
    private Coordinates destinationTile;

    private final Dimension panelDim = new Dimension(600, 600);
    private final Dimension tileDim = new Dimension(10, 10);

    public ChessTableView() {
        chessTable = new ChessTable();
        playerTurnLabel = new JLabel("jucatorul alb");
        playerTurnLabel.setHorizontalAlignment(CENTER);
        playerTurn = new PlayerTurn();
        gameFrame = new JFrame("Joc de sah");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar jocMenuBar = createJMenuBar();
        gameFrame.setJMenuBar(jocMenuBar);
        Dimension dimensiuneJoc = new Dimension(600, 600);
        gameFrame.setSize(dimensiuneJoc);
        gameFrame.setLocation(400, 100);
        player = playerA;
        boardPanel = new BoardPanel();
        gameFrame.add(boardPanel, BorderLayout.CENTER);
        gameFrame.add(this.playerTurn, BorderLayout.NORTH);
        gameFrame.setVisible(true);
    }

    /**
     * Creeaza JMenuBar cu 2 optiuni, "options" si "undo".
     *
     * @return JMenuBar, meniul cu cele 2 optiuni
     */
    private JMenuBar createJMenuBar() {
        JMenuBar jocMenuBar = new JMenuBar();
        JMenu menu = createOptionsMenu();
        menu.add(createModelsMenu());
        menu.add(createTileMenu());
        jocMenuBar.add(menu);
        jocMenuBar.add(createUndoMenu());
        return jocMenuBar;
    }

    /**
     * Creeaza un JMenu care functioneaza ca un undo button.
     *
     * @return JMenu, reprezinta butonul undo
     */
    private JMenu createUndoMenu() {
        JMenu undoMenu = new JMenu("Undo");
        JMenuItem undo = new JMenuItem("undo");
        undo.setIcon(new ImageIcon("icons/undo1.png"));
        undo.addActionListener(e -> {
            if (undoNumber > 0) undoNumber--;
            chessTable = undoTable[undoNumber].cloneTablaJoc();
            player = undoPlayer[undoNumber].clonePlayer();
            playerTurnLabel.setText("jucatorul " + player.getColor());
            boardPanel.drawBoard();
        });
        undo.setMnemonic('u');
        undo.setAccelerator(KeyStroke.getKeyStroke('u'));
        undoMenu.add(undo);
        return undoMenu;
    }

    /**
     * Creeaza JMenu care contine 3 modele pentru culoarea spatiilor de pe tabla de joc.
     *
     * @return JMenu, reprezinta posibilitatea de a alege unul dintre cele 3 modele
     */
    private JMenu createTileMenu() {
        JMenu tileMenu = new JMenu("Change tile color");
        JMenuItem op1 = new JMenuItem("gray");
        JMenuItem op2 = new JMenuItem("dark brown");
        JMenuItem op3 = new JMenuItem("light brown");

        op1.setIcon(new ImageIcon("icons/gray.png"));
        op2.setIcon(new ImageIcon("icons/darkbrown.png"));
        op3.setIcon(new ImageIcon("icons/lightbrown.png"));

        op1.addActionListener(e -> {
            lightColor = Color.lightGray;
            darkColor = Color.darkGray;
            boardPanel.drawBoard();
        });
        op2.addActionListener(e -> {
            lightColor = Color.decode("0x824100");
            darkColor = Color.decode("0x4A2500");
            boardPanel.drawBoard();
        });
        op3.addActionListener(e -> {
            lightColor = Color.decode("0xFF962D");
            darkColor = Color.decode("0xAE5700");
            boardPanel.drawBoard();
        });

        tileMenu.add(op1);
        tileMenu.add(op2);
        tileMenu.add(op3);
        return tileMenu;
    }

    /**
     * Creeaza JMenu cu 3 modele de piese care pot fi alese.
     *
     * @return JMenu, reprezinta posibilitatea de a alege unul dintre cele 3 modele
     */
    private JMenu createModelsMenu() {
        JMenu modelsMenu = new JMenu("Change piece models");
        modelsMenu.setIcon(new ImageIcon("icons/models.png"));

        JRadioButton defaultModels = new JRadioButton("default");
        defaultModels.setSelected(true);
        JRadioButton fancyModels = new JRadioButton("fancy");
        JRadioButton customModels = new JRadioButton("custom");

        defaultModels.addActionListener(e -> {
            modelsType = "default";
            boardPanel.drawBoard();
            if (fancyModels.isSelected()) fancyModels.setSelected(false);
            if (customModels.isSelected()) customModels.setSelected(false);
        });
        modelsMenu.add(defaultModels);

        customModels.addActionListener(e -> {
            modelsType = "custom";
            boardPanel.drawBoard();
            if (fancyModels.isSelected()) fancyModels.setSelected(false);
            if (defaultModels.isSelected()) defaultModels.setSelected(false);
        });
        modelsMenu.add(customModels);

        fancyModels.addActionListener(e -> {
            modelsType = "fancy";
            boardPanel.drawBoard();
            if (defaultModels.isSelected()) defaultModels.setSelected(false);
            if (customModels.isSelected()) customModels.setSelected(false);
        });
        modelsMenu.add(fancyModels);
        return modelsMenu;
    }

    /**
     * Creeaza JMenu cu 2 optiuni, exit (opreste jocul) si reset game (reincepe alt joc).
     *
     * @return JMenu, meniul cu cele 2 optiuni
     */
    private JMenu createOptionsMenu() {
        JMenu menuOptions = new JMenu("Options");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setIcon(new ImageIcon("icons/x.png"));
        exitMenuItem.addActionListener(e -> System.exit(0));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        menuOptions.add(exitMenuItem);
        JMenuItem resetGame = new JMenuItem("Reset Game");
        resetGame.addActionListener(e -> {
            gameFrame.setVisible(false);
            new ChessTableView();
        });
        menuOptions.add(resetGame);
        return menuOptions;
    }

    /**
     * Clasa folosita pentru crearea spatiilor pe tabla de joc.
     */
    private class BoardPanel extends JPanel {
        /**
         * patratelele de pe tabla de joc
         */
        List<TilePanel> boardTiles;

        /**
         * Creeaza 64 de patratele.
         */
        BoardPanel() {
            super(new GridLayout(8, 8));
            boardTiles = new ArrayList<>();
            for (int i = 0; i < 64; i++) {
                TilePanel tilePanel = new TilePanel(this, i);
                boardTiles.add(tilePanel);
                add(tilePanel);
            }
            setPreferredSize(panelDim);
            validate();
        }

        /**
         * Deseneaza tabla de joc.
         * <p>
         * Deseneaza pe rand fiecare patratel, iar daca regele este in sah si o ajuns la patratelul pe care se afla
         * regele, il coloreaza in rosu.
         * </p>
         */
        public void drawBoard() {
            removeAll();
            int tileId = player.getKingPosition().getX() * 8 + player.getKingPosition().getY();
            for (TilePanel tilePanel : boardTiles) {
                tilePanel.drawTile();
                if (player.kingInCheck(chessTable))
                    if (tilePanel.tileId == tileId)
                        tilePanel.setBackground(Color.red);
                add(tilePanel);
            }
            validate();
            repaint();
        }
    }

    /**
     * Reprezinta patratelele de pe tabla de joc.
     */
    private class TilePanel extends JPanel {
        private final int tileId;

        TilePanel(BoardPanel boardPanel, int tileId) {
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(tileDim);
            assignTileColor();
            assignTilePieceIcon();

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    int ix, jy = 0;
                    if (isRightMouseButton(e)) {
                        sourceTile = null;
                        destinationTile = null;
                        assignTileColor();
                    } else if (isLeftMouseButton(e)) {
                        if (sourceTile == null) {
                            System.out.println("source");
                            boolean found = false;
                            for (ix = 0; ix < 8; ix++) {
                                for (jy = 0; jy < 8; jy++) {
                                    if (ix * 8 + jy == tileId) found = true;
                                    if (found) break;
                                }
                                if (found) break;
                            }
                            sourceTile = new Coordinates(ix, jy);
                            if (!chessTable.isPiesa(sourceTile, player.getColor())) {
                                System.out.println("NU POTI MUTA ACEASTA PIESA!");
                                sourceTile = null;
                            }
                            System.out.println(ix + " " + jy);

                        } else {
                            System.out.println("destination");
                            ArrayList<Move> moves = legalMoves();
                            boolean found = false;
                            for (ix = 0; ix < 8; ix++) {
                                for (jy = 0; jy < 8; jy++) {
                                    if (ix * 8 + jy == tileId) found = true;
                                    if (found) break;
                                }
                                if (found) break;
                            }
                            System.out.println(ix + " " + jy);
                            boolean ok = false;
                            assert moves != null;
                            for (Move move : moves) {
                                System.out.println(move.toString());
                                if (move.getNextPosition().getY() == jy && move.getNextPosition().getX() == ix) {
                                    ok = true;
                                }
                            }
                            if (ok) {
                                destinationTile = new Coordinates(ix, jy);
                                Move move = new Move(sourceTile, destinationTile);
                                undoPlayer[undoNumber] = player.clonePlayer();
                                undoTable[undoNumber] = chessTable.cloneTablaJoc();
                                undoNumber++;
                                chessTable.executaMutarea(move, player);
                                if (player.getColor().equals("alb")) player = playerB;
                                else player = playerA;
                                playerTurnLabel.setText("jucatorul " + player.getColor());
                                System.out.println("JUCATORUL " + player.getColor());
                                chessTable.printBoard();
                            }
                            sourceTile = null;
                            destinationTile = null;
                        }
                    }
                    boardPanel.drawBoard();
                    if (chessTable.esteEgalitate()) {
                        JOptionPane pane = new JOptionPane();
                        JOptionPane.showMessageDialog(pane, "Este egalitate!");
                    }
                    if (player.checkmate(chessTable)) {
                        System.err.print("SAH MAT");
                        JOptionPane pane = new JOptionPane();
                        String winner = "Castigatorul este jucatorul " + player.getOtherCuloare();
                        JOptionPane.showMessageDialog(pane, winner);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(Color.green);
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            validate();
        }

        /**
         * Seteaza modelul si imaginea pentru fiecare tip de piesa.
         */
        private void assignTilePieceIcon() {
            this.removeAll();
            int i, j = 0;
            boolean found = false;
            for (i = 0; i < 8; i++) {
                for (j = 0; j < 8; j++) {
                    if (i * 8 + j == tileId) found = true;
                    if (found) break;
                }
                if (found) break;
            }
            if (!chessTable.isLocLiber(new Coordinates(i, j))) {
                String piece = chessTable.getTable()[i][j].toString();
                if (piece.equals(piece.toUpperCase())) piece = piece + "1";
                String iconPath;
                if (modelsType.equals("default")) {
                    iconPath = "icons/" + piece + ".png";
                } else if (modelsType.equals("custom")) {
                    iconPath = "icons/" + piece.charAt(0) + piece + ".gif";
                } else {
                    iconPath = "icons/" + piece.charAt(0) + piece.charAt(0) + piece + ".png";
                }
                ImageIcon imageIcon = new ImageIcon(iconPath);
                add(new JLabel(imageIcon));
            }
        }

        /**
         * Seteaza culorile patratelelor tablei de joc.
         */
        private void assignTileColor() {
            ArrayList<Integer> whiteTilesId = new ArrayList<>();
            int[] a = new int[]{0, 2, 4, 6, 9, 11, 13, 15, 16, 18, 20, 22, 25, 27, 29, 31, 32, 34, 36, 38, 41, 43, 45, 47, 48, 50, 52, 54, 57, 59, 61, 63};
            for (int i : a)
                whiteTilesId.add(i);
            boolean contains = whiteTilesId.contains(tileId);
            if (contains) setBackground(lightColor);
            else setBackground(darkColor);
        }

        /**
         * Deseneaza toate patratelele.
         */
        public void drawTile() {
            assignTileColor();
            assignTilePieceIcon();
            highlightLegamalMoves();
            validate();
            repaint();
        }

        /**
         * Evidentiaza mutarile legale pe tabla de joc.
         */
        private void highlightLegamalMoves() {
            ArrayList<Move> possibleMoves = legalMoves();
            if (possibleMoves == null) return;
            for (Move move : possibleMoves) {
                if (move.getNextPosition().getY() + move.getNextPosition().getX() * 8 == tileId) {
                    Color color = this.getBackground();
                    if (color.equals(lightColor))
                        setBackground(highlightColor.brighter().brighter());
                    else
                        setBackground(highlightColor.darker().darker());
                }
            }
        }

        /**
         * Determina mutarile legale.
         *
         * @return arraylist cu toate muitarile legale
         */
        private ArrayList<Move> legalMoves() {
            if (sourceTile == null) return null;
            Piece p = chessTable.getTable()[sourceTile.getX()][sourceTile.getY()];
            ArrayList<Move> moves = new ArrayList<Move>();

            if (p != null) {
                if (p.getType().equals(Piece.TipPiesa.PAWN)) {
                    Pawn pawn = (Pawn) p;
                    moves = pawn.legalMoves(chessTable, player);
                } else if (p.getType().equals(Piece.TipPiesa.BISHOP)) {
                    Bishop bishop = (Bishop) p;
                    moves = bishop.legalMoves(chessTable, player);
                } else if (p.getType().equals(Piece.TipPiesa.KNIGHT)) {
                    Knight knight = (Knight) p;
                    moves = knight.legalMoves(chessTable, player);
                } else if (p.getType().equals(Piece.TipPiesa.ROOK)) {
                    Rook rook = (Rook) p;
                    moves = rook.legalMoves(chessTable, player);
                } else if (p.getType().equals(Piece.TipPiesa.QUEEN)) {
                    Queen queen = (Queen) p;
                    moves = queen.legalMoves(chessTable, player);
                } else if (p.getType().equals(Piece.TipPiesa.KING)) {
                    King king = (King) p;
                    moves = king.legalMoves(chessTable, player);
                }
            }
            return moves;
        }
    }

    /**
     * Reprezinta jucatorul care trebuie sa mute
     */
    class PlayerTurn extends JPanel {

        public PlayerTurn() {
            super(new BorderLayout());
            add(playerTurnLabel);
            setBackground(Color.white);
            setPreferredSize(new Dimension(80, 40));
        }
    }
}
