// I needed a way of keeping track of the number of items the player had
// This class does just that
/*
    ItemCount objects have the ability of counting the current amount of items the player has and
    the total amount of items the player has had since the start of the round

    This class also implements Comparable since I need to compare objects of type ItemCount to numbers
 */
public class ItemCount implements Comparable {

    private int totalQuantity;
    private int currentQuantity;

    public ItemCount(){
        this.totalQuantity = 0;
        this.currentQuantity = 0;
    }

    public void upCurrentCount(int quantity){
        this.currentQuantity+=quantity;
    }

    public void downCurrentCount(){
        this.currentQuantity--;
    }

    public void upTotalCount(){
        this.totalQuantity++;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    @Override
    public String toString(){
        return String.valueOf(currentQuantity);
    }

    @Override
    public int compareTo(Object o) {
        if(this.currentQuantity > (int) o){
            return 1;
        }else if(this.currentQuantity < (int) o){
            return -1;
        }else{
            return 0;
        }
    }
}