import screens.*;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {

    public JPanel[] createScreens(JFrame frame) {
        // make 2 jframes
        TitleScreen titleScreen = new TitleScreen();
        GameScreen gameScreen = new GameScreen();

        Dimension frameSize = frame.getSize();
        titleScreen.init(frameSize);
        gameScreen.init(frameSize);

        return new JPanel[] {titleScreen, gameScreen};
    }
}
