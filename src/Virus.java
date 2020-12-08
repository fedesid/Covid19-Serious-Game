import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Virus extends Entity {

    Player player;
    int size;
    double distance;
    int speed;

    static BufferedImage sprite;

    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/virus.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Virus(int xpos, int ypos, int size, int speed) throws IOException {
        super(xpos, ypos);
        this.size=size;
        this.speed=speed;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public void chase(){

        //distance = GamePanel.calcDistance(xpos, ypos, player.getXpos(), player.getYpos());
        int dx = player.getXpos() - xpos;
        int dy = player.getYpos() - ypos;

        if(dx > 0){
            xpos += 1*speed;
        }else {
            xpos -= 1*speed;
        }

        if(dy > 0){
            ypos += 1*speed;
        }else{
            ypos -= 1*speed;
        }

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {


        g.drawImage(sprite, xpos, ypos, size, size, null);

        /*
        g.setColor(new Color(0x17000000, true));
        g.fillOval(xpos-100/speed,ypos-100/speed,200/speed,200/speed);
        g.setColor(new Color(0xFF0000));
        g.drawOval(xpos-100/speed,ypos-100/speed,200/speed,200/speed);

        g.setColor(new Color(0x6422BA));

        g.fillOval(xpos-size/2, ypos-size/2, size,size);

        for(int j=0; j<360; j+=45){
            g.fillOval((int) (Math.cos(Math.toRadians(j))*size + xpos), (int) (Math.sin(Math.toRadians(j))*size + ypos), 5, 5);
        }

         */

    }
}
