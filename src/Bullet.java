import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.atan2;

// Bullet class which handles the bullets shot by the player
public class Bullet extends Entity {
    static BufferedImage sprite;
    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int xPos;
    int yPos;
    int yBegin;
    int xBegin;
    int xEnd;
    int yEnd;
    double xt;
    double yt;

    int direction;
    double slope;
    double angle;

    int damage = 25;

    int size = 20;

    int speed = 2;

    public Bullet(int xBegin, int yBegin, int xEnd, int yEnd){
        this.xPos = xBegin - size/2;
        this.yPos = yBegin - size/2;
        this.xBegin = xBegin;
        this.yBegin = yBegin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.yt = -GamePanel.calcAngleSin(xBegin, yBegin, xEnd, yEnd);
        this.xt = GamePanel.calcAngleCos(xBegin, yBegin, xEnd, yEnd);
        slope = GamePanel.calcSlope(xBegin, yBegin, xEnd, yEnd);
        System.out.println("SLOPE: " + slope);

        int deltaX = xEnd - xBegin;
        int deltaY = yEnd - yBegin;
        angle = atan2(deltaY, deltaX);

        this.direction = xEnd - xBegin;

        System.out.println("BULLET: " + xPos + " " + yPos);
        System.out.println(xt + " " + yt);
    }

    // Method move which moves the bullet in the right direction
    public void move(){

        xPos += (int) ((xt/5)*(speed));
        yPos += (int) ((yt/5)*(speed));

    }

    // Checks if the bullet has gone beyond the screen, if so return true
    public boolean clear(){

        if(xPos < 0 || xPos > GamePanel.SCREEN_WIDTH){
            return true;
        }
        if(yPos < 0 || yPos > GamePanel.SCREEN_HEIGHT){
            return true;
        }
        return false;

    }

    // Method used to check if the virus has collided with the viruses on the screen
    public boolean collision(Virus virus){
        double distance = GamePanel.calcDistance(this.getXpos() + (this.getSize() /2), this.getYpos() + (this.getSize() /2), virus.getXpos() + (virus.getSize()/2), virus.getYpos() + (virus.getSize()/2));
        if( distance <= this.getSize() /2 + virus.getSize()/4){
            return true;
        }
        return false;
    }

    public int getXpos() {
        return xPos;
    }

    public int getYpos() {
        return yPos;
    }

    public int getSize() {
        return size;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    // simple draw method which simply draws the bullet
    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, xPos, yPos, size, size, null);
        /*
        g.setColor(new Color(0x9ACEFF));
        g.fillOval(xPos, yPos, size/2,size/3);

         */

    }
}
