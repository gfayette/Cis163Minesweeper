package Project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.*;

/**********************************************************************
 * CIS 163 Section 01
 * Project 2: The Mine Sweeper Game
 * MineSweeperPanel Class
 *
 * This class creates and updates GUI objects for the game.  It
 * contains a displayBoard method for updating the GUI and a
 * Mouse subclass which implements the MouseListener interface.
 *
 * @author George Fayette
 * @version 2/10/2019
 *********************************************************************/
public class MineSweeperPanel extends JPanel {

    /**
     * Private JButton[][] containing each tile in the game
     */
    private JButton[][] board;

    /**
     * Private JButton used for the "Quit" button
     */
    private JButton quitButton;

    /**
     * Private JLabel for displaying the number of wins
     */
    private JLabel winLabel;

    /**
     * Private JLabel for displaying the number of loses
     */
    private JLabel loseLabel;

    /**
     * Private JLabel for displaying the game status face
     */
    private JLabel faceLabel;

    /**
     * Private Cell representing a particular game cell
     */
    private Cell iCell;

    /**
     * Private integer representing the size of the game board
     */
    private int boardSize;

    /**
     * Private integer representing the number of wins
     */
    private int numWins;

    /**
     * Private integer representing the number of loses
     */
    private int numLoses;

    /**
     * Private MineSweeperGame class which manages the model for this
     * program
     */
    private MineSweeperGame game;

    /**
     * Private ImageIcon[] for storing game graphics
     */
    private ImageIcon[] images;

    /**
     * Private constant String[] for storing image file names
     */
    private final String[] fileNames =
            {"resources/0.png", "resources/1.png", "resources/2.png",
                    "resources/3.png", "resources/4.png",
                    "resources/5.png", "resources/6.png",
                    "resources/7.png", "resources/8.png",
                    "resources/f.png", "resources/m.png",
                    "resources/r.png", "resources/u.png",
                    "resources/w.png", "resources/xd.png",
                    "resources/xo.png", "resources/xs.png",
                    "resources/xw.png"};

    /**
     * Private constant int for storing tile size
     */
    private final int tileSize = 28;


    /*******************************************************************
     * Constructor creates GUI objects along with mineSweeperGame and
     * Mouse objects.  Adds mouse listeners to GUI objects and adds
     * all to contentPane.
     * @param size The size of the game board.
     * @param numMines The number of mines used for the game.
     ******************************************************************/
    public MineSweeperPanel(int size, int numMines) {
        // Set boardSize to size parameter.
        boardSize = size;
        // Initialize numWins to 0.
        numWins = 0;
        // Initialize numLoses to 0.
        numLoses = 0;
        // Initialize ImageIcon
        images = new ImageIcon[fileNames.length];

        // Attempt to load image files to images[]
        try {
            for (int i = 0; i < images.length; ++i) {
                images[i] = new ImageIcon(ImageIO.read(
                        getClass().getResource(fileNames[i]))
                        .getScaledInstance(tileSize, tileSize,
                                Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            // Catch error reading image files
            System.out.println("error reading image files");
        }

        // Create game, listeners
        game = new MineSweeperGame(boardSize, numMines);
        Mouse mouseListener = new Mouse();

        // Create JPanels
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        JPanel center = new JPanel();

        // Create top panel
        top.setLayout(new GridLayout(2, 1));
        Font titleFont = new Font("Courier", Font.PLAIN, 20);
        JLabel titleLabel = new JLabel("!!! MINESWEEPER !!!");
        titleLabel.setFont(titleFont);
        faceLabel = new JLabel(images[16]);
        top.add(titleLabel);
        top.add(faceLabel);

        // Create the board
        center.setLayout(new GridLayout(boardSize, boardSize));
        board = new JButton[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                // Create JButton for tile
                board[row][col] = new JButton(images[0]);
                board[row][col].setMargin(null);
                board[row][col].setBorder(null);
                // Add mouse listener
                board[row][col].addMouseListener(mouseListener);
                // Add to JPanel
                center.add(board[row][col]);
            }
        }

        // Create bottom panel
        bottom.setLayout(new GridLayout(3, 1));
        quitButton = new JButton("Quit");
        quitButton.addMouseListener(mouseListener);
        winLabel = new JLabel("Wins: " + numWins);
        loseLabel = new JLabel("Loses: " + numLoses);
        bottom.add(quitButton);
        bottom.add(winLabel);
        bottom.add(loseLabel);

        // add all to contentPane
        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        // Set board to initial state
        displayBoard();
    }

    /*******************************************************************
     * This method updates the board to reflect the current game status.
     ******************************************************************/
    private void displayBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                iCell = game.getCell(r, c);
                board[r][c].setIcon(images[12]);

                // Set image for all mines
                if (iCell.isMine()) {
                    board[r][c].setIcon(images[10]);
                }

                // Set images for all flagged tiles
                if (iCell.isFlagged()) {
                    // Set flag image
                    board[r][c].setIcon(images[9]);
                    if (game.getGameStatus() == GameStatus.Lost &&
                            !iCell.isMine()) {
                        // Show incorrect mines
                        board[r][c].setIcon(images[13]);
                    }
                }

                // Set images for exposed tiles
                if (iCell.isExposed()) {
                    if (iCell.isMine()) {
                        board[r][c].setIcon(images[10]);
                    } else {
                        board[r][c]
                                .setIcon(images[iCell.getMineCount()]);
                    }
                }
            }
        }
    }

    /*******************************************************************
     * Private class which implements MouseListener for handling
     * input from mouse.
     ******************************************************************/
    private class Mouse implements MouseListener {

        /***************************************************************
         * This method is called when a mouse button is clicked.
         * @param e A MouseEvent object containing information about
         *          the click event.
         **************************************************************/
        public void mouseClicked(MouseEvent e) {
        }

        /***************************************************************
         * This method is called when a mouse button is pressed.
         * @param e A MouseEvent object containing information about
         *          the pressed event.
         **************************************************************/
        public void mousePressed(MouseEvent e) {
            for (int r = 0; r < boardSize; r++) {
                for (int c = 0; c < boardSize; c++) {
                    // If the left mouse button is clicked over a tile
                    // that is not flagged or exposed
                    if (board[r][c] == e.getSource() &&
                            e.getButton() == MouseEvent.BUTTON1 &&
                            !game.getCell(r, c).isFlagged() &&
                            !game.getCell(r, c).isExposed()) {
                        // Update game status face
                        faceLabel.setIcon(images[15]);
                    }
                }
            }
        }

        /***************************************************************
         * This method is called when a mouse button is released.
         * @param e A MouseEvent object containing information about
         *          the released event.
         **************************************************************/
        public void mouseReleased(MouseEvent e) {
            JButton selectedButton = new JButton();
            // If the mouse is left-clicked
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (int r = 0; r < boardSize; r++) {
                    for (int c = 0; c < boardSize; c++) {
                        // If a game tile was clicked
                        if (board[r][c] == e.getSource()) {
                            // Select clicked cell
                            game.select(r, c);
                            // Set selected button
                            selectedButton = board[r][c];
                        }
                    }
                }

                // If quit button is clicked
                if (quitButton == e.getSource()) {
                    // Ask for confirmation first
                    String s = "Are you sure?";
                    if (JOptionPane.showConfirmDialog(null, s, s,
                            JOptionPane.YES_NO_OPTION) == 0) {
                        // Exit program if confirmed
                        System.exit(0);
                    }
                }
            }

            // If the mouse is right-clicked
            if (e.getButton() == MouseEvent.BUTTON3) {
                for (int r = 0; r < boardSize; r++) {
                    for (int c = 0; c < boardSize; c++) {
                        // If a game tile was clicked
                        if (board[r][c] == e.getSource()) {
                            iCell = game.getCell(r, c);
                            if (!iCell.isFlagged()) {
                                // Flag cell if it is not already
                                // flagged
                                iCell.setFlagged(true);
                            } else {
                                // Un-flag cell if it is already flagged
                                iCell.setFlagged(false);
                            }
                        }
                    }
                }
            }

            // If the game is lost
            if (game.getGameStatus() == GameStatus.Lost) {
                // Show final board
                displayBoard();
                selectedButton.setIcon(images[11]);
                // Update game status face
                faceLabel.setIcon(images[14]);
                // Display loss message
                JOptionPane.showMessageDialog(null,
                        "You Lose \n The game will reset");
                // Update losses
                ++numLoses;
                loseLabel.setText("Loses: " + numLoses);
                // Reset game
                game.reset();
            }

            // If the game is won
            if (game.getGameStatus() == GameStatus.Won) {
                // Update game status face
                faceLabel.setIcon(images[17]);
                // Show final board
                displayBoard();
                // Display win message
                JOptionPane.showMessageDialog(null,
                        "You Win: all mines have been found!" +
                                "\n The game will reset");
                // Update wins
                ++numWins;
                winLabel.setText("Wins: " + numWins);
                // Reset game
                game.reset();
            }

            // Set default game status face
            faceLabel.setIcon(images[16]);
            // Update board
            displayBoard();
        }

        /***************************************************************
         * This method is called when a mouse button is entered.
         * @param e A MouseEvent object containing information about
         *          the entered event.
         **************************************************************/
        public void mouseEntered(MouseEvent e) {
        }

        /***************************************************************
         * This method is called when a mouse button is exited.
         * @param e A MouseEvent object containing information about
         *          the exited event.
         **************************************************************/
        public void mouseExited(MouseEvent e) {
        }
    }
}