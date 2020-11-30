import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    public static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 2;
    boolean running = false;
    Timer timer;
    Random random;
    InputHandler input;
    int tempX;
    int tempY;
    public boolean[] keyList = new boolean[4];

    boolean moving = false;

    static ArrayList<Item> itemsOnScreen = new ArrayList<>();
    Player player;
    Gel gel;


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x008C1C));
        this.setFocusable(true);
        this.startGame();
        //this.addKeyListener(new MyKeyAdapter());
    }

    public void startGame(){
        input = new InputHandler(this);
        random = new Random();
        player = new Player("F", random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT),1, Color.red, input);
        gel = new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));

        System.out.println(gel.itemXpos + " " + gel.itemYpos);
        itemsOnScreen.add(gel);

        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void checkCollision(){

        for(int i=0; i<360; i+=90){
            if(i < 90){
                tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
            }else if(i < 180){
                tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
            }else if(i < 270){
                tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
            }else {
                tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
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
            if( tempY > SCREEN_HEIGHT){
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
    }

    public void checkItem(){
        for(Item item : itemsOnScreen){

            for(int i=0; i<360; i++) {
                if(i < 90){
                    tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                    tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
                }else if(i < 180){
                    tempX = (player.xpos + (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                    tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
                }else if(i < 270){
                    tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                    tempY = (player.ypos + (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
                }else {
                    tempX = (player.xpos - (int) Math.cos(Math.toRadians(i)) *player.PLAYER_SIZE);
                    tempY = (player.ypos - (int) Math.sin(Math.toRadians(i)) *player.PLAYER_SIZE);
                }

                //(System.out.println(tempX + " " + tempY);
                if( (tempX == item.itemXpos) && (tempY == item.itemYpos) ){
                    System.out.println(item.name);
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
        gel.draw(g);

    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(running){

            player.movePlayer();
            checkCollision();
            checkItem();
        }
        repaint();
    }
}
