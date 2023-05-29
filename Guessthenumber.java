import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int rangeStart = 1;
        int rangeEnd = 100;
        int maxAttempts = 5;
        int score = 0;

        System.out.println("Welcome to the Guess the Number Game!");
        System.out.println("I have generated a number between " + rangeStart + " and " + rangeEnd + ".");

        boolean playAgain = true;
        while (playAgain) {
            int numberToGuess = random.nextInt(rangeEnd - rangeStart + 1) + rangeStart;
            int attempts = 0;

            System.out.println("Round " + (score + 1));
            System.out.println("You have " + maxAttempts + " attempts to guess the number.");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess < numberToGuess) {
                    System.out.println("Too low!");
                } else if (guess > numberToGuess) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    int roundScore = maxAttempts - attempts + 1;
                    score += roundScore;
                    System.out.println("Round score: " + roundScore);
                    break;
                }

                if (attempts < maxAttempts) {
                    System.out.println("Try again.");
                } else {
                    System.out.println("Sorry, you have used all your attempts.");
                    System.out.println("The number I was thinking of was: " + numberToGuess);
                }
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainResponse = scanner.next();
            playAgain = playAgainResponse.equalsIgnoreCase("yes");

            if (playAgain) {
                System.out.println("Let's play another round!");
            } else {
                System.out.println("Thank you for playing. Your total score is: " + score);
            }
        }

        scanner.close();
    }
}
