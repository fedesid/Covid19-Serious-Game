import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Simple Mask class that extends Item
// This class represents the concept of masks in the game
public class Mask extends Item {

    static int totalNumberOfMaskUsed = 0;
    static int maskDuration = 5;

    int size = 40;

    public Mask(int xpos, int ypos) {
        super(xpos, ypos);
        this.name = "Mask";

        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/mask.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}
