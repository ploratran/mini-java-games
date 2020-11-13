import java.util.InputMismatchException;
import java.util.Scanner;

public class NumberGame {
    public static void main(String [] args) {
        // generate random number from 1 - 100:
        int randomNumber = (int) Math.floor(Math.random() * 100) + 1;
        boolean hasWon = false;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Guess from 1 to 100. You have 10 guesses.\n");

        // create a loop to ask user for the guess number:
        for (int i = 10; i > 0; i--) {
            System.out.println("You have " + i + " guess(es) left");
            System.out.println("Enter your guess: " );

            int guessNumber = scanner.nextInt();

            if (guessNumber < randomNumber) {
                System.out.println("Number should be larger than " + guessNumber);
            } else if (guessNumber > randomNumber) {
                System.out.println("Number should be smaller than " + guessNumber);
            } else {
                hasWon = true;
                break;
            }
        }

        if (hasWon) {
            System.out.println("Correct. You win!");
        } else {
            System.out.println("\nYou have used up 10 points. You lose!");
        }
    }
}
