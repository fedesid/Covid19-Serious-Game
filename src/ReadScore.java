import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadScore {

    static ArrayList<Score> scores = new ArrayList<>();

    public ReadScore(String filename){

        try{
            Scanner sc = new Scanner(new File(filename));
            sc.nextLine();
            while (sc.hasNext()){

                String[] score = sc.nextLine().split("[ ]+");

                scores.add(new Score(score[0], score[1], Integer.valueOf(score[2]), Integer.valueOf(score[3]), Integer.valueOf(score[4]),Integer.valueOf(score[5]), Integer.valueOf(score[6]) ));

            }

            sc.close();

        }catch (FileNotFoundException e){

        }catch (NoSuchElementException e){

        }

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(scores.toArray()) );

        ReadScore rs = new ReadScore("src/score.txt");

        scores.add(new Score("Mario", "France", 133, 123, 5345345, 645645, 35435));

        WriteScore writeScore = new WriteScore("src/score.txt");
    }

}
