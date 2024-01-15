package screens;

import util.AssetManager;
import javax.swing.*;
import java.awt.*;

public class TitleScreen extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        Image logo = AssetManager.getImage("logo.png");
        Image singleplayer = AssetManager.getImage("singleplayer.png");
        Image multiplayer = AssetManager.getImage("multiplayer.png");

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(AssetManager.getImage("background.png"), 0, 0, null);
        g2d.drawImage(logo, (this.getSize().width / 2) - (logo.getWidth(null) / 2), 110, null);
        g2d.drawImage(singleplayer, (this.getSize().width / 2) - (singleplayer.getWidth(null) / 2), 600, null);
        g2d.drawImage(multiplayer, (this.getSize().width / 2) - (multiplayer.getWidth(null) / 2), 725, null);
    }

    public void init(Dimension size) {
        this.setSize(size);
        this.setLayout(null);
        this.setBackground(Color.RED);
        this.setVisible(true);
    }
}