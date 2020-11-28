import java.awt.*;

public class Gel extends Item {

    public Gel(int quantity, int xpos, int ypos){
        super(quantity, xpos, ypos);
        name = "Gel";
    }

    @Override
    public void paintComponent(Graphics g) {

    }

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(0x6EFFFF));
        g.fillRect(itemXpos, itemYpos, 10, 10);
    }
}
