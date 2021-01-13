import java.awt.*;
import java.util.ArrayList;

public class Inventory {
    //static ArrayList<Integer> items = new ArrayList<int>();
    static ItemCount[] items;
    GamePanel gp;
    InputHandler input;
    int quantity;

    int width = GamePanel.SCREEN_WIDTH/3;
    int height = GamePanel.SCREEN_HEIGHT/10;
    int xpos = GamePanel.SCREEN_WIDTH/3;
    int ypos = GamePanel.SCREEN_HEIGHT-10 - GamePanel.SCREEN_HEIGHT/10;

    public Inventory(GamePanel gp){

        ItemCount gel = new ItemCount();
        ItemCount mask = new ItemCount();
        ItemCount vaccine = new ItemCount();

        this.gp = gp;
        items = new ItemCount[]{gel, mask, vaccine};

    }

    public void linkInput(InputHandler input){
        this.input = input;
    }

    public static void clear(){

        for(int i=0; i<items.length; i++){

            items[i].setCurrentQuantity(0);
            items[i].setTotalQuantity(0);

        }

    }

    public void paintComponent(Graphics g) {

    }

    public void draw(Graphics g) {

        g.setColor(Color.white);

        g.drawRect(xpos, ypos , width, height);

        for(int i=width/3; i<width; i+=width/3){
            g.drawLine( xpos+i, ypos, xpos+i, ypos+height);
        }

        Font font = new Font("Corbel", Font.PLAIN, 35);

        Menus.drawCenteredString("Gel", width/3, xpos*2, height, (int) (ypos*2-height*1.4), font, Color.white, g);
        Menus.drawCenteredString("Mask", width/3, xpos*2 + 2*(width/3), height, (int) (ypos*2-height*1.4), font, Color.white, g);
        Menus.drawCenteredString("Vaccine", width/3, xpos*2 + 4*(width/3), height, (int) (ypos*2-height*1.4), font, Color.white, g);

        int j= 0;
        for(int i=width/3-width/3/2; i<width; i+=width/3){

            Menus.drawCenteredString(String.valueOf(items[j]), width/3, xpos*2 + j*(2*(width/3)), height, ypos*2, font, Color.white, g);

            //g.drawString(String.valueOf(items[j]),xpos+i, ypos+height/2);
            j++;
        }

        g.setColor(new Color(0x460041FF, true));
        if(input.one.isPressed()){
            g.fillRect(xpos, ypos, width/3, height);
        }
        if(input.two.isPressed()){
            g.fillRect(xpos+width/3, ypos, width/3, height);
        }
        if(input.three.isPressed()){
            g.fillRect(xpos+width/3*2, ypos, width/3, height);
        }

    }

}
