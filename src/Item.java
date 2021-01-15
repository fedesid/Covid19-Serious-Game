import java.awt.*;
import java.awt.image.BufferedImage;

// Item class encapsulates the concept of an item in the game
/*
    Classes that extend this Item class:
        - Gel
        - Mask
        - Vaccine
 */
public abstract class Item extends Entity {
    String name;
    BufferedImage sprite;

    public static final int SIZE = 50;

    public Item(int xpos, int ypos){
        super(xpos, ypos);
    }

    public int getSize() {
        return SIZE;
    }

    public void draw(Graphics g){
        g.setColor(new Color(0x7C00E1FF, true));
        g.fillOval(xpos, ypos, SIZE, SIZE);
        g.setColor(new Color(0xFF5B6F72, true));
        g.drawOval(xpos, ypos, SIZE, SIZE);
        g.drawImage(sprite, xpos, ypos, SIZE, SIZE, null);
    }

}
