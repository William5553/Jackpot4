import javax.swing.*;

public class AssetManager {
    public ImageIcon getImage(String fileName) {
        return new ImageIcon("src/assets/" + fileName + ".png");
    }
}
