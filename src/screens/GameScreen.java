package screens;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public void init(Dimension size) {
        this.setSize(size);
        this.setLayout(null);
        this.setBackground(Color.BLUE);
        this.setVisible(true);
    }
}
