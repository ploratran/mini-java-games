import java.util.*;
import java.util.regex.Pattern.*;

public class Main {
    public static void main (String[] args) {
        // initialize new game with movies in movieList.txt:
        Game game = new Game("movieList.txt");

        System.out.println("Guess movie is: " + game.guessMovie);

        System.out.println("Welcome to Hang Man Game. Let's get started!\n");
        System.out.println("The movie title has with " + game.numOfCharacters() + " characters.");


        // set condition while game has not ended:
        while (!game.gameEnded()) {
            System.out.println(game.getHiddenMovieTitle());
            System.out.println("Your wrong characters:" + game.wrongChars);
            // guess game starts:
            game.guessGame();
        }
    }
}
