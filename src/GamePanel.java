import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class GamePanel extends JPanel implements ActionListener {

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 800;
    static final int DELAY = 20;
    boolean running = false;
    Timer timer;
    Timer spawnTimer;
    Random random;
    InputHandler input;
    MouseHandler mouse;
    Inventory inventory;

    int tempX;
    int tempY;
    public boolean[] keyList = new boolean[4];

    boolean moving = false;

    static ArrayList<Bullet> bulletsOnScreen = new ArrayList<>();
    static ArrayList<Virus> virusOnScreen = new ArrayList<>();
    static ArrayList<Item> itemsOnScreen = new ArrayList<Item>();
    Player player;
    Gel gel;
    Virus virus;

    static BufferedImage bg;

    static {
        try {
            bg = ImageIO.read(new File("src/Sprites/Background/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GamePanel() throws IOException {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x008C1C));
        this.setFocusable(true);
        this.startGame();
        //this.addKeyListener(new MyKeyAdapter());
    }

    public void startGame() throws IOException {

        inventory = new Inventory(this);
        input = new InputHandler(this);
        mouse = new MouseHandler(this);

        inventory.linkInput(input);
        mouse.linkInput(input);
        random = new Random();

        player = new Player("F", random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT), 40,7, Color.red, input);
        player.linkInput(input);
        player.linkInventory(inventory);

        for(int i=0; i<5; i++){
            Virus virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), random.nextInt(player.getSpeed()-2)+1);
            virus.setPlayer(player);
            virusOnScreen.add(virus);
            Virus.totalNumberOfViruses++;

        }

        //gel = new Gel(1, random.nextInt(SCREEN_WIDTH-20)+10, random.nextInt(SCREEN_HEIGHT-20)+10);
        //Mask mask = new Mask(1, random.nextInt(SCREEN_WIDTH-20)+10, random.nextInt(SCREEN_HEIGHT-20)+10);
        //Vaccine vaccine = new Vaccine(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10);

        //itemsOnScreen.add(gel);
        //itemsOnScreen.add(mask);
        //itemsOnScreen.add(vaccine);

        running = true;
        //spawnTimer = new Timer();

        timer = new Timer(DELAY, this);
        timer.start();

        java.util.Timer t = new java.util.Timer();
        t.schedule(new Spawn("Virus", this), 0, 5000);

        enemySpawner.start();
        gelSpawner.start();
        maskSpawner.start();
        vaccineSpawner.start();
    }

    public void checkCollision(){

        double distance;

        // top border
        if(player.getYpos() < 0){
            player.setYpos(0);
        }

        // right border
        distance = calcDistance(player.getXpos(), player.getYpos(), SCREEN_WIDTH, player.getYpos());
        if(distance < player.getPlayerSize()){
            player.setXpos(SCREEN_WIDTH - player.getPlayerSize());
        }

        // bottom border
        distance = calcDistance(player.getXpos(), player.getYpos(), player.getXpos(), SCREEN_HEIGHT);
        if(distance < player.getPlayerSize()){
            player.setYpos(SCREEN_HEIGHT - player.getPlayerSize());
        }

        // left border
        if(player.getXpos() < 0){
            player.setXpos(0);
        }
    }

    public static double calcAngleSin(int x1, int y1, int x2, int y2){
        double hyp = calcDistance(x1, y1, x2, y2);
        return Math.toDegrees( Math.asin( (y1-y2)/hyp ) ) ;
    }
    public static double calcAngleCos(int x1, int y1, int x2, int y2){
        double hyp = calcDistance(x1, y1, x2, y2);
        return Math.toDegrees( Math.acos( (x1-x2)/hyp )-Math.PI/2 ) ;
    }

    public static double calcDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt( Math.pow(Math.abs(x1-x2) , 2) + Math.pow( Math.abs( y1-y2) , 2) );
    }

    public void checkItem(){

        if( itemsOnScreen.size() != 0 ){
            for(Item item : itemsOnScreen){

                //TODO extrapolate this method (check distance between item and player) you could create a method inside the Item abstract class
                if( item.collision(player) ){

                    System.out.println("CONTACT");
                    System.out.println(item.getClass().toString());

                    //TODO change the way the inventory works. Use classes instead of indexes i.e. Class mask, class gel, class vaccine

                    if(item.getClass().toString().equals("class Gel")){
                        Inventory.items[0].upCurrentCount(5);
                        //itemsOnScreen.add(new Gel(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }else if(item.getClass().toString().equals("class Mask")){
                        Inventory.items[1].upCurrentCount(1);
                        //itemsOnScreen.add(new Mask(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }else if(item.getClass().toString().equals("class Vaccine")){
                        Inventory.items[2].upCurrentCount(1);
                        //itemsOnScreen.add(new Vaccine(1, random.nextInt(SCREEN_WIDTH-30)+10, random.nextInt(SCREEN_HEIGHT-30)+10));

                    }
                    itemsOnScreen.remove(item);
                    break;

                }

            }
        }

    }

    public void checkVirus(){

        if(virusOnScreen.size() != 0){
            int c=0;
            for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext();){
                Virus virus = virusIterator.next();

                if( virus.collision(player) ){
                    c++;
                }

            }

            if(c!=0){
                Virus.isInContact = true;
            }else {
                Virus.isInContact = false;
            }

        }

    }

    public void checkBulletCollision(){

        // list that contains all the bullets that did collide with a virus,
        // I keep track of this so that I will be able to remove them from the list of bullets that did not hit
        Collection<Bullet> tempBullet = new ArrayList<>();


        for(Iterator<Bullet> bulletIterator = GamePanel.bulletsOnScreen.iterator(); bulletIterator.hasNext();){
            Bullet bullet = bulletIterator.next();

            for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext(); ){
                Virus virus = virusIterator.next();

                if(bullet.collision(virus)){

                    virus.takeDamage(bullet.getDamage(), virusIterator);

                    tempBullet.add(bullet);

                }

            }

        }

        bulletsOnScreen.removeAll(tempBullet);

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
        draw(g);

    }

    public void draw(Graphics g){

        for(Virus virus : virusOnScreen){
            virus.chase();
            virus.draw(g);
        }

        for(Item item : itemsOnScreen){
            item.draw(g);
        }
        for(Bullet bullet : bulletsOnScreen){
            bullet.move();
            bullet.draw(g);

            if( bullet.clear() ){ // deleting the bullets after they leave the screen
                GamePanel.bulletsOnScreen.remove(bullet);
                break;
            }
        }

        player.draw(g);

        if(Virus.isInContact && !player.maskOn){ // Show the user that they are taking damage
            g.setColor(new Color(0x28FF0000, true));
            g.fillRect(0,0,SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        inventory.draw(g);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {


        if(running){
            player.movePlayer();
        }

        //spawnTimer.schedule(new Spawn("Virus", this));


        checkCollision();
        checkItem();
        checkVirus();
        checkBulletCollision();
        repaint();
    }

    Thread enemySpawner = new Thread(new Runnable() {
        int nSpawns = 1;
        public void run() {
            while(true) {

                try{

                    if(nSpawns%2==0){
                        System.out.println("BLOCK");
                        for(int i=0; i<3; i++){
                                virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), random.nextInt(player.getSpeed()-2)+1);
                                virus.setPlayer(player);
                                virusOnScreen.add(virus);
                                Virus.totalNumberOfViruses++;
                        }

                    }else {

                        virus = new Virus(random.nextInt(100)+GamePanel.SCREEN_WIDTH, random.nextInt(100)+GamePanel.SCREEN_HEIGHT, random.nextInt(player.getSpeed()-2)+1);
                        virus.setPlayer(player);
                        GamePanel.virusOnScreen.add(virus);
                        nSpawns++;
                        Virus.totalNumberOfViruses++;

                    }

                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(9000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread gelSpawner = new Thread(new Runnable() {
        public void run() {
            while(true) {
                Gel gel = new Gel(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                GamePanel.itemsOnScreen.add(gel);

                try {
                    Thread.sleep(8000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread maskSpawner = new Thread(new Runnable() {
        int nSpawns=1;
        public void run() {

            while(true) {

                Mask mask = new Mask(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                GamePanel.itemsOnScreen.add(mask);
                nSpawns++;
                try {
                    Thread.sleep(18000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    Thread vaccineSpawner = new Thread(new Runnable() {
        public void run() {
            while(true) {
                Vaccine vaccine = new Vaccine(random.nextInt(SCREEN_WIDTH-100)+50, random.nextInt(SCREEN_HEIGHT-100)+50);

                GamePanel.itemsOnScreen.add(vaccine);

                try {
                    Thread.sleep(45000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

}