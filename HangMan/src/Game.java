

// all game logic here:
public class Game {
    // Fields:
    public String guessMovie;

    // Constructor:
    // initialize a MovieList object with file name:
    public Game(String pathName) {
        MovieList movieList = new MovieList(pathName);
        // define guess movie name from .getRandomMovie() in MovieList object:
        guessMovie = movieList.generateRandomMovie();
    }

    // Methods:
}
