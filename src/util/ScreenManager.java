package util;

import screens.*;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {
    Dimension screenSize;
    JPanel[] screens = new JPanel[2];

    public JPanel[] createScreens() {
        // make 2 jframes
        TitleScreen titleScreen = new TitleScreen(screenSize);
        GameScreen gameScreen = new GameScreen(screenSize);

        screens = new JPanel[] {titleScreen, gameScreen};
        return screens;
    }

    public void setVisibility(int screenIndex, boolean visible) {
        screens[screenIndex].setVisible(visible);
    }

    public ScreenManager(Dimension screenSize) {
        this.screenSize = screenSize;
    }
}
