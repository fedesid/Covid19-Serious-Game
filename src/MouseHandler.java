import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    GamePanel gp;

    int xcoo;
    int ycoo;

    public MouseHandler(GamePanel gp){
        this.gp = gp;
        gp.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if (gp.inventory.items[0] > 0){

            gp.inventory.items[0]--;
            xcoo = mouseEvent.getX();
            ycoo = mouseEvent.getY();

            Bullet bullet = new Bullet(gp.player.getXpos()+gp.player.getPlayerSize()/2, gp.player.getYpos()+gp.player.getPlayerSize()/2, xcoo, ycoo);
            GamePanel.bulletsOnScreen.add(bullet);
            System.out.println(xcoo + " " + ycoo);
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
