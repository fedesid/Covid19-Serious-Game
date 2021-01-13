import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vaccine extends Item {

    static int totalNumberOfVaccineUsed;

    int size = 40;

    public Vaccine(int xpos, int ypos) {
        super(xpos, ypos);
        this.name = "Vaccine";

        try {
            sprite = ImageIO.read(new File("src/Sprites/Entities/vaccine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

    }

}
