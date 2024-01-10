import javax.swing.*;
import java.awt.*;

public class Connect4 {
    public void run() {
        JFrame frame = new JFrame("Connect 4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500,500));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);


        frame.setVisible(true);
        this.setSize(myFrame.getSize());

        ScreenManager screenManager = new ScreenManager();
        JPanel mainPanel = screenManager.createScreens();



    }

    public static void main(String[] args) {
        Connect4 myPanel = new Connect4();
        myPanel.run();
    }
}