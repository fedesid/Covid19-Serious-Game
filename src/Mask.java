import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mask extends Item {

    static int totalNumberOfMaskUsed = 0;
    static int maskDuration = 5;

    static BufferedImage sprite;
    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/mask.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int size = 40;

    public Mask(int xpos, int ypos) {
        super(xpos, ypos);
        this.name = "Mask";
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(sprite, xpos, ypos, size, size, null);
    }
}
