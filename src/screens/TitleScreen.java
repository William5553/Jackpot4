package screens;

import util.AssetManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TitleScreen extends JPanel {
//    JButton sButton, mButton;

    @Override
    public void paint(Graphics g) {
        System.out.println("Painting title screen");
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        Image logo = AssetManager.getImage("logo.png");
        Image singleplayer = AssetManager.getImage("singleplayer.png");
        Image multiplayer = AssetManager.getImage("multiplayer.png");

//        sButton.setIcon(AssetManager.getImageIcon("singleplayer.png"));
//        mButton.setIcon(AssetManager.getImageIcon("multiplayer.png"));

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.drawImage(AssetManager.getImage("background.png"), 0, 0, null);
        g2d.drawImage(logo, (this.getSize().width / 2) - (logo.getWidth(null) / 2), (int) (this.getSize().height * .1), null);
//        g2d.drawImage(singleplayer, (this.getSize().width / 2) - (singleplayer.getWidth(null) / 2), (int) (this.getSize().height * .55), null);
//        g2d.drawImage(multiplayer, (this.getSize().width / 2) - (multiplayer.getWidth(null) / 2), (int) (this.getSize().height * .675), null);

//        sButton.setLocation((this.getSize().width / 2) - (singleplayer.getWidth(null) / 2), 600);
//        this.add(sButton);
    }

    public TitleScreen(Dimension size) {
        System.out.println("Initializing title screen");
        this.setSize(size);
        this.setLayout(null);
        this.setBackground(Color.RED);
        this.setVisible(true);

//        System.out.println("here");

//        JButton sButton = new JButton("singleplayer", AssetManager.getImageIcon("singleplayer.png"));
//        JButton mButton = new JButton("multiplayer", AssetManager.getImageIcon("multiplayer.png"));

//        sButton.setBounds((this.getSize().width / 2) - (AssetManager.getImage("singleplayer.png").getWidth(null) / 2), 600, AssetManager.getImage("singleplayer.png").getWidth(null), AssetManager.getImage("singleplayer.png").getHeight(null));
//        mButton.setBounds((this.getSize().width / 2) - (AssetManager.getImage("multiplayer.png").getWidth(null) / 2), 725, AssetManager.getImage("multiplayer.png").getWidth(null), AssetManager.getImage("multiplayer.png").getHeight(null));
//        System.out.println("h2ere");


//        sButton.setLocation((this.getSize().width / 2) - (sButton.getWidth() / 2), 600);
//        sButton.setLocation((this.getSize().width / 2) - (AssetManager.getImage("singleplayer.png").getWidth(null) / 2), 600);
//        mButton.setLocation((this.getSize().width / 2) - (AssetManager.getImage("multiplayer.png").getWidth(null) / 2), 725);
//        System.out.println("he3re");

//        add(sButton);
//        add(mButton);
//        System.out.println("he4re");

        repaint();
    }
}