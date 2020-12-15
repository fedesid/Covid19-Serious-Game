public class GameOverGraphics extends Menus {

    Button mainMenu, scores, restart;

    int btnSize = 300;

    public GameOverGraphics(GamePanel gp){
        super(gp);

        mainMenu = new Button("MENU", GamePanel.SCREEN_WIDTH/2-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)-200, btnSize, btnSize/3,80,70,50 );
        scores = new Button("SCORES", GamePanel.SCREEN_WIDTH/2-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)-50, btnSize, btnSize/3,65,70,50 );
        restart = new Button("RESTART", GamePanel.SCREEN_WIDTH/2-btnSize/2, (GamePanel.SCREEN_HEIGHT/2)+100, btnSize, btnSize/3,80,70,50 );

        buttons.add(mainMenu);
        buttons.add(scores);
        buttons.add(restart);

    }

}