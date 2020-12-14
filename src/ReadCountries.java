import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class ReadCountries {

    TreeMap<String, Integer> countriesStats;

    public ReadCountries(String filename){

        countriesStats = new TreeMap<>();

        try{
            Scanner sc = new Scanner(new File(filename));

            while (sc.hasNext()){

                String[] input = sc.nextLine().split(" ");

                countriesStats.put(input[0], Integer.valueOf(input[1]) );


            }

            sc.close();

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }


    }
}