

// all game logic here:
public class Game {
    // Fields:
    public String guessMovie;
    public String rightChar;
    public String wrongChar;
    public int point;
    public boolean hasWon;

    // Constructor:
    // initialize a MovieList object with file name:
    public Game(String pathName) {
        MovieList movieList = new MovieList(pathName);
        // define guess movie name from .getRandomMovie() in MovieList object:
        guessMovie = movieList.generateRandomMovie();
        // System.out.println(guessMovie.replaceAll("\\s", "").length());
        rightChar = "";
        wrongChar = "";
        point = 0;
        hasWon = false;
    }

    // Methods:
    /**
     * Method to hide guessMovie with "_"
     *
     */
    public String hiddenMovieTitle() {
        return guessMovie.replaceAll("[a-zA-Z]", "_");
    }

    public int hiddenMovieTitleNoSpaces() {
        return this.guessMovie.replaceAll("\\s", "").length();
    }

    /**
     * Method to decide when the game ends
     * check if "point" reaches to 10 => return true as game ends
     * check if the hidden title is not all "_", means all chars revealed => return true
     * else return false;
     */
    public boolean gameEnded() {
        if (this.point >= 10) {
            return true;
        }

        if (!hiddenMovieTitle().contains("_")) {
            hasWon = true;
            return true;
        }

        return false;
    }



}
