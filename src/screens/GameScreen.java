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
    private static int incr; // distance between holes
    private static final int fallSpeed = 80; // speed at which pieces fall

    private int[][] board;
    private GamePiece addingPiece;
    private final Timer pieceDropped;
    private final Random rand = new Random();

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

        // Calculate total board width and height
        int totalBoardWidth = COLUMNS * incr;
        int totalBoardHeight = ROWS * incr;

        // Calculate starting x and y coordinates for the board
        int startX = (screenSize.width - totalBoardWidth) / 2;
        int startY = (screenSize.height - totalBoardHeight) / 2;

        // Clear area
        //        g2d.setColor(Color.WHITE);
        //        g2d.fillRect(0, 0, w, h);

        gbi.setColor(Color.decode("#dcb639")); // colour of the board
        gbi.fillRect(0, 0, screenSize.width, screenSize.height);

        // Draw pieces or holes
        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board[0].length; column++) {
                if (board[row][column] != 0) {// if there is a piece there
                    gbi.setColor(board[row][column] == 1 ? Color.RED : Color.BLUE);
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                } else // no piece there
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5f));
                gbi.fillOval(startX + incr * column, startY + incr * row, ovalSize, ovalSize);
            }
        }

        // Draw adding piece if we have it
        if (addingPiece != null) {
            gbi.setColor(addingPiece.player == 1 ? Color.RED : Color.BLUE);
            gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1.0f));
            gbi.fillOval(startX + addingPiece.x, startY + addingPiece.y, ovalSize, ovalSize);
        }

        // Draws the buffered image.
        g2d.drawImage(buffImg, null, 0, 0);

        // Draw the player's turn
        g2d.setColor(currentPlayer == 1 ? Color.RED : Color.BLUE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Player " + currentPlayer + "'s turn", 10, 20);
    }

    public void restartGame(int numPlayers) {
        board = new int[ROWS][COLUMNS];
        this.numPlayers = numPlayers;
        currentPlayer = 1;
        this.repaint();
    }

    public void addPiece(int column, int player) {
        if (addingPiece != null) return;
        if (column < 0 || column >= board[0].length) {
            getToolkit().beep();
            return;
        }

        // checks if the column is full
        if (board[0][column] == 0) {
            addingPiece = new GamePiece(0, column, incr * column, -ovalSize / 3, player);
            pieceDropped.start();
        } else {
            getToolkit().beep();
        }
    }

    // gets called every time the timer ticks (every 50 milliseconds)
    public void actionPerformed(ActionEvent e) {
        if (addingPiece != null) {
            addingPiece.y += fallSpeed; // move the piece down
            int row = (addingPiece.y - offset / 2) / incr + 1; // calculate the row the piece is in
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
                    JOptionPane.showMessageDialog(this, "Player " + winner + " wins!");
                    restartGame(numPlayers);
                } else {
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

        // check for diagonal wins
        for (int row = 0; row < board.length - 3; row++) {
            for (int column = 3; column < board[0].length; column++) {
                if (board[row][column] != 0 && board[row][column] == board[row + 1][column - 1] && board[row][column] == board[row + 2][column - 2] && board[row][column] == board[row + 3][column - 3]) {
                    return board[row][column];
                }
            }
        }

        return 0;
    }

    public GameScreen(Dimension size) {
        System.out.println("Initializing game screen");
        this.setSize(size);
        this.setVisible(false);
        setBounds(0, 0, size.width, size.height);
        pieceDropped = new Timer(50, this); // timer that calls actionPerformed every 50 milliseconds
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int totalBoardWidth = COLUMNS * incr;

                // Calculate starting x coordinate for the board
                int startX = (getSize().width - totalBoardWidth) / 2;

                // Calculate the column based on the new starting x coordinate
                int column = (e.getPoint().x - startX) / incr;
                addPiece(column, currentPlayer);
            }
        });
        int randomNumberThatWorks = 12;
        ovalSize = size.width / randomNumberThatWorks - offset * 2; // size of holes
        incr = size.width / randomNumberThatWorks; // distance between holes

        // add the back button
//        JButton backBtn = ScreenManager.createButton("QUIT");
//        backBtn.addActionListener(e -> ScreenManager.showScreen(Screen.TITLE_SCREEN));

//        this.add(backBtn);
    }
}