public abstract class Item {
    String name;
    int quantity;

    public Item(int quantity){
        this.quantity = quantity;
    }

    public void upQuantity(){
        quantity++;
    }

    public void downQuantity(){
        quantity--;
    }

}
