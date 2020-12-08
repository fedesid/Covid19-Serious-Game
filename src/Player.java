import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    String name;
    int playerSize;
    Inventory inv;
    int xpos;
    int ypos;
    int speed;

    static BufferedImage sprite;

    static {
        try {
            sprite = ImageIO.read(new File("src/Sprites/Country/italy.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    char direction;
    Color col;
    InputHandler input;

    public Player(String name, int xpos, int ypos, int size, int speed, Color col, InputHandler input) throws IOException {
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.playerSize = size;
        this.col = col;
        this.input = input;
        this.speed = speed;
    }

    //displacement x, displacement y
    public void move(int dx, int dy){

        this.xpos += dx*speed;
        this.ypos += dy*speed;
    }

    public void movePlayer(){

        if(input.up.isPressed()){
            move(0,-1);
        }
        if(input.down.isPressed()){
            move(0,1);
        }
        if(input.left.isPressed()){
            move(-1,0);
        }
        if(input.right.isPressed()){
            move(1,0);
        }

    }

    public int getXpos(){
        return this.xpos;
    }
    public int getYpos(){
        return this.ypos;
    }
    public int getPlayerSize(){return this.playerSize;}
    public int getSpeed(){return this.speed;}

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(sprite, xpos, ypos, playerSize, playerSize, null);

/*
        g.setColor(new Color(0));
        for(int i=0; i<1; i++){
            g.drawOval(this.xpos,this.ypos, playerSize+1, playerSize+1);

        }

        g.setColor(col);
        g.fillOval(this.xpos,this.ypos, playerSize, playerSize);

 */
    }
}
