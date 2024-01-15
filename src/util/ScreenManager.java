package util;

import screens.*;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {
    JPanel[] screens = new JPanel[2];

    public JPanel[] createScreens(JFrame frame) {
        // make 2 jframes
        TitleScreen titleScreen = new TitleScreen();
        GameScreen gameScreen = new GameScreen();

        Dimension frameSize = frame.getSize();
        titleScreen.init(frameSize);
        gameScreen.init(frameSize);

        screens = new JPanel[] {titleScreen, gameScreen};
        return screens;
    }

    public void setVisibility(int screenIndex, boolean visible) {
        screens[screenIndex].setVisible(visible);
    }
}
