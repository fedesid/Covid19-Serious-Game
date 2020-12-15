import java.awt.*;

public class LeaderboardGraphics extends Menus {

    Button back;

    public LeaderboardGraphics(GamePanel gp){
        super(gp);

        back = new Button("BACK", 20,20,100,50,20,30,20);
        buttons.add(back);

    }

    public void draw(Graphics g){
        super.draw(g);

        g.drawRect(GamePanel.SCREEN_WIDTH/4, GamePanel.SCREEN_HEIGHT/8, 400,400);

    }


}
