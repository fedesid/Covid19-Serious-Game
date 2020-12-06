import java.awt.*;

public abstract class Item extends Entity {
    String name;
    int quantity;
    int itemXpos;
    int itemYpos;
    int size;

    public Item(int quantity, int xpos, int ypos, int size){
        this.quantity = quantity;
        this.itemXpos = xpos;
        this.itemYpos = ypos;
        this.size = size;
    }

    public int getSize() {
        return size;
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
