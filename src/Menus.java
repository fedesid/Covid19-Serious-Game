import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Menus {

    ArrayList<Button> buttons = new ArrayList<>();
    GamePanel gp;

    public Menus(){}

    public Menus(GamePanel gp){
        this.gp = gp;
    }

    public void paintComponent(Graphics g) {

    }

    public void draw(Graphics g){

        g.setColor(Color.white);

        for(Iterator<Button> buttonIterator = buttons.iterator(); buttonIterator.hasNext();){
            Button button = buttonIterator.next();

            g.setFont(new Font("LATO", Font.BOLD, button.getTextSize()));

            g.drawRect(button.getXpos(), button.getYpos(), button.getWidth(), button.getHeight());
            g.drawString(button.getName(), button.getXpos()+ button.getXoffset(), button.getYpos()+button.getYoffset());

        }

    }
}
