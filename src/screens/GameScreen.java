package screens;

import structs.GamePiece;
import util.AssetManager;
import util.ScreenManager;
import util.ScreenManager.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class GameScreen extends JPanel implements ActionListener {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private static final int offset = 10;
    private static int ovalSize; // size of holes
    private static int pieceDistance; // distance between holes
    private static final int fallSpeed = 80; // speed at which pieces fall
    private static final int gridSizeRatio = 12;

    private int[][] board;
    private GamePiece addingPiece;
    private final Timer pieceDropped;
    private final Random rand = new Random();

    private static final Image redPieceImage = AssetManager.getImage("pieces/red.png");
    private static final Image greenPieceImage = AssetManager.getImage("pieces/green.png");

    private int numPlayers;
    private int currentPlayer = 1;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension screenSize = getSize();

        BufferedImage buffImg = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = buffImg.createGraphics();

        // Calculate starting x and y coordinates for the board
        int startX = (screenSize.width - COLUMNS * pieceDistance) / 2;
        int startY = (screenSize.height - ROWS * pieceDistance) / 2;

        // Clear area
        // g2d.setColor(Color.WHITE);
        // g2d.fillRect(0, 0, w, h);

        gbi.setColor(Color.decode("#dcb639")); // colour of the board
        gbi.fillRect(0, 0, screenSize.width, screenSize.height);

        // Draw pieces or holes
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != 0) { // if there is a piece there
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                    gbi.drawImage(getPieceImage(board[row][column]), startX + pieceDistance * column, startY + pieceDistance * row, ovalSize, ovalSize, null);
                } else { // no piece there
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5f));
                    gbi.fillOval(startX + pieceDistance * column, startY + pieceDistance * row, ovalSize, ovalSize);
                }
            }
        }

        // Draw adding piece
        if (addingPiece != null) {
            gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1.0f));
            gbi.drawImage(getPieceImage(addingPiece.player), startX + addingPiece.x, startY + addingPiece.y, ovalSize, ovalSize, null);
        }

        // Draws the buffered image.
        g2d.drawImage(buffImg, null, 0, 0);

        // Draw the player's turn
        g2d.setColor(currentPlayer == 1 ? Color.RED : Color.decode("#009B24"));
        g2d.setFont(AssetManager.getFont("PressStart2P-Regular.ttf").deriveFont(18f));
        if (numPlayers == 1)
            g2d.drawString((currentPlayer == 1 ? "Your" : "Computer's") + " turn", 10, 30);
        else
            g2d.drawString("Player " + currentPlayer + "'s turn", 10, 30);
    }

    public void restartGame(int numPlayers) {
        board = new int[ROWS][COLUMNS]; // reset the board
        this.numPlayers = numPlayers; // set the number of players
        currentPlayer = 1;
        this.repaint();
    }

    public void addPiece(int column, int player) {
        if (addingPiece != null) return; // if there is already a piece being added, don't add another one
        if (column < 0 || column >= board[0].length) return; // if the column is out of bounds

        // checks if the column is full
        if (board[0][column] == 0) {
            addingPiece = new GamePiece(0, column, pieceDistance * column, -ovalSize / 3, player);
            pieceDropped.start();
        } else
            getToolkit().beep();
    }

    // gets called every time the timer ticks (every 50 milliseconds)
    public void actionPerformed(ActionEvent e) {
        if (addingPiece == null) return; // if there is no piece being added, don't do anything

        addingPiece.y += fallSpeed; // move the piece down
        int row = (addingPiece.y - offset / 2) / pieceDistance + 1; // calculate the row the piece is in
        // if the piece is in the last row or there is a piece below it
        if (row >= board.length || board[row][addingPiece.column] != 0) {
            // plays a random drop sound
            AssetManager.playSound("drop" + rand.nextInt(1, 5), false);
            board[row - 1][addingPiece.column] = currentPlayer; // add the piece to the board
            addingPiece = null;
            pieceDropped.stop();

            // check for wins
            int winner = checkForWin();
            if (winner != 0) {
                if (numPlayers == 1)
                    JOptionPane.showMessageDialog(this, (winner == 1 ? "You" : "The computer") + " won!");
                else
                    JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                restartGame(numPlayers);
            } else {
                boolean tie = checkForTie();
                if (tie) {
                    JOptionPane.showMessageDialog(this, "It's a tie!");
                    restartGame(numPlayers);
                } else {
                    // switch players
                    currentPlayer++;

                    // if it's singleplayer mode, and it's the computer's turn
                    // addingPiece == null is necessary or else infinite loop
                    if (numPlayers == 1 && currentPlayer == 2 && addingPiece == null) {
                        int computerColumn = rand.nextInt(COLUMNS);
                        // if the column is full, keep generating random numbers until it's not
                        while (board[0][computerColumn] != 0)
                            computerColumn = rand.nextInt(COLUMNS);
                        addPiece(computerColumn, currentPlayer);
                    }

                    // switch back to the human player after the computer's move is made
                    if (numPlayers == 1 && currentPlayer == 3)
                        currentPlayer = 1;
                    else if (numPlayers > 1 && currentPlayer > numPlayers)
                        currentPlayer = 1;
                }
            }
        }
        this.repaint();
    }

    private int checkForWin() {
        // check for horizontal wins
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length - 3; column++) {
                if (board[row][column] != 0 && board[row][column] == board[row][column + 1] && board[row][column] == board[row][column + 2] && board[row][column] == board[row][column + 3]) {
                    return board[row][column];
                }
            }
        }

        // check for vertical wins
        for (int row = 0; row < board.length - 3; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != 0 && board[row][column] == board[row + 1][column] && board[row][column] == board[row + 2][column] && board[row][column] == board[row + 3][column]) {
                    return board[row][column];
                }
            }
        }

        // check for diagonal wins
        for (int row = 0; row < board.length - 3; row++) {
            for (int column = 0; column < board[0].length - 3; column++) {
                if (board[row][column] != 0 && board[row][column] == board[row + 1][column + 1] && board[row][column] == board[row + 2][column + 2] && board[row][column] == board[row + 3][column + 3]) {
                    return board[row][column];
                }
            }
        }
        for (int row = 0; row < board.length - 3; row++) {
            for (int column = 3; column < board[0].length; column++) {
                if (board[row][column] != 0 && board[row][column] == board[row + 1][column - 1] && board[row][column] == board[row + 2][column - 2] && board[row][column] == board[row + 3][column - 3]) {
                    return board[row][column];
                }
            }
        }

        return 0;
    }

    private boolean checkForTie() {
        // check for a tie
        boolean tie = true;
        for (int[] row1 : board) {
            for (int column = 0; column < board[0].length; column++) {
                if (row1[column] == 0) {
                    tie = false;
                    break;
                }
            }
        }
        return tie;
    }

    private Image getPieceImage(int player) {
        return player == 1 ? redPieceImage : greenPieceImage;
    }

    public GameScreen(Dimension size) {
        System.out.println("Initializing game screen");
        this.setSize(size);
        this.setVisible(false);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ovalSize = size.width / gridSizeRatio - offset * 2; // size of holes
        pieceDistance = size.width / gridSizeRatio; // distance between holes

        pieceDropped = new Timer(50, this); // timer that calls actionPerformed every 50 milliseconds
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int totalBoardWidth = COLUMNS * pieceDistance;

                // Calculate starting x coordinate for the board
                int startX = (getSize().width - totalBoardWidth) / 2;

                // Calculate the column based on the new starting x coordinate
                int column = (e.getPoint().x - startX) / pieceDistance;
                addPiece(column, currentPlayer);
            }
        });

        Dimension gap = new Dimension(0, (int) (size.height * .825));
        this.add(new Box.Filler(gap, gap, gap)); // make a gap between the top of the screen and the buttons

        // add the back button
        JButton backBtn = ScreenManager.createButton("MAIN MENU", size.width / 6);
        backBtn.addActionListener(e -> ScreenManager.showScreen(Screen.TITLE_SCREEN));

        this.add(backBtn);
    }
}