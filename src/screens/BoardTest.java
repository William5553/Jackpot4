package screens;

import structs.GamePiece;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameScreen extends JPanel implements ActionListener {
    private static int size = 400; // add 34 in the Y direction
    private static int offset = 10;
    private static int ovalSize = size / 4 - offset * 2;
    private static int pos = offset / 2;
    private static int incr = size / 4;

    private int[][] pieces = new int[4][4];
    private GamePiece addingPiece = null;
    private Timer pieceDropped = null;

    public void Board() {
        setPreferredSize(new Dimension(size, size));
        setBounds(0, 0, size, size);
        pieceDropped = new Timer(50, this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int column = (e.getPoint().x - pos) / incr;
                addPiece(column);
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Composite comp = g2d.getComposite();

        Dimension d = getSize();
        int w = d.width;
        int h = d.height;

        BufferedImage buffImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gbi = buffImg.createGraphics();

        // Clear area
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, w, h);

        // Draw screen
        //          gbi.setColor( Color.YELLOW );
        gbi.setColor(Color.BLUE);
        gbi.fillRect(0, 0, w, h);

        // Draw pieces or holes
        gbi.setColor(Color.RED);
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 4; column++) {
                if (pieces[row][column] == 1)
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .1f));
                else
                    gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.5f));
                gbi.fillOval(incr * column + pos, incr * row + pos, ovalSize, ovalSize);
            }
        }

        // Draw adding piece if we have it
        if (addingPiece != null) {
            gbi.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1.0f));
            gbi.fillOval(addingPiece.x, addingPiece.y, ovalSize, ovalSize);
        }

        // Draws the buffered image.
        g2d.drawImage(buffImg, null, 0, 0);

        //          g2d.setComposite( comp );
    }

    public void addPiece(int column) {
        if (addingPiece == null) {
            if (pieces[0][column] == 0) {
                addingPiece = new GamePiece(0, column, incr * column + pos, 0);
                pieceDropped.start();
            } else {
                getToolkit().beep();
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (addingPiece != null) {
            addingPiece.y += 5;
            int row = (addingPiece.y - pos) / incr + 1;
            if (row > 3 || pieces[row][addingPiece.column] == 1) {
                pieces[row - 1][addingPiece.column] = 1;
                addingPiece = null;
                pieceDropped.stop();
            }
        }
        repaint();
    }
}