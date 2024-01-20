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
            File musicPath = new File("src/assets/" + name);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                if (loops) clip.loop(Clip.LOOP_CONTINUOUSLY);
                if (name.equals("music.wav")) {
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
