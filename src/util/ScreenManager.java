package util;

import screens.GameScreen;
import screens.TitleScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    public static JButton createButton(String text) {
        JButton newButton = new JButton(text, AssetManager.getImageIcon("button.png"));
        newButton.setBorderPainted(false);
        newButton.setContentAreaFilled(false);
        newButton.setFocusPainted(false);

        newButton.setHorizontalTextPosition(JButton.CENTER);
        newButton.setVerticalTextPosition(JButton.CENTER);
        newButton.setFont(AssetManager.getFont("PressStart2P-Regular.ttf").deriveFont(23f));
        newButton.setForeground(Color.BLACK);

        // change image when hovered over
        newButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newButton.setIcon(AssetManager.getImageIcon("hover.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newButton.setIcon(AssetManager.getImageIcon("button.png"));
            }
        });

        return newButton;
    }

    public ScreenManager(Dimension screenSize) {
        this.screenSize = screenSize;
    }
}
