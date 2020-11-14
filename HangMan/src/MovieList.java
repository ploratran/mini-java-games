import java.util.*;
import java.io.*;

public class MovieList {

    // Fields:
    private ArrayList<String> movies;

    // Constructor with movie list file as param:
    public MovieList(String pathName) {
        // initialize file from movieList.txt:
        File file = new File(pathName);

        // initialize an array list that stores all movie titles:
        this.movies = new ArrayList<>();

        // read file using Scanner
        // iterate over each line in file using .hasNextLine()
        // add each movie title in each line to array list "movies":
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                this.movies.add(scanner.nextLine());
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found " + e);
        }

//        print each movie in movies List:
//        for (String movie : this.movies) {
//            System.out.println(movie);
//        }
    }

    // define method to generate random movie from movies list:
    public String generateRandomMovie() {
        int randomIndex = (int) (Math.random() * this.movies.size()) + 1;
        System.out.println("random index " + randomIndex);
        String randomMovie = this.movies.get(randomIndex-1);
        System.out.println("Random movie is: " + randomMovie);

        return "";
    }
}
