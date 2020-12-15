import java.awt.event.*;
import java.io.IOException;
import java.util.Iterator;

public class MouseHandler implements MouseListener {
    GamePanel gp;
    InputHandler input;

    int xcoo;
    int ycoo;
    static long startTime = System.currentTimeMillis();


    public MouseHandler(GamePanel gp){

        maskTime.start();
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

        if(gp.state == GamePanel.STATE.GAME){

            if(input.one.isPressed()){ // Gel
                if (Inventory.items[0].compareTo(0) > 0){

                    Gel.totalNumberOfGelUsed++;
                    Inventory.items[0].downCurrentCount();
                    xcoo = mouseEvent.getX();
                    ycoo = mouseEvent.getY();
                    System.out.println("AIM COO: " + xcoo + " " + ycoo);
                    System.out.println("PLAYER: " + gp.player.getXpos() + " " + gp.player.getYpos());
                    Bullet bullet = new Bullet(gp.player.getXpos()+gp.player.getPlayerSize()/2, gp.player.getYpos()+gp.player.getPlayerSize()/2, xcoo, ycoo);
                    GamePanel.bulletsOnScreen.add(bullet);

                }
            }

            if(input.two.isPressed()){ // Mask

                if(gp.player.maskOn){
                    gp.player.wearMask(false);
                }else if(Inventory.items[1].compareTo(0) > 0){

                    startTime = System.currentTimeMillis();

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

                        double distance = GamePanel.calcDistance(gp.player.getXpos()+gp.player.getPlayerSize()/2, gp.player.getYpos()+gp.player.getPlayerSize()/2, virus.getXpos()+virus.getSize()/2, virus.getYpos()+virus.getSize()/2);

                        // two different formulas for calculating the damage of the vaccine, still have to decide which way to go
                        //int damage = (int) (-Math.pow( (float) distance/100, 2 )+virus.initHealth*1.1);
                        int damage = (int) ( (15000)/(distance+125) );
                        if(damage<0){
                            damage=0;
                        }
                        System.out.println(damage);
                        virus.takeDamage((int) (damage), virusIterator);

                        gp.country.healPopulation(10);

                    }
                }
            }


        // Main menu state
        }else if(gp.state == GamePanel.STATE.MENU ){

            // Play button
            if( gp.menu.play.checkClick(mouseEvent.getX(), mouseEvent.getY())  ){
                System.out.println("PRESSED PLAY");
                try {
                    gp.startGame();
                    gp.state = GamePanel.STATE.GAME;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            // Score button
            }else if( gp.menu.scores.checkClick(mouseEvent.getX(), mouseEvent.getY()) ){
                System.out.println("PRESSED SCORES");
                gp.state = GamePanel.STATE.LEADERBOARD;

            // Quit button
            }else if( gp.menu.quit.checkClick(mouseEvent.getX(), mouseEvent.getY()) ){
                System.out.println("PRESSED QUIT");
                System.exit(-1);
            }

        // Leaderboard state
        }else if(gp.state == GamePanel.STATE.LEADERBOARD){

            if( gp.leaderboard.back.checkClick(mouseEvent.getX(), mouseEvent.getY())  ){
                System.out.println("PRESSED BACK");
                gp.state = GamePanel.STATE.MENU;

            }
        }else if(gp.state == GamePanel.STATE.END){

            if(gp.gameover.mainMenu.checkClick(mouseEvent.getX(), mouseEvent.getY())){
                System.out.println("PRESSED MENU");
                gp.state = GamePanel.STATE.MENU;

            }else if(gp.gameover.scores.checkClick(mouseEvent.getX(), mouseEvent.getY())){
                System.out.println("PRESSED SCORES");
                gp.state = GamePanel.STATE.LEADERBOARD;

            }else if(gp.gameover.restart.checkClick(mouseEvent.getX(), mouseEvent.getY())){
                try {
                    System.out.println("PRESSED RESTART");
                    gp.startGame();
                    gp.state = GamePanel.STATE.GAME;
                } catch (IOException e) {
                    e.printStackTrace();
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



    Thread maskTime = new Thread(new Runnable() {

        public void run() {

            while(true) {

                if(gp.state == GamePanel.STATE.GAME ){

                    if( Math.abs(startTime - System.currentTimeMillis())/1000 > Mask.maskDuration ){
                        gp.player.wearMask(false);
                    }

                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
}

class MouseWheelHandler implements MouseWheelListener {

    InputHandler input;

    public MouseWheelHandler(GamePanel gp){
        gp.addMouseWheelListener(this);
    }

    public void linkInput(InputHandler input){
        this.input = input;
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {

        if(mouseWheelEvent.getWheelRotation() < 0){
            System.out.println("UP");
            System.out.println("DOWN");

            if(input.one.isPressed()){
                input.one.toggle(false);
                input.three.toggle(true);
            }else if(input.two.isPressed()){
                input.two.toggle(false);
                input.one.toggle(true);
            }else if(input.three.isPressed()){
                input.three.toggle(false);
                input.two.toggle(true);
            }else{
                input.one.toggle(true);
            }

        }else{


            if(input.one.isPressed()){
                input.one.toggle(false);
                input.two.toggle(true);
            }else if(input.two.isPressed()){
                input.two.toggle(false);
                input.three.toggle(true);
            }else if(input.three.isPressed()){
                input.three.toggle(false);
                input.one.toggle(true);
            }else{
                input.three.toggle(true);
            }
        }


    }
}
