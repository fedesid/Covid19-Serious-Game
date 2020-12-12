import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

public class MouseHandler implements MouseListener {
    GamePanel gp;
    InputHandler input;

    int xcoo;
    int ycoo;

    public MouseHandler(GamePanel gp){
        this.gp = gp;
        gp.addMouseListener(this);
    }

    public void linkInput(InputHandler input){
        this.input = input;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        if(input.one.isPressed()){ // Gel
            if (Inventory.items[0].compareTo(0) > 0){

                Gel.totalNumberOfGelUsed++;
                Inventory.items[0].downCurrentCount();
                xcoo = mouseEvent.getX();
                ycoo = mouseEvent.getY();
                System.out.println(xcoo + " " + ycoo);

                Bullet bullet = new Bullet(gp.player.getXpos()+gp.player.getPlayerSize()/2, gp.player.getYpos()+gp.player.getPlayerSize()/2, xcoo, ycoo);
                GamePanel.bulletsOnScreen.add(bullet);

            }
        }

        if(input.two.isPressed()){ // Mask

            if(Inventory.items[1].compareTo(0) > 0){

                Mask.totalNumberOfMaskUsed++;
                gp.player.wearMask(true);
                Inventory.items[1].downCurrentCount();
            }


        }

        if(input.three.isPressed()){ // Vaccine
            if (Inventory.items[2].compareTo(0) > 0){
                Vaccine.totalNumberOfVaccineUsed++;

                Inventory.items[2].downCurrentCount();

                for(Iterator<Virus> virusIterator = GamePanel.virusOnScreen.iterator(); virusIterator.hasNext(); ){
                    Virus virus = virusIterator.next();
                    virus.takeDamage(75, virusIterator);

                }
            }
        }



    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
