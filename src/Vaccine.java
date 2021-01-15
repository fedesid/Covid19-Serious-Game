import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
/*
    This class represents the concept of a Vaccine inside the game
    It is responsible for the logic of the game round as well.
    The vaccine Threshold variable is a threshold which represent the amount of progress that the player needs to accomplish in order to obtain a vaccine
    The progress variable represents the progress that the player has made towards obtaining a vaccine
 */
public class Vaccine extends Item {

    static int totalNumberOfVaccineUsed;

    static double difficultyLvl = 1.2;

    static int timeToDevelop = 0;

    static double vaccineThreshold = ( 120 )/difficultyLvl;
    static double progress = (Inventory.items[0].getTotalQuantity()+ (Inventory.items[1].getTotalQuantity()*5) )/Vaccine.vaccineThreshold;

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
        vaccineThreshold = ( 120 )/difficultyLvl;
    }

    static void updateProgress(int gel, int mask, int kills){
        progress = (gel + (mask*3) + (int)(kills/3) )/Vaccine.vaccineThreshold;
    }

    @Override
    public void paintComponent(Graphics g) {

    }

}
