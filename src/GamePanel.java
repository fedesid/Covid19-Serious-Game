import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    Inventory inventory;

    int tempX;
    int tempY;
    public boolean[] keyList = new boolean[4];

    boolean moving = false;

    static ArrayList<Entity> entitiesOnScreen = new ArrayList<>();
    static ArrayList<Item> itemsOnScreen = new ArrayList<Item>();
    Player player;
    Gel gel;
    Virus virus;

    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x008C1C));
        this.setFocusable(true);
        this.startGame();
        //this.addKeyListener(new MyKeyAdapter());
    }

    public void startGame(){

        inventory = new Inventory(this);
        input = new InputHandler(this);
        random = new Random();
        player = new Player("F", random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT), 40,7, Color.red, input);
        gel = new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT), 15);
        virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), 30, random.nextInt(player.getSpeed()-2)+1);
        virus.setPlayer(player);

        Virus virus2 = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), 30, random.nextInt(player.getSpeed()-2)+1);
        virus2.setPlayer(player);

        for(int i=0; i<5; i++){
            Virus virus = new Virus(random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_WIDTH), 30, random.nextInt(player.getSpeed()-2)+1);
            virus.setPlayer(player);
            entitiesOnScreen.add(virus);

        }

        entitiesOnScreen.add(virus);
        entitiesOnScreen.add(virus2);
        System.out.println(itemsOnScreen.isEmpty());
        itemsOnScreen.add(gel);
        System.out.println(itemsOnScreen.isEmpty());


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

        /*
        for(int i=0; i<360; i+=90) {
            if(i <= 90){
                tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.playerSize);
                tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.playerSize);
            }else if(i <= 180){
                tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.playerSize);
                tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.playerSize);
            }else if(i <= 270){
                tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.playerSize);
                tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.playerSize);
            }else {
                tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.playerSize);
                tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.playerSize);
            }

            if( player.getYpos() < 0){
                System.out.println("Top Border");
                player.ypos++;
                player.direction = 'S';
                break;
            }
            if( player.getXpos() < 0){
                player.xpos++;
                System.out.println("Left Border");
                player.direction = 'S';
                break;
            }
            if( tempY > SCREEN_HEIGHT - player.playerSize){
                player.ypos--;
                System.out.println("Bottom Border");
                player.direction = 'S';
                break;
            }
            if( tempX > SCREEN_WIDTH){
                player.xpos--;
                System.out.println("Right Border");
                player.direction = 'S';

                break;
            }

        }

         */
    }

    public static double calcDistance(int x1, int y1, int x2, int y2){
        return Math.sqrt( Math.pow(Math.abs(x1-x2) , 2) + Math.pow( Math.abs( y1-y2) , 2) );
    }

    public void checkItem(){

        if( itemsOnScreen.size() != 0 ){
            for(Item item : itemsOnScreen){

                double distance = calcDistance(player.getXpos() + (player.getPlayerSize() /2), player.getYpos() + (player.getPlayerSize() /2), item.getItemXpos() + (item.getSize()/2), item.getItemYpos() + (item.getSize()/2));

                if( distance <= player.playerSize /2 + item.size/2){

                    System.out.println("CONTACT");
                    itemsOnScreen.add(new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT), 15));

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

        for(Entity virus : entitiesOnScreen){
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
