import java.awt.*;

public class Player extends Entity {

    String name;
    static final int PLAYER_SIZE = 40;
    Inventory inv;
    int xpos;
    int ypos;

    char direction;
    Color col;
    InputHandler input;

    public Player(String name, int xpos, int ypos, int speed, Color col, InputHandler input){
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.col = col;
        this.input = input;
    }

    //displacement x, displacement y
    public void move(int dx, int dy){

        this.xpos += dx;
        this.ypos += dy;
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

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(col);
        g.fillOval(this.xpos,this.ypos,PLAYER_SIZE, PLAYER_SIZE);
    }
}
