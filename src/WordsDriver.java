import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Robert Jardine
 * An ArrayList will be populated with random letters.
 * When a user enters a word, it will check that it is
 * a valid English word and that all words are present
 * in the pool of letters. It will then delete those
 * characters from the ArrayList and move to the next
 * round. One point is awarded per character. Entering
 * '**' will end the game.
 *
 */

public class WordsDriver {

    public static void main(String[] args) throws FileNotFoundException {

        String userInput;
        Words pool = new Words();

        while (true) {
            if (pool.getRounds() == 0) {
                System.out.println("NEW GAME");
            } else if(pool.getGiveup()) {
                pool.setGiveup(false);
                pool = new Words();
                System.out.println("NEW GAME");
            }
            System.out.println(pool.printOut());
            System.out.println("There are " + pool.poolSize() + " letters remaining");

            //Ask for user to input word
            System.out.println("*****************************************");
            System.out.println("Please Enter a Valid Word: ('**' WILL END THE GAME)");
            System.out.println("*****************************************");
            System.out.print("ANSWER: ");
            Scanner sc = new Scanner(System.in);
            //Make user input lower case b/c the dict entries also are
            userInput = sc.next();
            String toLowerCase = userInput.toLowerCase();

            //Give - Up
            if (toLowerCase.equals("**")) {
                //Ask if they want to play another game
                pool.setGiveup(true);
                pool.addRound();
                System.out.println("GAME OVER");
                System.out.println("FINAL SCORE: " + pool.getScore());
                System.out.println("Would you like to play again? (y/n)");
                Scanner input = new Scanner(System.in);
                String playAgain = input.next();
                if (playAgain.equals("n")) {
                    //Break from loop and end program
                    System.out.println("Thank You For Playing!");
                    //break from while loop
                    break;
                }
            } else {
                boolean validWord = isInDictionaryAndPool(pool, toLowerCase);

                if (validWord) {
                    if (pool.isEmpty()) {
                        System.out.println("NO LETTERS REMAINING");
                    }

                    pool.deleteLetters(toLowerCase);
                    System.out.println("Your Current Score Is: " + pool.getScore());
                    pool.addRound();
                } else {
                    pool.addRound();
                    System.out.println("Your Current Score Is: " + pool.getScore());
                }
            }
            pool.setInPool(false);
            pool.setInDict(false);
        }
    }

    private static boolean isInDictionaryAndPool(Words pool, String word) {
        boolean inDict = pool.inDict(word);
        boolean inPool = pool.inPool(word);

        if (!inPool) {
            System.out.println("The Characters Entered Are Not Valid");
        }

        if (!inDict) {
            System.out.println("The Word Entered Was Not Found in the Dictionary");
        }
        return inDict && inPool;
    }
}
