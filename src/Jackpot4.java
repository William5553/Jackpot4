import util.AssetManager;
import util.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Jackpot4 {
    public void run() {
        // play music
        AssetManager.playSound("music", true);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Screen size: " + screenSize);
        JFrame frame = new JFrame("Jackpot 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(AssetManager.getImage("icon.png")); // set icon to chips

        ScreenManager screenManager = new ScreenManager(screenSize);
        ArrayList<JPanel> screens = screenManager.createScreens(); // create the title and game screen

        for (JPanel screen : screens) {
            frame.add(screen); // add the screens to the frame
        }

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Jackpot4 game = new Jackpot4();
        game.run();
    }
}