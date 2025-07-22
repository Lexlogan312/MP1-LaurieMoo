/**
 * RandomMooValue class for the LaurieMOO game.
 * The RandomMooValue class generates the secret value, calculates the number of big and little MOOs,
 * and checks if the player's guess matches the secret value.
 *
 * @author: Alex Randall
 * @version: 1.0
 * @date: 02/18/2025
 */

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Collections;

// RandomMooValue class for the LaurieMOO game
public class RandomMooValue {
    // Arrays to store the guess value and secret value
    private int[] guessValue;
    private int[] secretValue;

    // Array to keep track of used digits in the secret value
    private boolean[] bigMoo = new boolean[4];

    // Counters for big and little MOOs
    private int bigMooCount = 0;
    private int littleMooCount = 0;

    // Counter for the number of guess attempts
    private int guessAttempt = 1;

    /**
     * Constructor to initialize the guess and secret value arrays.
     */
    public RandomMooValue(){
        guessValue = new int[4];
        secretValue = new int[4];
    }

    /**
     * Calculates the number of big MOOs (correct digits in the correct positions)
     * in the current guess compared to the secret value.
     *
     * @param guess The 4-digit integer value guessed by the player.
     * @return The number of big MOOs (digits that are correct and in the correct positions).
     */
    public int getBigMooCount(int guess){
        bigMooCount = 0;

        // Convert the guess to an array
        guessValue = getArray(guess);

        // Compare each digit of the guess with the secret value
        for(int i = 0; i < guessValue.length; i++){
            // If the digit matches, increment the big moo count
            if(guessValue[i] == secretValue[i]){
                bigMooCount++;

                // Mark the digit as a big moo
                bigMoo[i] = true;
            }
            else{
                bigMoo[i] = false;
            }
        }
        return bigMooCount;
    }

    /**
     * Calculates the number of little moos (correct digits in the wrong positions)
     * in the current guess compared to the secret value.
     *
     * @param guess The 4-digit integer value guessed by the player.
     * @return The number of little moos (digits that are correct but in the wrong positions).
     */
    public int getLittleMooCount(int guess) {
        littleMooCount = 0;

        // Convert the guess to an array
        guessValue = getArray(guess);

        // Check for little moos
        for(int i = 0; i < guessValue.length; i++){

            // Check if the digit is not a big moo (not in the same position)
            if(guessValue[i] != secretValue[i]){
                for(int j = 0; j < secretValue.length; j++){

                    // Check if the digit is in the secret value and not already counted as a big moo
                    if(!bigMoo[j] && guessValue[i] == secretValue[j]){

                        // Increment the little moo count and mark the digit as used
                        littleMooCount++;
                        bigMoo[j] = true;
                        break;
                    }
                }
            }
        }
        return littleMooCount;
    }

    /**
     * Converts the secret value from an array of digits to a 4-digit integer.
     *
     * @return The secret value as a 4-digit integer.
     */
    public int getSecretValue(){
        int value = 0;

        // Convert the secret value array to an integer by multiplying each digit by its place value
        for(int i = 0; i < secretValue.length; i++){
            value += secretValue[i] * Math.pow(10, 3 - i);
        }
        return value;
    }

    /**
     * Checks if the player's guess matches the secret value.
     * Displays a winning message if the guess is correct.
     *
     * @param guess The 4-digit integer value guessed by the player.
     * @return True if the guess matches the secret value, otherwise false.
     */
    public boolean isCorrectGuess(int guess){
        // If the guess is correct, show LaurieMOO message and return true
        if(guess == getSecretValue()){
            JOptionPane.showMessageDialog(null, "LaurieMOO!!!");
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Sets the secret value to a random 4-digit number.
     *
     * @return True once the secret value is successfully generated.
     */
    public boolean setSecretValue(){
        // Generate a random 4-digit number
        for(int i = 0; i < secretValue.length; i++){
            secretValue[i] = (int)(Math.random() * 10);
        }
        return true;
    }

    /**
     * Sets the secret value to a specific 4-digit number provided by the user.
     *
     * @param n The specific 4-digit integer to set as the secret value.
     * @return True if the value is set successfully, false if the number is not 4 digits.
     */
    public boolean setSecretValue(int n) {
        // Check if the value is a 4-digit number
        if(!(0 <= n && n <= 9999)){
            return false;
        }
        // Convert the value to an array
        secretValue = getArray(n);
        return true;
    }

    /**
     * Converts a 4-digit integer into an array of its digits.
     *
     * @param n The 4-digit integer to be converted.
     * @return An array of 4 digits representing the integer.
     */
    public int[] getArray(int n){
        int[] value = new int[4];
        // Extract each digit of the integer
        for(int i = value.length - 1; i >= 0; i--){
            value[i] = n % 10;
            n /= 10;
        }
        return value;
    }

    /**
     * Increments the counter for the number of guess attempts.
     */
    public void addGuessAttempt(){
        guessAttempt++;
    }

    /**
     * Retrieves the current number of guess attempts made by the player.
     *
     * @return The number of guess attempts.
     */
    public int getGuessAttempt(){
        return guessAttempt;
    }

    /**
     * Resets the guess attempt counter to one.
     */
    public void resetGuessAttempt(){
        guessAttempt = 1;
    }

    /**
     * Generates a shuffled string of big MOOs ("MOO!") and little moos ("moo.").
     * If no MOOs or moos are present, a cowbells message is shown.
     *
     * @return A string of randomized MOO and moo messages or a cowbells message.
     */
    public String mooText(){
        ArrayList<String> mooList = new ArrayList<String>();
        String mooText = "";
        // If there are no big or little moos, show the cowbells message
        if(bigMooCount == 0 && littleMooCount == 0){
            mooList.add("All you hear are cowbells.");
            mooText = mooList.get(0);
        }
        else{
            // Add big and little moos to the list
            for(int i = 0; i < bigMooCount; i++){
                mooList.add("MOO!");
            }
            for(int i = 0; i < littleMooCount; i++){
                mooList.add("moo.");
            }

            // Shuffle the list to randomize the order of messages
            Collections.shuffle(mooList);

            // Convert the array to a string
            for(int i = 0; i < mooList.size(); i++){
                mooText += mooList.get(i) + " ";
            }
        }
        return mooText;
    }
}