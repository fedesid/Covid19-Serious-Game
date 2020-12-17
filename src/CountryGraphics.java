import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CountryGraphics {

    static ArrayList<CountryGraphics> countryGraphicsList = new ArrayList<>();

    BufferedImage flag;
    String name;
    int xpos, xposOpposite, ypos, yposOpposite, size;

    boolean isSelected = false;

    public CountryGraphics(String name, BufferedImage flag, int xpos, int xposOpposite, int ypos, int yposOpposite, int size) {
        this.name = name;

        this.xpos = xpos;
        this.xposOpposite = xposOpposite;
        this.ypos = ypos;
        this.yposOpposite = yposOpposite;
        this.size = size;

        this.flag = flag;

    }

    public String getName() {
        return name;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public int getSize() {
        return size;
    }

    public int getOppositeXpos() {
        return xposOpposite;
    }

    public int getOppositeYpos() {
        return yposOpposite;
    }

    public void toggleSelection(){
        for(Iterator<CountryGraphics> countryGraphicsIterator = CountryGraphics.countryGraphicsList.iterator(); countryGraphicsIterator.hasNext();){
            CountryGraphics countryGraphics = countryGraphicsIterator.next();

            countryGraphics.isSelected = false;
        }
        this.isSelected = true;
    }

    public boolean checkClick(int mouseX, int mouseY){

        if( ( mouseX > this.getXpos() && mouseX < this.getOppositeXpos() ) && (mouseY > this.getYpos() && mouseY < this.getOppositeYpos()) ){
            return true;
        }
        return false;
    }

    public void drawSelection(Graphics g){

        g.setColor(new Color(0xFF0000FF, true));
        //g.fillOval( ((this.getOppositeXpos() - this.getXpos())/5)+this.getXpos() , this.getYpos(), this.getSize()*2, this.getSize()*2);

        g.setColor(new Color(0xC900FF02, true));
        //g.drawRect(this.getXpos(), this.getYpos(), this.getOppositeXpos()-this.getXpos(), this.getOppositeYpos()-this.getYpos());

        if(this.isSelected){
            Menus.drawCenteredCircle( (int) (size*2.2), this.getXpos(), this.getOppositeXpos(), this.getYpos(), this.getOppositeYpos(), g);
        }

        Menus.drawCenteredImage(flag, size, this.getOppositeXpos(), this.getXpos(), this.getOppositeYpos(), this.getYpos(), g);
        Menus.drawCenteredString(this.getName(), this.getOppositeXpos(), this.getXpos(), this.getOppositeYpos()+this.getSize()*2/3, this.getYpos()+this.getSize()*2/3, new Font("LATO", Font.ITALIC, 30), new Color(0xFFFFFF), g);

    }

}