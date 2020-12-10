import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Virus extends Entity {

    Player player;
    double distance;
    int speed;
    int range;
    int initHealth;
    int currentHealth;

    static BufferedImage sprite, sprite2;

    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/virus.png"));
            sprite2 = ImageIO.read(new File("src/Sprites/Entities/stink2.png"));
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public Virus(int xpos, int ypos, int speed) throws IOException {
        super(xpos, ypos);
        this.speed=speed;
        setSize();
        setRange();
        setHealth();
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

    public void setSize(){
        this.size = -this.speed*20 + 170;
    }

    public void setRange() {
        this.range = (size/speed)+size;
    }

    public void setHealth(){
        this.initHealth = this.range/2;
        this.currentHealth = this.initHealth;
    }

    public void takeDamage(int damage, Iterator<Virus> it){
        this.currentHealth -= damage;

        if(this.currentHealth <= 0){
            it.remove();
        }
/*
        if(this.currentHealth <= 0){
            GamePanel.virusOnScreen.remove(this);
        }
        */
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(sprite2, xpos-size/(2*speed),ypos-size/(2*speed),range,range, null);
        //g.setColor(new Color(0x17000000, true));
        //g.fillOval(xpos-size/(2*speed),ypos-size/(2*speed),range,range);
        //g.setColor(new Color(0xFF0000));
        //g.drawOval(xpos-size/(2*speed),ypos-size/(2*speed),range,range);
        g.drawImage(sprite, xpos, ypos, size, size, null);

        if(this.currentHealth != initHealth){
            g.setColor(Color.red);
            g.fillRect(xpos, ypos, this.initHealth, 5);

            g.setColor(Color.green);
            g.fillRect(xpos, ypos, this.currentHealth, 5);
        }


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
