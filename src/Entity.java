import java.awt.*;

public abstract class Entity {

    int xpos, ypos;

    public Entity(){}

    public Entity(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public void chase(){}

    public abstract void paintComponent(Graphics g);

    public abstract void draw(Graphics g);

}
