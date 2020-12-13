import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

// TODO virus that sneezes at you and does not move. it can spawn near the sides of the map
// TODO vaccine deals "splash" damage. the closer the virus the higher the damage. further the virus is the less damage it takes (close to nothing)

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

    int cc = 0;

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
        setDamage();
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

    public int getRange() {
        return range;
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

        g.setColor(Color.green);
        g.drawString( String.valueOf(totalNumberOfViruses), 50, 50 );
        g.drawString( String.valueOf(totalNumberOfVirusesKilled), 50, 60 );
        g.drawString( String.valueOf(totalNumberOfContacts), 50, 70 );

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
