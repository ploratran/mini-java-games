import java.util.*;
import java.io.*;

public class MovieList {

    // initialize field for movies:
    private ArrayList<String> movies;

    // constructor with movie list file as param:
    public MovieList(String pathName) {
        // initialize file from movieList.txt:
        File file = new File(pathName);

        // initialize an array list that stores all movie titles:
        movies = new ArrayList<>();

        // read file using Scanner
        // iterate over each line in file using .hasNextLine()
        // add each movie title in each line to array list "movies":
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                movies.add(scanner.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found " + e);
        }

        // define iterator:
        Iterator i = movies.iterator();

        // iterate over each movie in movieList:
        while(i.hasNext()) {
            System.out.println(i.next());
        }
    }
}
