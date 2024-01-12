package util;

import javax.swing.*;
import java.awt.*;

public class AssetManager {
    public static ImageIcon getImageIcon(String fileName) {
        try {
            return new ImageIcon("src/assets/" + fileName);
        } catch (Exception e) {
            System.out.println("Error loading image: " + fileName);
            System.out.println(e);
        }
        return null;
    }

    public static Image getImage(String fileName) {
        return getImageIcon(fileName).getImage();
    }
}