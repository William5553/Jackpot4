package screens;

import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(new ImageIcon("src/assets/background.jpg").getImage(), 6, 0, null);
        g2d.drawImage(new ImageIcon("src/assets/singleplayer.png").getImage(), (this.getSize().width / 2), 300, null);
        g2d.drawImage(new ImageIcon("src/assets/multiplayer.png").getImage(), this.getSize().width / 2, 700, null);
    }

    public void init(Dimension size) {
        this.setSize(size);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setVisible(true);
    }
}
