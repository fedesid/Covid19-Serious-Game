public abstract class Item extends Entity {
    String name;
    int quantity;
    int itemXpos;
    int itemYpos;

    public Item(int quantity, int xpos, int ypos){
        this.quantity = quantity;
        this.itemXpos = xpos;
        this.itemYpos = ypos;
    }

    public void upQuantity(){
        quantity++;
    }

    public void downQuantity(){
        quantity--;
    }

}
