public class ItemCount implements Comparable {

    private int totalQuantity;
    private int currentQuantity;

    public ItemCount(){
        this.totalQuantity = 0;
        this.currentQuantity = 0;
    }

    public void upCurrentCount(){
        this.currentQuantity++;
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

    /*
    @Override
    public String toString() {
        return "ItemCount{" +
                "totalQuantity=" + totalQuantity +
                ", currentQuantity=" + currentQuantity +
                '}';
    }

     */
}
