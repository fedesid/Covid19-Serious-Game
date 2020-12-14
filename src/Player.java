import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player extends Entity {

    String name;
    int playerSize;

    int xpos;
    int ypos;
    int speed;

    boolean maskOn = false;

    Inventory inv;
    InputHandler input;
    Country country;

    static BufferedImage mask;
    static BufferedImage hand;
    static BufferedImage gel, vaccine;

    static {
        try {
            mask = ImageIO.read(new File("src/Sprites/Entities/maskOn.png"));
            hand = ImageIO.read(new File("src/Sprites/Entities/hand2.png"));
            gel = ImageIO.read(new File("src/Sprites/Entities/gel.png"));
            vaccine = ImageIO.read(new File("src/Sprites/Entities/vaccine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    char direction;
    Color col;

    public Player(String name, int xpos, int ypos, int size, int speed, Color col, InputHandler input) throws IOException {
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.playerSize = size;
        this.col = col;
        this.input = input;
        this.speed = speed;
    }

    public void linkInput(InputHandler input){
        this.input = input;
    }

    public void linkInventory(Inventory inv){
        this.inv = inv;
    }

    public void linkCountry(Country country){
        this.country = country;
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

    public void wearMask(boolean bl){
        this.maskOn = bl;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {

        if(maskOn){

            g.setColor(new Color(0x3EFFFFFF, true));
            g.fillRect(0,0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        }

        g.drawImage(country.flag, xpos, ypos, playerSize, playerSize, null);

        if(maskOn){

            g.drawImage(mask, xpos, ypos+10, playerSize, playerSize, null);

        }

        g.drawImage(hand, xpos-10, ypos+20, playerSize, playerSize, null);

        if(input.one.isPressed() && Inventory.items[0].compareTo(0) > 0){
            g.drawImage(gel, xpos+5, ypos+20, playerSize/2, playerSize/2, null);

        }else if(input.two.isPressed() && Inventory.items[1].compareTo(0) > 0){
            g.drawImage(mask, xpos+5, ypos+20, playerSize/2, playerSize/2, null);

        }else if(input.three.isPressed() && Inventory.items[2].compareTo(0) > 0){
            g.drawImage(vaccine, xpos+5, ypos+20, playerSize/2, playerSize/2, null);
        }

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
