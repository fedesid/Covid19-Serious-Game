import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 600;
    static final int DELAY = 75;

    JFrame frame;
    GamePanel gPanel;

    GridLayout grid;

    public GameFrame(String title){
        frame = new JFrame(title);
        this.setVisible(true);
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        grid = new GridLayout(3,3);
        frame.setLayout(grid);

        gPanel = new GamePanel();
        gPanel.setSize(SCREEN_WIDTH/2, SCREEN_HEIGHT);

        gPanel.setBackground(Color.red);

        this.add(gPanel);

    }

}
