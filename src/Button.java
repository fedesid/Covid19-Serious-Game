public class Button {

    String name;
    int xpos, ypos, width, height, xoffset, yoffset, textSize;

    public Button(String name, int xpos, int ypos, int width, int height, int xoffest, int yoffset, int textSize) {
        this.name = name;
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        this.xoffset = xoffest;
        this.yoffset = yoffset;
        this.textSize = textSize;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOppositeXpos(){
        return xpos + width;
    }

    public int getOppositeYpos(){
        return ypos + height;
    }

    public String getName() {
        return name;
    }

    public int getXoffset() {
        return xoffset;
    }

    public int getYoffset() {
        return yoffset;
    }

    public int getTextSize() {
        return textSize;
    }

    public boolean checkClick(int mouseX, int mouseY){

        if( ( mouseX > this.getXpos() && mouseX < this.getOppositeXpos() ) && (mouseY > this.getYpos() && mouseY < this.getOppositeYpos()) ){
            return true;
        }
        return false;
    }
}
