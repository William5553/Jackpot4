package screens;

import structs.GamePiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel implements ActionListener {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    private static final int offset = 10;
    private static int ovalSize; // size of holes
    private static final int pos = offset / 2;
    private static int incr; // distance between holes
    private static final int fallSpeed = 60; // speed at which pieces fall

    private final int[][] pieces = new int[ROWS][COLUMNS];
    private GamePiece addingPiece;
    private final Timer pieceDropped;

    private Color currentPieceColor = Color.RED;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension d = getSize();
        int w = d.width;
        int h = d.height;

        BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = buffImg.createGraphics();

        // Calculate total board width and height
        int totalBoardWidth = COLUMNS * incr;
        int totalBoardHeight = ROWS * incr;

        // Calculate starting x and y coordinates for the board
        int startX = (w - totalBoardWidth) / 2;
        int startY = (h - totalBoardHeight) / 2;

        // Clear area
        //        g2d.setColor(Color.WHITE);
        //        g2d.fillRect(0, 0, w, h);

        // Draw screen
        gbi.setColor(Color.decode("#dcb639")); // colour of the board
        gbi.fillRect(0, 0, w, h);

        // Draw pieces or holes
        gbi.setColor(currentPieceColor);
        for (int row = 0; row < pieces.length; row++) {
            for (int column = 0; column < pieces[0].length; column++) {
                if (pieces[row][column] == 1) // if there is a piece there
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
                else // no piece there
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5f));
                gbi.fillOval(startX + incr * column, startY + incr * row, ovalSize, ovalSize);
            }
        }

        // Draw adding piece if we have it
        if (addingPiece != null) {
            gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1.0f));
            gbi.fillOval(startX + addingPiece.x, startY + addingPiece.y, ovalSize, ovalSize);
        }

        // Draws the buffered image.
        g2d.drawImage(buffImg, null, 0, 0);
    }

    public void addPiece(int column) {
        if (addingPiece != null) return;
        if (column < 0 || column >= pieces[0].length) {
            getToolkit().beep();
            return;
        }

        // checks if the column is full
        if (pieces[0][column] == 0) {
            addingPiece = new GamePiece(0, column, incr * column, -ovalSize/3);
            pieceDropped.start();
        } else {
            getToolkit().beep();
        }
    }

    // gets called every time the timer ticks (every 50 milliseconds)
    public void actionPerformed(ActionEvent e) {
        if (addingPiece != null) {
            addingPiece.y += fallSpeed; // move the piece down
            int row = (addingPiece.y - pos) / incr + 1; // calculate the row the piece is in
            // if the piece is in the last row or there is a piece below it
            if (row >= pieces.length || pieces[row][addingPiece.column] == 1) {
                pieces[row - 1][addingPiece.column] = 1; // add the piece to the board
                addingPiece = null;
                pieceDropped.stop();
            }
        }
        repaint();
    }

    public GameScreen(Dimension size) {
        System.out.println("Initializing game screen");
        this.setSize(size);
        this.setVisible(false);
        setBounds(0, 0, size.width, size.height + 34);
        pieceDropped = new Timer(50, this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                // Calculate total board width
                int totalBoardWidth = COLUMNS * incr;

                // Calculate starting x coordinate for the board
                int startX = (getSize().width - totalBoardWidth) / 2;

                // Calculate the column based on the new starting x coordinate
                int column = (e.getPoint().x - startX) / incr;
                addPiece(column);
            }
        });
        int randomNumberThatWorks = 15;
        ovalSize = size.width / randomNumberThatWorks - offset * 2; // size of holes
        incr = size.width / randomNumberThatWorks; // distance between holes
    }
}