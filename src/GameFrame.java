import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(String title){

        this.add(new GamePanel());
        this.setTitle(title);
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


    }

}
