import java.awt.*;

public abstract class Item extends Entity {
    String name;
    int quantity;
    int itemXpos;
    int itemYpos;
    public static final int SIZE = 50;

    public Item(int quantity, int xpos, int ypos){
        this.quantity = quantity;
        this.itemXpos = xpos;
        this.itemYpos = ypos;
    }

    public int getSize() {
        return SIZE;
    }

    public int getItemXpos() {
        return itemXpos;
    }

    public int getItemYpos() {
        return itemYpos;
    }

    public abstract void draw(Graphics g);

    public void upQuantity(){
        quantity++;
    }

    public void downQuantity(){
        quantity--;
    }

}
