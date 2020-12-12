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

    public boolean collision(Player player){
        double distance = GamePanel.calcDistance(player.getXpos() + (player.getPlayerSize() /2), player.getYpos() + (player.getPlayerSize() /2), this.getXpos() + (this.getSize()/2), this.getYpos() + (this.getSize()/2));
        if( distance <= player.getPlayerSize() /2 + this.getSize()/4){
            return true;
        }
        return false;
    }


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
