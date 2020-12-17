import java.awt.*;
import java.util.Iterator;

public class LeaderboardGraphics extends Menus {

    Button back;

    int nOfLines, xunit, yunit, xcount, ycount, xindex, yindex;

    public LeaderboardGraphics(GamePanel gp){
        super(gp);

        back = new Button("BACK", 20,20,100,50,20,30,20);
        buttons.add(back);

        nOfLines = 12;
        xunit = (GamePanel.SCREEN_WIDTH/nOfLines);
        yunit = (GamePanel.SCREEN_HEIGHT/nOfLines);
        xcount = 5;
        ycount = 3;
        xindex = (GamePanel.SCREEN_WIDTH/nOfLines)*xcount;
        yindex = (GamePanel.SCREEN_HEIGHT/nOfLines)*ycount;

    }

    public void draw(Graphics g){
        super.draw(g);

        int nOfLines = 12;
        for(int i=0; i<=nOfLines; i++){

            g.setColor(new Color(0x4FFF0000, true));
            //g.drawLine((GamePanel.SCREEN_WIDTH/nOfLines)*i, 0, (GamePanel.SCREEN_WIDTH/nOfLines)*i, GamePanel.SCREEN_HEIGHT );
            //g.drawLine(0, (GamePanel.SCREEN_HEIGHT/nOfLines)*i, GamePanel.SCREEN_WIDTH, (GamePanel.SCREEN_HEIGHT/nOfLines)*i);

        }

        g.setColor(new Color(0xFFFFFF));
        g.drawRect(xunit*3, yunit*3, xunit*6,yunit*8);
        for(int i=0; i<8; i++){
            g.drawLine(xunit*3, yunit*(3+i), xunit*9, yunit*(3+i));
        }

        Menus.drawCenteredString("Name___Country___Score___Kills___Contacts", xunit*2, xunit*10, yunit*(3),yunit*(3+1), new Font("LATO", Font.BOLD, 30), new Color(0xFFFFFF), g );


        int count = 0;
        for(Iterator<Score> scoreIterator = ReadScore.scores.iterator(); scoreIterator.hasNext();){
            Score score = scoreIterator.next();

            if(count < 7){
                Menus.drawCenteredString(score.leaderboard(), xunit*2, xunit*10, yunit*(4+count),yunit*(4+count+1), new Font("LATO", Font.BOLD, 30), new Color(0xFFFFFF), g );
                //System.out.println(score.leaderboard());
            }
            count++;


        }

    }


}
