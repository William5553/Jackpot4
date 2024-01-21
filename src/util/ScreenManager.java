package util;

import screens.GameScreen;
import screens.TitleScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScreenManager {
    public enum Screen {
        TITLE_SCREEN,
        GAME_SCREEN
    }

    Dimension screenSize;
    private static final ArrayList<JPanel> screens = new ArrayList<>();
    private static Screen currentScreen = Screen.TITLE_SCREEN;

    public ArrayList<JPanel> createScreens() {
        // make 2 jpanels, one for the title screen and one for the game screen
        TitleScreen titleScreen = new TitleScreen(screenSize);
        GameScreen gameScreen = new GameScreen(screenSize);

        screens.add(titleScreen);
        screens.add(gameScreen);

        return screens;
    }

    public static void showScreen(Screen screen) {
        screens.get(currentScreen.ordinal()).setVisible(false);
        currentScreen = screen;
        screens.get(screen.ordinal()).setVisible(true);
    }

    public static Object getScreen(Screen screen) {
        return screens.get(screen.ordinal());
    }

    public ScreenManager(Dimension screenSize) {
        this.screenSize = screenSize;
    }
}
