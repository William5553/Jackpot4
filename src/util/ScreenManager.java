package util;

import screens.*;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScreenManager {
    Dimension screenSize;
    private static ArrayList<JPanel> screens = new ArrayList<JPanel>();

    public ArrayList<JPanel> createScreens() {
        // make 2 jframes
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
