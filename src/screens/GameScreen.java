package screens;

import structs.GamePiece;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class GameScreen extends JPanel implements ActionListener {
//    private static int size = 400; // add 34 in the Y direction
    private static int offset = 10;
    private static int ovalSize;// = size / 4 - offset * 2;
    private static int pos = offset / 2;
    private static int incr;// = size / 4;
    private static int boardPercentage = 80;
    private static int fallSpeed = 80;

    private int[][] pieces = new int[6][7];
    private GamePiece addingPiece;
    private Timer pieceDropped;

    public void init(Dimension size) {
        this.setSize(size);
//        this.setBackground(Color.MAGENTA);
        this.setVisible(false);
        setBounds(0, 0, size.width, size.height + 34);
        pieceDropped = new Timer(50, this);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int column = (e.getPoint().x - pos) / incr;
                addPiece(column);
            }
        });
        int randomNumberThatWorks = 20;
        ovalSize = size.width / randomNumberThatWorks - offset * 2;
        incr = size.width / randomNumberThatWorks;
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
//
//        // Clear area
//        g2d.setColor(Color.WHITE);
//        g2d.fillRect(0, 0, w, h);

        // Draw screen
        //          gbi.setColor( Color.YELLOW );
        gbi.setColor(Color.BLUE);
        gbi.fillRect(
                w * (boardPercentage / 100),
                h * (boardPercentage / 100),
                w - (w * ((100 - boardPercentage) / 100)),
                h - (h * ((100 - boardPercentage) / 100))
        );

        // Draw pieces or holes
        gbi.setColor(Color.GREEN);
        for (int row = 0; row < pieces.length; row++) {
            for (int column = 0; column < pieces[0].length; column++) {
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
            if (column < 0 || column >= pieces[0].length) {
                getToolkit().beep();
                return;
            }

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
            addingPiece.y += fallSpeed;
            int row = (addingPiece.y - pos) / incr + 1;
            if (row >= pieces.length || pieces[row][addingPiece.column] == 1) {
                pieces[row - 1][addingPiece.column] = 1;
                addingPiece = null;
                pieceDropped.stop();
            }
        }
        repaint();
    }
}