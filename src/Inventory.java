import java.awt.*;

public class Inventory {
    int[] items;
    GamePanel gp;
    int quantity;

    int width = GamePanel.SCREEN_WIDTH/3;
    int height = GamePanel.SCREEN_HEIGHT/10;
    int xpos = GamePanel.SCREEN_WIDTH/3;
    int ypos = GamePanel.SCREEN_HEIGHT-10 - GamePanel.SCREEN_HEIGHT/10;

    public Inventory(GamePanel gp){

        this.gp = gp;
        items = new int[]{0, 0, 0};

    }

    public void upQuantity(){
        quantity++;
    }

    public void downQuantity(){
        quantity--;
    }


    public void paintComponent(Graphics g) {

    }

    public void draw(Graphics g) {
        g.setColor(Color.black);

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
    }

}
