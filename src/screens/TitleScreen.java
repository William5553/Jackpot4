package screens;

import util.AssetManager;
import util.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TitleScreen extends JPanel {
    // represented as a percentage of the screen height
    private static final double DISTANCE_BETWEEN_BUTTONS = 0.075;
    private static final double BUTTON_HEIGHT = 0.5;
    private static final String[] btnImgs = {"singleplayer.png", "multiplayer.png"};
    private static final ArrayList<JButton> buttons = new ArrayList<>();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Image logo = AssetManager.getImage("logo.png");

        g2d.drawImage(AssetManager.getImage("background.png"), 0, 0, null);
        g2d.drawImage(logo, (this.getSize().width / 2) - (logo.getWidth(null) / 2), (int) (this.getSize().height * .1), null);
    }

    public TitleScreen(Dimension size) {
        System.out.println("Initializing title screen");
        this.setSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.RED);

        Dimension gap = new Dimension(0, (int) (size.height * BUTTON_HEIGHT));
        this.add(new Box.Filler(gap, gap, gap));

        for (String button : btnImgs) {
            JButton newButton = new JButton("", AssetManager.getImageIcon(button));
            newButton.setBorderPainted(false);
            newButton.setContentAreaFilled(false);
            newButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            newButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            this.add(newButton);
            this.add(Box.createRigidArea(new Dimension(0, (int) (size.height * DISTANCE_BETWEEN_BUTTONS))));
            buttons.add(newButton);
        }

        buttons.get(0).addActionListener(e -> {
            ScreenManager.setVisibility(0, false);
            ScreenManager.setVisibility(1, true);
        });

        buttons.get(1).addActionListener(e -> {
            ScreenManager.setVisibility(0, false);
            ScreenManager.setVisibility(1, true);
        });

        this.setVisible(true);
    }
}