import java.util.*;
import java.io.*;

public class Game {
    public static void main (String[] args) {

        MovieList movieList = new MovieList("movieList.txt");
        movieList.generateRandomMovie();
    }
}
