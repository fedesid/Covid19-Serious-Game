import java.awt.*;

public abstract class Item extends Entity {
    String name;
    int itemXpos;
    int itemYpos;

    public static final int SIZE = 50;

    public Item(int xpos, int ypos){
        this.itemXpos = xpos;
        this.itemYpos = ypos;
    }

    public boolean collision(Player player){
        double distance = GamePanel.calcDistance(player.getXpos() + (player.getPlayerSize() /2), player.getYpos() + (player.getPlayerSize() /2), this.getItemXpos() + (this.getSize()/2), this.getItemYpos() + (this.getSize()/2));
        if( distance <= player.getPlayerSize() /2 + this.getSize()/4){
            return true;
        }
        return false;
    }

    public int getSize() {
        return SIZE;
    }

    public int getItemXpos() {
        return itemXpos;
    }

    public int getItemYpos() {
        return itemYpos;
    }

    public abstract void draw(Graphics g);

}
