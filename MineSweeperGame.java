package Project2;

import java.util.Random;

/**********************************************************************
 * CIS 163 Section 01
 * Project 2: The Mine Sweeper Game
 * MineSweeperGame Class
 *
 * This class manages the model for the mine sweeper game.  It
 * creates a two-dimensional array of Cell objects representing the
 * game board and contains methods for manipulating these objects.
 *
 * @author George Fayette
 * @version 2/10/2019
 *********************************************************************/
public class MineSweeperGame {

    /**
     * Private Cell[][] representing each tile in the game
     */
    private Cell[][] board;

    /**
     * Private GameStatus for storing the current status of the game
     */
    private GameStatus status;

    /**
     * Private int representing the number of mines in the game
     */
    private int mineCount;

    /**
     * Private int representing the size of the board
     */
    private int boardSize;

    /**
     * Private int representing the number of game tiles which do not
     * contain a mine
     */
    private int nonMineCells;

    /**
     * Private int representing the number of game tiles which have been
     * revealed
     */
    private int revealedCells;

    /**
     * Private boolean representing whether the first game tile has been
     * clicked
     */
    private boolean gameStarted;

    /*******************************************************************
     * Constructor sets mineCount and boardSize to parameter values.
     * Sets game to its initial state.
     * @param size The size of the game board.
     * @param mines The number of mines used for the game.
     ******************************************************************/
    public MineSweeperGame(int size, int mines) {
        // Set mineCount and boardSize to parameter values
        mineCount = mines;
        boardSize = size;

        // Calculate the value of nonMineCells
        nonMineCells = boardSize * boardSize - mineCount;
        // Set revealedCells to initial value
        revealedCells = 0;
        // Set status to initial value
        status = GameStatus.NotOverYet;
        // Create board
        board = new Cell[boardSize][boardSize];

        // Set board to initial values and initialize gameStarted
        setEmpty();
        gameStarted = false;
    }


    /*******************************************************************
     * This method initializes every Cell in board to starting values
     * of 0 adjacent mines, not flagged, not exposed, and not a mine.
     ******************************************************************/
    private void setEmpty() {
        for (int r = 0; r < boardSize; r++)
            for (int c = 0; c < boardSize; c++)
                // Initialize all Cells
                board[r][c] = new Cell(0, false, false, false);
    }


    /*******************************************************************
     * This method returns the Cell in board in a given row and column.
     * @param row The row of the Cell being requested.
     * @param col The column of the Cell being requested.
     * @return The Cell being requested.
     ******************************************************************/
    public Cell getCell(int row, int col) {
        return board[row][col];
    }


    /*******************************************************************
     * This method is called when a game tile is selected.  If the
     * associated Cell is not flagged, then the game will attempt to
     * expose the Cell.  If the newly exposed Cell has zero adjacent
     * mines, then the game will reveal adjacent Cells and zeros.
     * Finally, this method updates the game status if necessary.
     * @param row The size of the game board.
     * @param col The number of mines used for the game.
     ******************************************************************/
    public void select(int row, int col) {
        // If the first tile is being selected
        if (!gameStarted) {
            layMines(mineCount, row, col);
            gameStarted = true;
        }

        // If the player exposes a new game tile
        if (exposeCell(row, col)) {
            // If the player selects a mine
            if (board[row][col].isMine()) {
                // Player loses the game
                status = GameStatus.Lost;
                // For every Cell in board
                for (int r = 0; r < boardSize; r++) {
                    for (int c = 0; c < boardSize; c++) {
                        // If the Cell is a mine
                        if (board[r][c].isMine()) {
                            // Expose and un-flag mine
                            board[r][c].setExposed(true);
                            board[r][c].setFlagged(false);

                        }
                    }
                }
            }

            // Reveal adjacent cells and zeros
            if (board[row][col].getMineCount() == 0 &&
                    !board[row][col].isMine()) {
                //revealAdjacentCellsRecursive(row, col);
                revealAdjacentCellsNonRecursive();
                //revealAdjacentCellsNonRecursive2(row, col);
            }

            // If all tiles that are not mines have been exposed
            if (revealedCells == nonMineCells) {
                // Player wins the game
                status = GameStatus.Won;
                // For every Cell in board
                for (int r = 0; r < boardSize; r++) {
                    for (int c = 0; c < boardSize; c++) {
                        // If the Cell is a mine
                        if (board[r][c].isMine()) {
                            // Flag mine
                            board[r][c].setFlagged(true);
                        }
                    }
                }
            }
        }
    }

    /*******************************************************************
     * This method returns the current game status.
     * @return A GameStatus Enum representing the current status of
     * the game.
     ******************************************************************/
    public GameStatus getGameStatus() {
        return status;
    }

    /*******************************************************************
     * This method sets the game to its initial state.
     ******************************************************************/
    public void reset() {
        // Set status and revealedCells to initial values
        status = GameStatus.NotOverYet;
        revealedCells = 0;

        // Set board to initial values and reset gameStarted
        setEmpty();
        gameStarted = false;
    }

    /*******************************************************************
     * This method lays all mines on the game board and calls the
     * setMineCounts method.
     * @param totalMineCount An int representing the total number of
     *                       mines in the game.
     * @param row An int representing the row of the first click
     * @param col An int representing the column of the first click
     ******************************************************************/
    private void layMines(int totalMineCount, int row, int col) {
        // minesDown represents the number of mines that have been
        // placed.  Initialize to zero.
        int minesDown = 0;
        Random random = new Random();

        // While there are still mines to be placed
        while (minesDown < totalMineCount) {
            // Generate random location for a mine
            int c = random.nextInt(boardSize);
            int r = random.nextInt(boardSize);

            // Lay mine if the location does not already have a mine
            // and is not the first selected game tile
            if (!board[r][c].isMine() && !(r == row && c == col)) {
                board[r][c].setMine(true);
                ++minesDown;
            }
        }
        // Set the number of adjacent mines for all Cells
        setMineCounts();
    }

    /*******************************************************************
     * This method sets the number of adjacent mines for all Cells in
     * the game.
     ******************************************************************/
    private void setMineCounts() {
        // For every Cell in board
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                // If the Cell is not a mine
                if (!board[r][c].isMine()) {
                    // Set mine count to the number of adjacent mines
                    board[r][c].setMineCount(getAdjacentMines(r, c));
                }
            }
        }
    }

    /*******************************************************************
     * This method returns the number of adjacent mines for a given
     * Cell.
     * @param row The row of the Cell for which the number of
     *            adjacent mines is being requested.
     * @param col The column of the Cell for which the number of
     *            adjacent mines is being requested.
     * @return An int representing he number of adjacent mines.
     ******************************************************************/
    private int getAdjacentMines(int row, int col) {
        // Initialize the number of adjacent mines to zero
        int numMines = 0;
        // For each adjacent Cell
        for (int r = row - 1; r <= row + 1; ++r) {
            for (int c = col - 1; c <= col + 1; ++c) {
                // If the adjacent cell exists and is a mine
                if (isValidCell(r, c) && board[r][c].isMine()) {
                    // Increment numMines
                    ++numMines;
                }
            }
        }
        return numMines;
    }

    /*******************************************************************
     * This method determines if a given row and column represents a
     * valid game tile.
     * @param row The row which is being checked.
     * @param col The column which is being checked.
     * @return A boolean value for whether or not the
     * parameters row and col represent a valid cell.
     ******************************************************************/
    private boolean isValidCell(int row, int col) {
        // If row and col are between zero and the board size
        if (row >= 0 && row < boardSize && col >= 0 &&
                col < boardSize) {
            return true;
        }
        return false;
    }

    /*******************************************************************
     * This method attempts to expose a Cell in a given row and column
     * @param row The row which is being exposed
     * @param col The column which is being exposed.
     * @return A boolean value representing whether or not the
     * cell was exposed.
     ******************************************************************/
    private boolean exposeCell(int row, int col) {
        // If row and col represent a valid cell and that cell is not
        // already exposed
        if (isValidCell(row, col) && !board[row][col].isExposed() &&
                !board[row][col].isFlagged()) {
            // Set exposed
            board[row][col].setExposed(true);
            // Increment revealed Cells
            if (!board[row][col].isMine()) {
                ++revealedCells;
            }
            return true;
        }
        return false;
    }


    /*******************************************************************
     * This is a recursive method for revealing all adjacent Cells
     * and zeros.
     * @param row The row of the Cell which is having all adjacent
     *            Cells revealed.
     * @param col The column of the Cell which is having all adjacent
     *            Cells revealed.
     ******************************************************************/
    private void revealAdjacentCellsRecursive(int row, int col) {
        // For all adjacent Cells
        for (int r = row - 1; r <= row + 1; ++r) {
            for (int c = col - 1; c <= col + 1; ++c) {
                // Expose Cell
                if (exposeCell(r, c)) {
                    // If the newly exposed cell has zero adjacent mines
                    if (board[r][c].getMineCount() == 0) {
                        // Reveal all adjacent Cells
                        revealAdjacentCellsRecursive(r, c);
                    }
                }
            }
        }
    }

    /*******************************************************************
     * This is a non-recursive method for revealing all adjacent
     * Cells and zeros.
     ******************************************************************/
    private void revealAdjacentCellsNonRecursive() {
        // Boolean representing whether or not more Cells have been
        // revealed
        boolean moreCellsRevealed = true;
        // While more Cells have not been revealed
        while (moreCellsRevealed == true) {
            moreCellsRevealed = false;
            // For every Cell in the game
            for (int r = 0; r < boardSize; r++) {
                for (int c = 0; c < boardSize; c++) {
                    // If the Cell has zero adjacent mines and has
                    // been exposed
                    if (board[r][c].getMineCount() == 0 &&
                            board[r][c].isExposed()) {
                        // Attempt to reveal all adjacent Cells
                        if (revealAdjacentCells(r, c)) {
                            // If more Cells have been revealed then
                            // continue loop
                            moreCellsRevealed = true;
                        }
                    }
                }
            }
        }
    }

    /*******************************************************************
     * This method attempts to reveal all adjacent Cells
     * @param row The row of the Cell which is having all adjacent
     *            Cells revealed.
     * @param col The column of the Cell which is having all adjacent
     *            Cells revealed.
     * @return A boolean value representing whether or not more Cells
     * have been revealed.
     ******************************************************************/
    private boolean revealAdjacentCells(int row, int col) {
        // Initialize moreCellsRevealed to false
        boolean moreCellsRevealed = false;
        // For all adjacent Cells
        for (int r = row - 1; r <= row + 1; ++r) {
            for (int c = col - 1; c <= col + 1; ++c) {
                // Attempt to expose Cell
                if (exposeCell(r, c)) {
                    // If more cells have been revealed then set
                    // return value to zero
                    moreCellsRevealed = true;
                }
            }
        }
        return moreCellsRevealed;
    }

    /*******************************************************************
     * This is a non-recursive method for revealing all adjacent Cells
     * and zeros.
     * @param row The row of the Cell which is having all adjacent
     *            Cells revealed.
     * @param col The column of the Cell which is having all adjacent
     *            Cells revealed.
     ******************************************************************/
    private void revealAdjacentCellsNonRecursive2(int row, int col) {
        // An array for storing the locations of all revealed Cells
        // with zero adjacent mines
        int[][] zeroLocations = new int[nonMineCells + 1][2];

        // Initialize array with -1 for the row location
        for (int[] zeroLocation : zeroLocations) {
            zeroLocation[0] = -1;
        }

        // Set location of first zero to parameter values
        zeroLocations[0][0] = row;
        zeroLocations[0][1] = col;

        // Array index used to reveal all adjacent Cells for every
        // location in the array
        int i = 0;
        // Array index used to place the location of the next revealed
        // zero in the array
        int numZeros = 1;
        // While there are more zero locations in the array
        while (row != -1) {
            // For all adjacent Cells
            for (int r = row - 1; r <= row + 1; ++r) {
                for (int c = col - 1; c <= col + 1; ++c) {
                    // Attempt to expose Cell
                    if (exposeCell(r, c)) {
                        // If the newly exposed Cell has zero
                        // adjacent mines
                        if (board[r][c].getMineCount() == 0) {
                            // Add the location of the Cell to the array
                            zeroLocations[numZeros][0] = r;
                            zeroLocations[numZeros][1] = c;
                            // Increment index
                            ++numZeros;
                        }
                    }
                }
            }
            // Increment index
            ++i;
            // Prepare loop for next zero location
            row = zeroLocations[i][0];
            col = zeroLocations[i][1];
        }
    }
}