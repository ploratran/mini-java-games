import java.util.*;
import java.util.regex.Pattern.*;

public class Main {
    public static void main (String[] args) {
        // initialize new game with movies in movieList.txt:
        Game game = new Game("movieList.txt");

        System.out.println("Guess movie is: " + game.guessMovie);

        System.out.println("Welcome to Hang Man Game. Let's get started!\n");
        System.out.println("The movie title has with " + game.hiddenMovieTitleNoSpaces() + " characters.");
        System.out.println(game.hiddenMovieTitle());
    }
}
