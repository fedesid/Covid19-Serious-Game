import java.awt.*;

public class Player extends Entity {

    String name;
    static final int PLAYER_SIZE = 40;
    int initialPopulation;
    int infectedPopulation;
    int deadPopulation;
    Inventory inv;
    int xpos;
    int ypos;
    char direction;
    Color col;

    public Player(String name, int xpos, int ypos, int speed, Color col){
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.col = col;
    }

    //displacement x, displacement y
    public void move(int dx, int dy){

        switch (direction){
            case 'U':
                ypos += -dy;
                break;
            case 'D':
                ypos += dy;
                break;
            case 'L':
                xpos += -dx;
                break;
            case 'R':
                xpos += dx;
                break;
            case 'X':
                xpos += dx;
                ypos += -dy;
            case 'Y':
                xpos += dx;
                ypos += dy;
            case 'Z':
                xpos += -dx;
                ypos += dy;
            case 'N':
                xpos += -dx;
                ypos += -dy;
            case 'S':
                break;
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
        g.fillOval(xpos,ypos,PLAYER_SIZE, PLAYER_SIZE);
    }
}
