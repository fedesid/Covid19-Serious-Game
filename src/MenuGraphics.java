import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class MenuGraphics extends Menus {

    int btnSize = 300;

    Button play, scores, quit;

    public MenuGraphics(GamePanel gp){
        super();

        play = new Button("PLAY", GamePanel.SCREEN_WIDTH/5-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)-150, btnSize, btnSize/3,80,70,50 );
        scores = new Button("SCORES", GamePanel.SCREEN_WIDTH/5-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)-0, btnSize, btnSize/3,65,70,50 );
        quit = new Button("QUIT", GamePanel.SCREEN_WIDTH/5-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)+150, btnSize, btnSize/3,80,70,50 );

        buttons.add(play);
        buttons.add(scores);
        buttons.add(quit);

    }

}
