import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.util.Random;

public class Game {
    public static void main (String[] args) throws FileNotFoundException {
        // initialize file that contains all movies:
        File file = new File("movieList.txt");

        // generate random number:
        Random rand = new Random();

        String movie = null;
        int n = 0;

        // iterate over each line in file
        // check if there exists next line by .hasNext():
        for(Scanner sc = new Scanner(file); sc.hasNext(); ) {
            n++;
            String line = sc.nextLine();
            // .nextInt() reads string of characters and convert them into int:
            if(rand.nextInt(n) == 0) {
                // name of movie at particular line: 
                movie = line;
            }
        }
        System.out.println(movie);
    }
}
