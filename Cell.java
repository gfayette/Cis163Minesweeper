package Project2;

/**********************************************************************
 * CIS 163 Section 01
 * Project 2: The Mine Sweeper Game
 * Cell Class
 *
 * This class is used to represent an individual game tile.  It has
 * four instance variables: mineCount, isFlagged, isExposed, and
 * isMine, with getter and setter methods for each.
 *
 * @author George Fayette
 * @version 2/10/2019
 *********************************************************************/
public class Cell {
    /**
     * Private int representing the number of adjacent mines
     */
    private int mineCount;

    /**
     * Private boolean, true if the Cell is flagged
     */
    private boolean isFlagged;

    /**
     * Private boolean, true if the Cell is exposed
     */
    private boolean isExposed;

    /**
     * Private boolean, true if the Cell is a mine
     */
    private boolean isMine;

    /*******************************************************************
     * Constructor initializes all instance variables.
     * @param count Sets the initial value of mineCount.
     * @param flagged Sets the initial value of isFlagged.
     * @param exposed Sets the initial value of isExposed.
     * @param mine Sets the initial value of isMine.
     ******************************************************************/
    public Cell(int count, boolean flagged, boolean exposed,
                boolean mine) {
        mineCount = count;
        isFlagged = flagged;
        isExposed = exposed;
        isMine = mine;
    }

    /*******************************************************************
     * This method returns the current value of mineCount.
     * @return An int representing the number of adjacent mines.
     ******************************************************************/
    public int getMineCount() {
        return mineCount;
    }

    /*******************************************************************
     * This method sets mineCount to the parameter value.
     * @param count An int representing the number of adjacent mines.
     ******************************************************************/
    public void setMineCount(int count) {
        mineCount = count;
    }

    /*******************************************************************
     * This method returns the current value of isFlagged.
     * @return A boolean which is true if the Cell is flagged.
     ******************************************************************/
    public boolean isFlagged() {
        return isFlagged;
    }

    /*******************************************************************
     * This method sets isFlagged to the parameter value.
     * @param flagged A boolean which is true if the Cell is flagged.
     ******************************************************************/
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /*******************************************************************
     * This method returns the current value of isExposed.
     * @return A boolean which is true if the Cell is Exposed.
     ******************************************************************/
    public boolean isExposed() {
        return isExposed;
    }

    /*******************************************************************
     * This method sets isExposed to the parameter value.
     * @param exposed A boolean which is true if the Cell is exposed.
     ******************************************************************/
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /*******************************************************************
     * This method returns the current value of isMine.
     * @return A boolean which is true if the Cell is a mine.
     ******************************************************************/
    public boolean isMine() {
        return isMine;
    }

    /*******************************************************************
     * This method sets isMine to the parameter value.
     * @param mine A boolean which is true if the Cell is a mine.
     ******************************************************************/
    public void setMine(boolean mine) {
        isMine = mine;
    }
}