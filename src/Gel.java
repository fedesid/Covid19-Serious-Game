import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Simple Gel class that extends Item
// This class represents the concept of the gel in the game
public class Gel extends Item {

    static int totalNumberOfGelUsed = 0;

    public Gel(int xpos, int ypos){
        super(xpos, ypos);
        name = "Gel";

        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/gel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

    }
}
