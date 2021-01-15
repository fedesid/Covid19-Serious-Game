import java.awt.*;
import java.util.Iterator;

/*
    LeaderboardGraphics is a class that extends Menus
    This class handle the GUI inside of the leaderboard screen

    It prints the table together with the scores

    Just run the game and look at it to understand the draw method better

 */
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

        //draw table
        g.setColor(new Color(0x25FFFFFF, true));
        g.fillRect(xunit*3, yunit*3, xunit*6, yunit*8);
        //g.drawRect(xunit, yunit*3, xunit*10,yunit*8);

        g.setColor(new Color(0x4A03FF));
        // draw outer rectangle
        for(int i=0; i<10; i++){
            g.drawRect((xunit*3)-i, yunit*3-i, xunit*6+i*2,yunit*8+i*2);
        }

        // draw horizontal lines
        for(int i=0; i<8; i++){
            g.drawLine(xunit*3, yunit*(3+i)-1, xunit*9, yunit*(3+i)-1);
            g.drawLine(xunit*3, yunit*(3+i), xunit*9, yunit*(3+i));
            g.drawLine(xunit*3, yunit*(3+i)+1, xunit*9, yunit*(3+i)+1);
        }

        // draw vertical lines
        for (int i = 1; i < 5; i++){
            g.setColor(new Color(0xFF897BFF, true));
            g.drawLine(xunit*(2*i+1), yunit*3, xunit*(2*i+1), yunit*11);
        }

        Font font = new Font("LATO", Font.BOLD, 30);

        Menus.drawCenteredString("NAME", xunit*2, xunit*6, yunit*3, yunit*4, font, new Color(0xFFFFFF), g);
        Menus.drawCenteredString("COUNTRY", xunit*2, xunit*10, yunit*3, yunit*4, font, new Color(0xFFFFFF), g);
        Menus.drawCenteredString("SCORE", xunit*2, xunit*14, yunit*3, yunit*4, font, new Color(0xFFFFFF), g);

        // Loop through the scores so that it can print them. It will only print 7 scores and only the name, country, and #score of each score
        int count = 0;
        for(Iterator<Score> scoreIterator = ReadScore.scores.iterator(); scoreIterator.hasNext();){
            Score score = scoreIterator.next();
            String[] leaderboard = score.leaderboard().split(" ");

            if(count < 7){
                for(int i=0; i<3; i++){
                    Menus.drawCenteredString(leaderboard[i], xunit*2, xunit*(6+4*i), yunit*(4+count), yunit*(5+count), font, new Color(0xFFFFFF), g);
                }
            }
            count++;

        }

    }


}
