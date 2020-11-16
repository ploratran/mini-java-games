import java.util.*;

// all game logic here:
public class Game {
    // Fields:
    private String guessMovie;
    private String rightChars;
    private String wrongChars;
    private int point;
    private boolean hasWon;

    // Constructor:
    // initialize a MovieList object with file name:
    public Game(String pathName) {
        MovieList movieList = new MovieList(pathName);
        // define guess movie name from .getRandomMovie() in MovieList object:
        guessMovie = movieList.generateRandomMovie();
        // System.out.println(guessMovie.replaceAll("\\s", "").length());
        rightChars = "";
        wrongChars = "";
        point = 10;
        hasWon = false;
    }

    // Methods:
    /**
     * Method to hide guessMovie with "_"
     * 1) hide all characters with underscores when game first starts
     * meaning when "rightChars" equals empty string
     * 2) when "rightChar" is not empty, start to reveal correctly guessed characters
     * use ^ regex to NOT replace rightChars with underscore
     *
     */
    public String getHiddenMovieTitle() {
        // when "rightChars" is an empty string
        // is when no characters have been guessed yet:
        if (this.rightChars.equals("")) {
            return this.guessMovie.replaceAll("[a-zA-Z]", "_");
        }
        // revealed correctly guessed characters in "rightChars" string:
        // all other characters remain hidden with underscore:
        else {
            return this.guessMovie.replaceAll("[a-zA-Z&&[^" + this.rightChars + "]]", "_");
        }
    }

    /**
     * Method to let users know how many characters they have to guess
     * @return number of characters in movie title
     */
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
     * 3) check if character is already guessed
     * by check if characters is already exists in "rightChars" or "wrongChars"
     * recursive call method again to re-prompt
     */
    private String getInputCharacter() {

        System.out.println("\nGuess a letter: ");
        String letter = null;

        try {
            Scanner scanner = new Scanner(System.in);
            // read user input
            // .nextLine() to read input with spaces and till the end of line:
            // .toLowerCase() to handle upper case title:
            letter = scanner.nextLine().toLowerCase();
        } catch (Exception e) {
            System.out.println("Exception " + e);
        }

        // check for correct input format:
        // recursive call to ask for another input:
        if (!letter.matches("[a-zA-z]")) {
            System.out.println("Input has to be a letter!");
            return getInputCharacter();
        }
        // check if input character is already guessed:
        if (this.rightChars.contains(letter) || this.wrongChars.contains(letter)) {
            System.out.println("Letter is already guessed. Guess again!");
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

        // concatenate correct letter into "rightChars" string:
        if (this.guessMovie.contains(guessedCharacter)) {
            // include both uppercase and lowercase character in rightChars string:
            this.rightChars += guessedCharacter + guessedCharacter.toUpperCase() + " ";
            // System.out.println("Right chars: " + this.rightChars);

        // concatenate wrong letter into "wrongChars" string:
        // decrement point to indicate lose point:
        } else {
            this.wrongChars += " " + guessedCharacter;
            System.out.println("\nYou have guessed: [" + this.wrongChars + " ] wrong!");
            this.point--;

            if (this.point > 1) {
                System.out.println("You have " + this.point + " points left. Guess again: \n");
            }
            if (this.point == 1) {
                System.out.println("You have " + this.point + " point left\n");
            }
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
        if (this.point <= 0) {
            return true;
        }

        // all characters have revealed, NOT all underscores:
        if (!getHiddenMovieTitle().contains("_")) {
            this.hasWon = true;
            return true;
        }

        return false;
    }

    /**
     * Method to return whether the game has ended or not
     * @return boolean of game has won or lose:
     */
    public boolean WonGame() {
        return this.hasWon;
    }

    public String getGuessMovie() {
        return this.guessMovie;
    }
}
