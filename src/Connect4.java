import util.ScreenManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Connect4 {
    public void run() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Screen size: " + screenSize);
        JFrame frame = new JFrame("Jackpot 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        ScreenManager screenManager = new ScreenManager(screenSize);
        ArrayList<JPanel> screens = screenManager.createScreens();

        for (JPanel screen : screens) {
            frame.add(screen);
        }
    }

    public static void main(String[] args) {
        Connect4 myPanel = new Connect4();
        myPanel.run();
    }
}