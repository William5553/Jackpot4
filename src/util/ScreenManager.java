package util;

import screens.GameScreen;
import screens.TitleScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScreenManager {
    Dimension screenSize;
    private static final ArrayList<JPanel> screens = new ArrayList<>();

    public ArrayList<JPanel> createScreens() {
        // make 2 jpanels, one for the title screen and one for the game screen
        TitleScreen titleScreen = new TitleScreen(screenSize);
        GameScreen gameScreen = new GameScreen(screenSize);

        screens.add(titleScreen);
        screens.add(gameScreen);

        return screens;
    }

    public static void setVisibility(int screenIndex, boolean visible) {
        screens.get(screenIndex).setVisible(visible);
    }

    public ScreenManager(Dimension screenSize) {
        this.screenSize = screenSize;
    }
}
