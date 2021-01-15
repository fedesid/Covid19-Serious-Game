import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

// Menus is an abstract class
/*
    This class represents the concept of Menus
    Every Menu in my game (except the gameplay "menu") has buttons
    Thus I every class that extends Menus will have access to a Button array list
    which will contain all of the buttons needed in that menu
 */
public abstract class Menus {

    ArrayList<Button> buttons = new ArrayList<>();
    GamePanel gp;

    public Menus(){}

    public Menus(GamePanel gp){
        this.gp = gp;
    }

    public void paintComponent(Graphics g) {

    }

    // This draw method draws all of the buttons stored in the button list
    public void draw(Graphics g){

        g.setColor(Color.white);

        for(Iterator<Button> buttonIterator = buttons.iterator(); buttonIterator.hasNext();){
            Button button = buttonIterator.next();

            g.setFont(new Font("LATO", Font.BOLD, button.getTextSize()));

            g.drawRect(button.getXpos(), button.getYpos(), button.getWidth(), button.getHeight());
            drawCenteredString(button.getName(), button.getOppositeXpos(), button.getXpos(), button.getOppositeYpos(), button.getYpos(), new Font("LATO", Font.BOLD, button.getTextSize()), Color.white, g);

        }

    }

    // Method used to print and align a string on the 2d plane. It also prints the string in the middle of two coordinates
    // This is how I print many things in the game, and how the text looks align all the time
    public static void drawCenteredString(String s, int w, int xp, int h, int yp, Font font, Color color, Graphics g) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = ( (w-xp) - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + ( (h-yp) - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x+xp, y+yp);
    }

    // Prints an image aligned based on coordinates
    public static void drawCenteredImage(BufferedImage sprite, int size, int w, int xp, int h, int yp, Graphics g){
        int x = ( (w-xp) - size)/2;
        int y = ( ( (h-yp) - size )/2 );
        g.drawImage(sprite, x+xp, y+yp, size, size, null);
    }

    // Prints a circle aligned based on coordinates
    public static void drawCenteredCircle(int size, int w, int xp, int h, int yp, Graphics g){
        int x = ( (w-xp) - size)/2;
        int y = ( ( (h-yp) - size )/2 );
        g.setColor(new Color(0xA325D7FF, true));
        g.fillOval(x+xp, y+yp, size, size);
    }
}
