import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    public static void main (String[] args) throws FileNotFoundException {
        // initialize file that contains all movies:
        File file = new File("movieList.txt");

        // generate random number:
        Random rand = new Random();

        // initialize an Array List of movies:
        ArrayList<String> movies = new ArrayList<String>();

        // Read movies in movieList.txt:
        Scanner scanner = new Scanner(file);

        // iterate over each line in file
        // check if there exists next line by .hasNext():
        while (scanner.hasNextLine()) {
            movies.add(scanner.nextLine());
        }

        Iterator i = movies.iterator();

        // iterate over each character in movie title:
        while (i.hasNext()) {
            System.out.println(i.next());
        }

    }
}
