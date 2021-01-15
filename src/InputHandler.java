import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// This class handles the inputs from the keyboard
public class InputHandler implements KeyListener {

    public InputHandler(GamePanel gp){
        gp.addKeyListener(this);
    }

    // This class represents the concept of a key.
    // A key can be either pressed or not pressed
    // The toggle method is used to switch between the two states
    public class Key{
        private boolean pressed = false;

        public void toggle(boolean isPressed){
            pressed = isPressed;
        }

        public boolean isPressed(){
            return pressed;
        }
    }

    // These four keys are the directional keys
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();

    // These three keys are the inventory keys
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

    // This method is used to switch between the three inventory keys.
    // Only one key at a time can be selected, thus selecting one will deselect the others
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

    // This methods handles the direction keys
    // Thanks to this a player can be moving diagonally
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
