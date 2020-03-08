package Project2;

import javax.swing.*;

/**********************************************************************
 * CIS 163 Section 01
 * Project 2: The Mine Sweeper Game
 * MineSweeperGUI Class
 *
 * This class starts the program by prompting the user to enter a valid
 * board size and number of mines for the game.  It then creates a
 * MineSweeperPanel using the user input and adds it to a JFrame
 * object.
 *
 * @author George Fayette
 * @version 2/10/2019
 *********************************************************************/
public class MineSweeperGUI {

    /*******************************************************************
     * This is a static main method for starting the program.  It
     * prompts the user to enter the board size and number of mines,
     * then creates a MineSweeperPanel and JFrame.
     * @param args a String array
     ******************************************************************/
    public static void main(String[] args) {
        // String for storing the user input for board size
        String size;
        // Int for storing the valid board size
        int boardSize = 0;
        // Constants for window size and board size
        final int MINIMUM_BOARD_SIZE = 3;
        final int MAXIMUM_BOARD_SIZE = 30;
        final int TILE_LENGTH = 28;
        final int WIDTH_PADDING = 40;
        final int HEIGHT_PADDING = 230;

        // While the user has not entered a valid board size
        boolean validBoardSize = false;
        while (!validBoardSize) {
            // Ask user for board size
            size = JOptionPane.showInputDialog(null,
                    "Enter in the size of the board: ");

            // Exit if cancel button was selected
            if (size == null) {
                System.exit(0);
            }

            try {
                // Attempt to parse int
                boardSize = Integer.parseInt(size);
                // Check for valid board size
                if (boardSize < MINIMUM_BOARD_SIZE ||
                        boardSize > MAXIMUM_BOARD_SIZE) {
                    throw new Exception();
                }
                // Update validBoardSize if valid input
                validBoardSize = true;
            } catch (Exception e) {
                // Display error message, exit if cancel button is
                // selected
                if (JOptionPane.showConfirmDialog(null,
                        "Please enter a number between 3 and 30",
                        "Invalid input",
                        JOptionPane.OK_CANCEL_OPTION) == 2) {
                    System.exit(0);
                }
            }
        }

        // String for storing user input for number of mines
        String mines;
        // Int for storing a valid number of mines
        int numMines = 0;

        // While the user has not entered a valid number of mines
        boolean validNumberOfMines = false;
        while (!validNumberOfMines) {
            // Ask user for number of mines
            mines = JOptionPane.showInputDialog(null,
                    "Enter in the number of mines: ");
            // Exit if cancel button was selected
            if (mines == null) {
                System.exit(0);
            }

            try {
                // Attempt to parse int
                numMines = Integer.parseInt(mines);
                // Check for valid number of mines
                if (numMines < 0 || numMines >= boardSize * boardSize) {
                    throw new Exception();
                }
                // Update validNumberOfMines if valid input
                validNumberOfMines = true;
            } catch (Exception e) {
                // Display error message, exit if cancel button is
                // selected
                if (JOptionPane.showConfirmDialog(null,
                        "Please enter a valid number of mines",
                        "Invalid input",
                        JOptionPane.OK_CANCEL_OPTION) == 2) {
                    System.exit(0);
                }
            }
        }

        // Create MineSweeperPanel object using user input as arguments
        MineSweeperPanel panel =
                new MineSweeperPanel(boardSize, numMines);

        // Create JFrame
        JFrame frame = new JFrame("Mine Sweeper!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.setSize(boardSize * TILE_LENGTH + WIDTH_PADDING,
                boardSize * TILE_LENGTH + HEIGHT_PADDING);
        frame.setVisible(true);
    }
}