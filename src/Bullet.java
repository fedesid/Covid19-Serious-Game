import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
    int xEnd;
    int yEnd;
    double xt;
    double yt;

    int size = 20;

    int speed=10;

    public Bullet(int xBegin, int yBegin, int xEnd, int yEnd){
        this.xPos = xBegin - size;
        this.yPos = yBegin - size;
        this.yBegin = yBegin;
        this.xEnd = xEnd;
        this.yEnd = yEnd;
        this.yt = -GamePanel.calcAngleSin(xPos, yPos, xEnd, yEnd);
        this.xt = GamePanel.calcAngleCos(xPos, yPos, xEnd, yEnd);
        System.out.println(xt + " " + yt);
    }

    public void move(){

        xPos += (int) ((xt)*(speed));
        yPos += (int) ((yt)*(speed));

    }

    public boolean clear(){

        if(xPos < 0 || xPos > GamePanel.SCREEN_WIDTH){
            return true;
        }
        if(yPos < 0 || yPos > GamePanel.SCREEN_HEIGHT){
            return true;
        }
        return false;

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, xPos, yPos, size, size, null);
        /*
        g.setColor(new Color(0x9ACEFF));
        g.fillOval(xPos, yPos, size/2,size/3);

         */

    }
}
