package util;

import javax.swing.*;
import java.awt.*;

public class AssetManager {
    public static ImageIcon getImageIcon(String fileName) {
        return new ImageIcon("src/assets/" + fileName);
    }

    public static Image getImage(String fileName) {
        return getImageIcon(fileName).getImage();
    }
}
