import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

public class WriteScore {

    public WriteScore(String filename){

        try{
            PrintWriter output = new PrintWriter(filename);

            output.println(String.format("%-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s", "Name", "Country", "Score", "Kills", "Contacts", "Alive", "Infected", "Dead", "Gel", "Masks", "Vaccines"));

            for(Iterator<Score> scoreIterator = ReadScore.scores.iterator(); scoreIterator.hasNext();){
                Score score = scoreIterator.next();
                output.println(score);
            }

            output.close();


        }catch (FileNotFoundException e){

        }

    }
}
