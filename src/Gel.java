import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Gel extends Item {
    static BufferedImage sprite;

    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/gel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Gel(int quantity, int xpos, int ypos){
        super(quantity, xpos, ypos);
        name = "Gel";
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(sprite, itemXpos, itemYpos, SIZE, SIZE, null);
        /*
        g.setColor(new Color(0x6EFFFF));
        g.fillRect(itemXpos, itemYpos, size, size);

         */
    }
}
