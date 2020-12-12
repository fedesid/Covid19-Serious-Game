import java.awt.*;

public abstract class Item extends Entity {
    String name;
    int itemXpos;
    int itemYpos;

    public static final int SIZE = 50;

    public Item(int xpos, int ypos){
        super(xpos, ypos);
    }

    public int getSize() {
        return SIZE;
    }

    public abstract void draw(Graphics g);

}
