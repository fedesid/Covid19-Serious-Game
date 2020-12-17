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
        /*
        items.add(Gel.getCurrentQuantity());
        items.add(Mask.getCurrentQuantity());
        items.add(Vaccine.getCurrentQuantity());
         */
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

        g.setFont(new Font("Lato", Font.ITALIC, 25));
        g.drawString("Gel", xpos, ypos);
        g.drawString("Mask", xpos + width/3, ypos);
        g.drawString("Vaccine", xpos + 2*(width/3), ypos);

        int j= 0;
        for(int i=width/3-width/3/2; i<width; i+=width/3){
            g.drawString(String.valueOf(items[j]),xpos+i, ypos+height/2);
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
