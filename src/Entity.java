import java.awt.*;

public abstract class Entity {

    int xpos, ypos, size;

    public Entity(){}

    public Entity(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
        this.size = size;
    }

    public abstract void paintComponent(Graphics g);

    public abstract void draw(Graphics g);

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public int getSize() {
        return size;
    }
}
