import java.awt.*;

// Entity class is a parent class for all of the objects that are printed on the screen while the player is in the gameplay phase
/*
    Parent class of the following classes
        - Virus
        - Player
        - Items
*/
public abstract class Entity {

    int xpos, ypos, size;

    public Entity(){}

    public Entity(int xpos, int ypos){
        this.xpos = xpos;
        this.ypos = ypos;
        this.size = size;
    }

    public abstract void paintComponent(Graphics g);

    // Collision method which is really useful
    // It checks whether any Entity type objet has collided with the player
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
