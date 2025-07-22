/**
 * Main class for the LaurieMOO game.
 * The main class creates the GUI for the game and handles the user input.
 *
 * @author: Alex Randall
 * @version: 1.0
 * @date: 02/18/2025
 */

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// Main class for the LaurieMOO game
public class Main {

    // Main application frame
    private JFrame frame;

    // Text fields for secret value and guess value
    private JTextField guessValueTextField;

    // Variable to store the secret value
    private int secretValue;

    // Instance of RandomMooValue class
    RandomMooValue randomMooValue = new RandomMooValue();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        randomMooValue.setSecretValue();
        secretValue = randomMooValue.getSecretValue();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Set up the main frame
        frame = new JFrame();
        frame.setTitle("LaurieMOO!");
        frame.getContentPane().setBackground(new Color(94, 94, 94));
        frame.setBounds(100, 100, 475, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // Label for guess value
        JLabel guessLabel = new JLabel("Guess #1");
        guessLabel.setForeground(new Color(254, 255, 255));
        guessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        guessLabel.setBounds(25, 33, 115, 29);
        frame.getContentPane().add(guessLabel);

        // Label to display a cow picture
        JLabel cowPicture = new JLabel("");
        cowPicture.setIcon(new ImageIcon(Main.class.getResource("/images/Cow.jpg")));
        cowPicture.setBounds(58, 173, 208, 189);
        frame.getContentPane().add(cowPicture);

        // Label to display the moo text
        JLabel mooLabel = new JLabel("");
        mooLabel.setForeground(new Color(254, 255, 255));
        mooLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mooLabel.setBounds(41, 118, 247, 29);
        frame.getContentPane().add(mooLabel);

        // Text area to display previous guesses
        JTextArea previousGuessesList = new JTextArea();
        previousGuessesList.setForeground(new Color(254, 255, 255));
        previousGuessesList.setBackground(new Color(145, 145, 145));
        previousGuessesList.setBounds(355, 125, 76, 146);
        frame.getContentPane().add(previousGuessesList);

        // Button to play a new game
        JButton newGame = new JButton("New Game!");
        newGame.setBackground(new Color(145, 145, 145));
        newGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset the game
                randomMooValue.setSecretValue();
                secretValue = randomMooValue.getSecretValue();
                previousGuessesList.setText("");
                mooLabel.setText("");
                guessValueTextField.setText("");
                newGame.setVisible(false);
                randomMooValue.resetGuessAttempt();
                guessLabel.setText("Guess #1");
            }
        });
        newGame.setForeground(new Color(145, 145, 145));
        newGame.setBounds(58, 374, 363, 35);
        newGame.setVisible(false);
        frame.getContentPane().add(newGame);

        // Text field for entering the guess value
        guessValueTextField = new JTextField();
        guessValueTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Check if the number of attempts is less than 10 and the guess is a 4-digit number
                if(randomMooValue.getGuessAttempt() < 10 && guessValueTextField.getText().length() == 4){
                    int guess = Integer.parseInt(guessValueTextField.getText());
                    // Add the guess to the previous guesses list
                    previousGuessesList.append(guessValueTextField.getText() + "\n");

                    // Check if the guess is correct
                    randomMooValue.isCorrectGuess(guess);

                    // Update the number of big and little MOOs
                    randomMooValue.getBigMooCount(guess);
                    randomMooValue.getLittleMooCount(guess);

                    // Update the moo text
                    mooLabel.setText(randomMooValue.mooText());

                    // Update the guess label
                    guessLabel.setText("Guess #" + (randomMooValue.getGuessAttempt()+1));

                    // Add a guess to the counter
                    randomMooValue.addGuessAttempt();
                }
                else if(randomMooValue.getGuessAttempt() == 10) {
                    // Show a message if the maximum number of attempts is reached
                    ImageIcon customIcon = new ImageIcon(Main.class.getResource("/images/Shocked_Cow.jpg"));
                    JOptionPane.showMessageDialog(null, "Boo hoo -- no LaurieMOO." + "\nThe secret value was " + String.format("%04d", secretValue), "Game Over!", JOptionPane.INFORMATION_MESSAGE, customIcon);

                    // Show the new game button
                    newGame.setVisible(true);
                }
            }
        });
        guessValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        guessValueTextField.setForeground(new Color(254, 255, 255));
        guessValueTextField.setBackground(new Color(145, 145, 145));
        guessValueTextField.setColumns(10);
        guessValueTextField.setBounds(152, 27, 136, 40);
        frame.getContentPane().add(guessValueTextField);

        // Label for previous guesses
        JLabel previousGuessLabel = new JLabel("Previous Guesses");
        previousGuessLabel.setForeground(new Color(254, 255, 255));
        previousGuessLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previousGuessLabel.setBounds(333, 89, 115, 40);
        frame.getContentPane().add(previousGuessLabel);
    }
}