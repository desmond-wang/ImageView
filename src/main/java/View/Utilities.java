package View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Utilities {

    public static ImageIcon readImage(String name) {
        try {
            return new ImageIcon(ImageIO.read(new File(name)).getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(2);
            return null;
        }
    }
}
