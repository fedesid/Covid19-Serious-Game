import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {


    public static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 1;
    boolean running = false;
    Timer timer;
    Random random;
    int tempX;
    int tempY;

    static ArrayList<Item> itemsOnScreen = new ArrayList<>();
    Player player;
    Gel gel;


    public GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(0x008C1C));
        this.setFocusable(true);
        this.startGame();
        this.addKeyListener(new MyKeyAdapter());
    }

    public void startGame(){
        random = new Random();
        player = new Player("Hi", random.nextInt(SCREEN_WIDTH),random.nextInt(SCREEN_HEIGHT),1, Color.red);
        gel = new Gel(1, random.nextInt(SCREEN_WIDTH), random.nextInt(SCREEN_HEIGHT));

        itemsOnScreen.add(gel);

        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void checkCollision(){

        for(int i=0; i<4; i+=90){
            tempX = (int) (player.xpos + Math.cos(i)) + player.PLAYER_SIZE;
            tempY = (int) (player.ypos + Math.sin(i)) + player.PLAYER_SIZE;

            if( tempY <= 38){
                System.out.println("Top Border");
                player.ypos++;
                player.direction = 'S';
                break;
            }
            if( tempX <= 38){
                player.xpos++;
                System.out.println("Left Border");
                player.direction = 'S';
                break;
            }
            if( tempY >= SCREEN_HEIGHT){
                player.ypos--;
                System.out.println("Bottom Border");
                player.direction = 'S';
                break;
            }
            if( tempX >= SCREEN_WIDTH){
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
                tempX = (int) (player.xpos + Math.cos(i)) + player.PLAYER_SIZE;
                tempY = (int) (player.ypos + Math.sin(i)) + player.PLAYER_SIZE;

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
            player.move(1,1);
            checkCollision();
            checkItem();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            System.out.println(e.getKeyChar());

            switch (e.getKeyCode()){
                case KeyEvent.VK_W:
                    if(player.direction == 'D'){
                        player.direction = 'S';
                        break;
                    }
                    player.direction = 'U';
                    break;
                case KeyEvent.VK_S:
                    if(player.direction == 'U'){
                        player.direction = 'S';
                        break;
                    }
                    player.direction = 'D';
                    break;
                case KeyEvent.VK_A:
                    if(player.direction == 'R'){
                        player.direction = 'S';
                        break;
                    }
                    player.direction = 'L';
                    break;
                case KeyEvent.VK_D:
                    if(player.direction == 'L'){
                        player.direction = 'S';
                        break;
                    }
                    player.direction = 'R';
                    break;
            }



        }
    }
}
