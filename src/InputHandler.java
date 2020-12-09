import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    public InputHandler(GamePanel gp){
        gp.addKeyListener(this);
    }

    public class Key{
        private boolean pressed = false;

        public void toggle(boolean isPressed){
            pressed = isPressed;
        }

        public boolean isPressed(){
            return pressed;
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    public Key one = new Key();
    public Key two = new Key();
    public Key three = new Key();

    @Override
    public void keyPressed(KeyEvent e) {
        toggleKey(e.getKeyCode(), true);
        selectKey(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleKey(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void selectKey(int keyCode, boolean isSelected){

        if (keyCode == KeyEvent.VK_1){
            one.toggle(isSelected);
            two.toggle(!isSelected);
            three.toggle(!isSelected);
        }
        if (keyCode == KeyEvent.VK_2){
            one.toggle(!isSelected);
            two.toggle(isSelected);
            three.toggle(!isSelected);
        }
        if (keyCode == KeyEvent.VK_3){
            one.toggle(!isSelected);
            two.toggle(!isSelected);
            three.toggle(isSelected);
        }

        //System.out.println("one: " + one.pressed + " two: " + two.pressed + " three: " + three.pressed);
    }

    public void toggleKey(int keyCode, boolean isPressed){
        if (keyCode == KeyEvent.VK_W){
            up.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_S){
            down.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_A){
            left.toggle(isPressed);
        }
        if (keyCode == KeyEvent.VK_D){
            right.toggle(isPressed);
        }
    }

}
