import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vaccine extends Item {

    static BufferedImage sprite;
    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/vaccine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int size = 40;

    public Vaccine(int quantity, int xpos, int ypos) {
        super(quantity, xpos, ypos);
        this.name = "Vaccine";
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, itemXpos, itemYpos, size, size, null);
    }

}
