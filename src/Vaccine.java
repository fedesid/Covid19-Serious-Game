import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Vaccine extends Item {

    static int totalNumberOfVaccineUsed;

    static double difficultyLvl = 1.2;

    static int timeToDevelop = 0;

    static double vaccineThreashold = ( 120 )/difficultyLvl;
    static double progress = (Inventory.items[0].getTotalQuantity()+ (Inventory.items[1].getTotalQuantity()*5) )/Vaccine.vaccineThreashold;

    static boolean vaccineIsReady = false;

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

    public static void updateThreshold(){
        vaccineThreashold = ( 120 )/difficultyLvl;
    }

    static void updateProgress(int gel, int mask, int kills){
        progress = (gel + (mask*3) + (int)(kills/3) )/Vaccine.vaccineThreashold;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

}
