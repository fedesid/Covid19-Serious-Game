import java.awt.*;

public class GameOverGraphics extends Menus {

    Button mainMenu, scores, restart, quit;

    int btnSize = 300;

    public GameOverGraphics(GamePanel gp){
        super(gp);

        mainMenu = new Button("MENU", (GamePanel.SCREEN_WIDTH/3)-btnSize-30, (GamePanel.SCREEN_HEIGHT/2)+100, btnSize, btnSize/3,80,70,50 );
        scores = new Button("SCORES", (GamePanel.SCREEN_WIDTH*2/3)-btnSize-30, (GamePanel.SCREEN_HEIGHT/2)+100, btnSize, btnSize/3,65,70,50 );
        restart = new Button("RESTART", (GamePanel.SCREEN_WIDTH*3/3)-btnSize-30, (GamePanel.SCREEN_HEIGHT/2)+100, btnSize, btnSize/3,50,70,50 );
        quit = new Button("QUIT", (GamePanel.SCREEN_WIDTH*2/3)-btnSize-30, (GamePanel.SCREEN_HEIGHT/2)+250, btnSize, btnSize/3,85,70,50 );

        buttons.add(mainMenu);
        buttons.add(scores);
        buttons.add(restart);
        buttons.add(quit);

    }

    public void draw(Graphics g){
        super.draw(g);

        // Draw country flag + name
        gp.country.drawFlag(g, GamePanel.SCREEN_WIDTH, 0, 200, 200, 80);

        // Draw player name
        Menus.drawCenteredString(gp.player.getName(), GamePanel.SCREEN_WIDTH, 0, 100, 100, new Font("LATO", Font.BOLD, 40), new Color(0xFFFFFF), g);

        // Draw score
        Menus.drawCenteredString("Score: " + (int)gp.userScore.getScore(), GamePanel.SCREEN_WIDTH, 0, 400, 400, new Font("LATO", Font.BOLD, 50), new Color(0xFFFFFF), g );

    }

}