import java.util.*;

// all game logic here:
public class Game {
    // Fields:
    public String guessMovie;
    public String rightChars;
    public String wrongChars;
    public int point;
    public boolean hasWon;

    // Constructor:
    // initialize a MovieList object with file name:
    public Game(String pathName) {
        MovieList movieList = new MovieList(pathName);
        // define guess movie name from .getRandomMovie() in MovieList object:
        guessMovie = movieList.generateRandomMovie();
        // System.out.println(guessMovie.replaceAll("\\s", "").length());
        rightChars = "";
        wrongChars = "";
        point = 0;
        hasWon = false;
    }

    // Methods:
    /**
     * Method to hide guessMovie with "_"
     */
    public String getHiddenMovieTitle() {
        return this.guessMovie.replaceAll("[a-zA-Z]", "_");
    }

    public int numOfCharacters() {
        // get the number of characters in movie title, exclude spaces:
        return this.guessMovie.replaceAll("\\s", "").length();
    }

    /**
     * Method to read user input
     * 1) read user input using .nextLine() to include spaces
     * handle Exception error
     * 2) check if input is character
     * if input is different than character, log error and re-prompt
     * 3)
     */
    public String getInputCharacter() {

        System.out.println("Guess a letter: ");
        String letter = null;

        try {
            Scanner scanner = new Scanner(System.in);
            // read user input
            // .nextLine() to read input with spaces and till the end of line:
            // .toLowerCase() to handle upper case title:
            letter = scanner.nextLine().toLowerCase();
            System.out.println(letter);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

        // check for correct input format:
        // recursive call to ask for another input:
        if (!letter.matches("[a-zA-z]")) {
            System.out.println("Input has to be a letter!");
            return getInputCharacter();
        }

        return letter;
    }

    /**
     *  Method to guess letter
     *  1) check if the "guessMovie" contains user input character
     *  if true, add the guessed character to the string "rightChar"
     *  use .toLowerCase() to check for all characters in lower case
     *  2) if "guessMovie" does not contain the user's input character
     *  add the guessed character to the string "wrongChar"
     *  increment point to indicate point lost up till 10
     */
    public void guessGame() {
        // get user input:
        String guessedCharacter = getInputCharacter();

        if (this.guessMovie.contains(guessedCharacter)) {
            this.rightChars += guessedCharacter;
            System.out.println("Right chars: " + this.rightChars + " ");
        } else {
            this.wrongChars += " " + guessedCharacter;
            System.out.println("Wrong chars: " + this.wrongChars);
            this.point++;
        }
    }

    /**
     * Method to decide when the game ends
     * 1) check if "point" reaches to 10 => return true as game ends
     * 2) check if the hidden title is not all "_", means all chars have revealed => return true
     * else return false;
     */
    public boolean gameEnded() {
        // when point reaches 10 => lose game:
        if (this.point >= 10) {
            return true;
        }

        // all characters have revealed, NOT all underscores:
        if (!getHiddenMovieTitle().contains("_")) {
            this.hasWon = true;
            return true;
        }

        return false;
    }
}
