import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gel extends Item {

    static int totalNumberOfGelUsed = 0;

    static BufferedImage sprite;

    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/gel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Gel(int xpos, int ypos){
        super(xpos, ypos);
        name = "Gel";
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(sprite, xpos, ypos, (int) (SIZE/1.5), SIZE, null);
        /*
        g.setColor(new Color(0x6EFFFF));
        g.fillRect(itemXpos, itemYpos, size, size);

         */
    }
}
