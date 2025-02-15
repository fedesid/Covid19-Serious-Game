import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

// TODO virus that sneezes at you and does not move. it can spawn near the sides of the map
// TODO vaccine deals "splash" damage. the closer the virus the higher the damage. further the virus is the less damage it takes (close to nothing)
/*
    The Virus class represents the concept of the virus in my game
    The virus has a speed, range, health
    By looking at the constructor it can be deduced that the size, range, health, and damage of the virus all depend on the speed
    A fast virus will be small, deal a lot of damage but it will not have a lot of health
    A slower virus will be bigger, deal not much damage, but it will have a lot of health
 */
public class Virus extends Entity {

    static int totalNumberOfViruses = 0;
    static int totalNumberOfVirusesKilled = 0;
    static int totalNumberOfContacts = 0;

    Player player;
    double distance;

    boolean contact = false;
    boolean prevContact = false;

    static boolean isInContact = false;

    int speed;
    int range;
    int initHealth;
    int currentHealth;
    int damage;
    int imgNum;
    Random random = new Random();

    int cc = 0;

    BufferedImage sprite, sprite2;

    public Virus(int xpos, int ypos, int speed) throws IOException {
        super(xpos, ypos);
        this.speed=speed;
        setSize();
        setRange();
        setHealth();
        setDamage();

        try {
            this.imgNum = random.nextInt(5)+1;
            sprite = ImageIO.read(new File("src/Sprites/Entities/virus.png"));
            sprite2 = ImageIO.read(new File("src/Sprites/Entities/stink"+speed+".png"));
        } catch (IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    // This method moves the virus towards the player
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

    // Checks if the virus has collided with the player
    public boolean collision(Player player){
        double distance = GamePanel.calcDistance(player.getXpos() + (player.getPlayerSize() /2), player.getYpos() + (player.getPlayerSize() /2), this.getXpos() + (this.getSize()/2), this.getYpos() + (this.getSize()/2));
        if( distance <= player.getPlayerSize() /2 + this.getRange()/2){
            this.setContact(true);
            upCollisions();
            return true;
        }
        this.setContact(false);
        return false;
    }

    // I keep track of the number of times that the player has made contact with the virus
    public void upCollisions(){

        if(!prevContact && contact){
            if(!player.maskOn){
                totalNumberOfContacts++;

            }
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

    public void setDamage(){
        this.damage = this.size*100;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public static void clear(){
        totalNumberOfContacts = 0;
        totalNumberOfViruses = 0;
        totalNumberOfVirusesKilled = 0;
    }

    public void setContact(boolean contact) {

        this.prevContact = this.contact;
        this.contact = contact;
    }

    public boolean isContact() {
        return contact;
    }

    public void takeDamage(int damage, Iterator<Virus> it){
        this.currentHealth -= damage;

        if(this.currentHealth <= 0){
            Virus.totalNumberOfVirusesKilled++;
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

        //Debugging purposes
        /*
        g.setColor(Color.green);
        g.drawString( String.valueOf(totalNumberOfViruses), 50, 50 );
        g.drawString( String.valueOf(totalNumberOfVirusesKilled), 50, 60 );
        g.drawString( String.valueOf(totalNumberOfContacts), 50, 70 );
         */

        g.drawImage(sprite2, xpos-size/(2*speed),ypos-size/(2*speed),range,range, null);
        //g.setColor(new Color(0x17000000, true));
        //g.fillOval(xpos-size/(2*speed),ypos-size/(2*speed),range,range);
        //g.setColor(new Color(0xFF0000));
        //g.drawOval(xpos-size/(2*speed),ypos-size/(2*speed),range,range);
        g.drawImage(sprite, xpos, ypos, size, size, null);

        // Virus health bar
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
