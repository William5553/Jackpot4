package util;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AssetManager {
    public static FloatControl musicVolume;

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

    public static ImageIcon getScaledImageIcon(String fileName, int width) {
        // get the image width and height, find the aspect ratio, and scale the image
        int height = (int) (getImage(fileName).getHeight(null) * ((double) width / getImage(fileName).getWidth(null)));
        return new ImageIcon(getImage(fileName).getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    public static Font getFont(String fontName) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, AssetManager.class.getResourceAsStream("/assets/" + fontName));
        } catch (Exception e) {
            System.out.println("Error loading font: " + fontName);
            System.out.println(e);
        }
        return null;
    }

    public static void playSound(String name, boolean loops) {
        try {
            File audioFile = new File("src/assets/sounds/" + name + ".wav");

            if (audioFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(audioFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                if (loops) clip.loop(Clip.LOOP_CONTINUOUSLY);
                if (name.equals("music")) {
                    musicVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    musicVolume.setValue(-15.0f);
                }
                clip.start();
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
