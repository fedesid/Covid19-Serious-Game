import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    public static final int SCREEN_WIDTH = 900;
    public static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 20;
    boolean running = false;
    Timer timer;
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
        random = new Random();

        player = new Player("F", random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT), 40,7, Color.red, input);

        for(int i=0; i<5; i++){
            Virus virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), 100, random.nextInt(player.getSpeed()-2)+1);
            virus.setPlayer(player);
            virusOnScreen.add(virus);

        }

        gel = new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));

        itemsOnScreen.add(gel);
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
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
        return Math.asin( (y1-y2)/hyp );
    }
    public static double calcAngleCos(int x1, int y1, int x2, int y2){
        double hyp = calcDistance(x1, y1, x2, y2);
        return Math.acos( (x1-x2)/hyp )-Math.PI/2;
    }

    public static double calcDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt( Math.pow(Math.abs(x1-x2) , 2) + Math.pow( Math.abs( y1-y2) , 2) );
    }

    public void checkItem(){

        if( itemsOnScreen.size() != 0 ){
            for(Item item : itemsOnScreen){

                double distance = calcDistance(player.getXpos() + (player.getPlayerSize() /2), player.getYpos() + (player.getPlayerSize() /2), item.getItemXpos() + (item.getSize()/2), item.getItemYpos() + (item.getSize()/2));

                if( distance <= player.getPlayerSize() /2){

                    System.out.println("CONTACT");
                    itemsOnScreen.add(new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT)));

                    switch (item.getClass().toString())
                    {
                        case "class Gel" :
                            inventory.items[0] += 1;
                    }
                    itemsOnScreen.remove(item);
                    break;

                }

            }
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void draw(Graphics g){
        player.draw(g);
        inventory.draw(g);

        for(Bullet bullet : bulletsOnScreen){
            bullet.move();
            bullet.draw(g);

            if( bullet.clear() ){ // deleting the bullets after they leave the screen
                GamePanel.bulletsOnScreen.remove(bullet);
                break;
            }
        }

        for(Virus virus : virusOnScreen){
            virus.chase();
            virus.draw(g);
        }

        for(Item item : itemsOnScreen){
            item.draw(g);
        }

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(running){
            player.movePlayer();

        }
        checkCollision();
        checkItem();
        repaint();
    }
}