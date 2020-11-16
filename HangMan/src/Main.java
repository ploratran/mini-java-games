import java.util.*;
import java.util.regex.Pattern.*;

public class Main {
    public static void main (String[] args) {
        // initialize new game with movies in movieList.txt:
        Game game = new Game("movieList.txt");

        System.out.println("Welcome to Hang Man Game. You have 10 points to win. Let's get started!\n");
        System.out.println("The movie title has with " + game.numOfCharacters() + " characters.");


        // set condition while game has not ended:
        while (!game.gameEnded()) {
            System.out.println(game.getHiddenMovieTitle());
            // guess game starts:
            game.guessGame();
        }

        // determine game win or lose:
        if (game.WonGame()) {
            System.out.println("\nYou win!");
            System.out.println("The hidden movie is: " + game.getGuessMovie());
        } else {
            System.out.println("\nYou lose!");
            System.out.println("The hidden movie is: " + game.getGuessMovie());
            System.out.println("Good luck next time!");
        }

    }
}
