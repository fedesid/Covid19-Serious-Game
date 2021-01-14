import java.awt.*;

public class GameplayGraphics extends Menus {

    Button countDown;
    Button vaccineProgress;

    int vaccineX = GamePanel.SCREEN_WIDTH/3;
    int vaccineY = (GamePanel.SCREEN_HEIGHT/20)*3;
    int vacciceWidth = GamePanel.SCREEN_WIDTH - vaccineX*2;
    int vaccineHeigth = 15;

    static long timeLeft;

    public GameplayGraphics(GamePanel gp){
        super(gp);

        timeLeft = (GamePanel.roundLength - (System.currentTimeMillis()-GamePanel.START_TIME)) ; // time left in milliseconds

        countDown = new Button(String.valueOf(  (timeLeft*360)/GamePanel.roundLength ) + " days left", (GamePanel.SCREEN_WIDTH/2)-75,65,150,25,20,30,20);
        vaccineProgress = new Button("VACCINE", vaccineX, vaccineY, vacciceWidth, vaccineHeigth,20,30,20 );


        buttons.add(countDown);
        buttons.add(vaccineProgress);

    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);


        // DRAWING VACCINE PROGRESS BAR
        if(!Vaccine.vaccineIsReady){
            g.setColor(new Color(0x36000000, true));
            g.fillRect(vaccineX, vaccineY, vacciceWidth, vaccineHeigth);
            g.setColor(new Color(0x9A3AA100, true));
            g.fillRect(vaccineX, vaccineY, (int)(Vaccine.progress*vacciceWidth), vaccineHeigth);
        }

        //System.out.println(Vaccine.progress + " " + Vaccine.vaccineThreashold);


    }
}
