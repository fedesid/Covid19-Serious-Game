import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class ReadScore {

    static ArrayList<Score> scores = new ArrayList<>();

    public ReadScore(String filename) throws IOException {

        try{
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();
            while (sc.hasNext()){

                String[] score = sc.nextLine().split("[ ]+");

                scores.add(new Score(score[0], score[1], Double.valueOf(score[2]), Integer.valueOf(score[3]), Integer.valueOf(score[4]),Integer.valueOf(score[5]), Integer.valueOf(score[6]), Integer.valueOf(score[7]), Integer.valueOf(score[8]), Integer.valueOf(score[9]), Integer.valueOf(score[10]), Integer.valueOf(score[11]), Integer.valueOf(score[12]) ));

            }
            Collections.sort(ReadScore.scores);
            sc.close();
            System.out.println(filename + " found!");

        }catch (FileNotFoundException e){
            File file = new File(filename);
            file.createNewFile();
            System.out.println("File not found! Creating new file..\n" + filename + " created.");

        }catch (NoSuchElementException e){

        }

    }

}