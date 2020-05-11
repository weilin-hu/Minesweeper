public class MineField {
    private Mine[][] mines;
    /* Constructor
     * 
     * Draws a black background and sets the field 2D array
     * of board of mines to a 9 by 9 grid.
     * It then creates 81 empty tiles on each place
     * in the grid.
     */
    public MineField() {
        // draws black background
        PennDraw.clear(PennDraw.BLACK);
        // creates a 9x9 double array of mines
        mines = new Mine[9][9];
        // each index is an empty mine
        for (int row = 0; row < mines.length; row++) {
            for (int col = 0; col < mines[0].length; col++) {
                mines[row][col] = new Mine('E', row, col);
            }
        }
    }
    /* This is a getter method for the private field mines
     * 
     * Input: none
     * output: Mine 2D array of the instance of minefield()
     */
    public Mine[][] getMines() {
        return mines;
    }
    /* This helper method counts the amount of mines in the board
     * 
     * Input: none
     * Output: an integer representing the amount of mines
     * that the board currently has.
     */
    public int countMines() {
        // instantiate accumulator numMines
        int numMines = 0;
        // iterate through all indices 
        for (int row = 0; row < mines.length; row++) {
            for (int col = 0; col < mines[0].length; col++) {
                if (mines[row][col].hasMine()) {
                    // if tile has a mine, add 1 to numMines
                    numMines++;
                }
            }
        }
        return numMines;
    }
    /* This method finds the state of the tile, i.e.
     * how many mines are on or around it.
     * 
     * Input: Two integers representing the x coor and 
     * y coor, or also the indices of the double array
     * Output: void
     * Byproduct: 
     */
    public void findState(int xIndex, int yIndex) {
        // instantiate two variables
        int numMines = 0;
        char newCharState = 'B';
        // if the index does not have a mine 
        if (!mines[xIndex][yIndex].hasMine()) {
            for (int i = xIndex - 1; i <= xIndex + 1; i++) {
                // continue if index if out of bounds
                if (i < 0 || i > 8) {
                    continue;
                }
                for (int j = yIndex - 1; j <= yIndex + 1; j++) {
                    // continue if index is out of bounds
                    if (j < 0 || j > 8) {
                        continue;
                        // continue if index is the input
                    } else if (i == xIndex && j == yIndex) {
                        continue;
                    } else {
                        // if there is a mine beside it, add 1 to numMines
                        if (mines[i][j].getCharState() == 'M') {
                            numMines++;
                        }
                    }
                }
            }
            // if there is a mine around the tile
            if (numMines > 0 && numMines < 9) {
                // new state is that number
                newCharState = (char) (numMines + 48);
            }
        } else {
            // if tile does have mine, label it with char M
            newCharState = 'M';
        }
        /* this changes the charState of the tile (which is 
         * at this moment supposed to be a double array of E's or M's
         * to the corresponding values found above
         */
        mines[xIndex][yIndex].changeCharState(newCharState);
    }
    /* This method puts 10 mines on the board upon mouseclick
     * 
     * Input: two integer indexes, x and y, that are the
     * coordinates where the mouse is pressed (to integer)
     * so it corresponds to the tile indices
     * Output: void
     * Byproduct: The 9x9 board gets filled with 10 mines,
     * at random locations except cannot be at the indices
     * in the input.
     * 
     */
    public void putMines(int x, int y) {
        // continue if there are not 10 mines
        while (countMines() < 10) {
            // random indices for mines [-1 to 8]
            int xIndex = (int) (Math.random() * 10) - 1;
            int yIndex = (int) (Math.random() * 10) - 1;
            // If index is nonnegative
            if (xIndex >= 0 && yIndex >= 0) {
                // If Index is not at the mouseclick
                if (xIndex != x && yIndex != y) {
                    // if Mine has not already been placed at location
                    if (!mines[xIndex][yIndex].hasMine()) {
                        // create a tile with a Mine
                        mines[xIndex][yIndex] = new Mine('M', xIndex, yIndex);
                    }
                }
            }
        }
        // iterate through all Mine tiles
        for (int j = 0; j < mines.length; j++) {
            for (int k = 0; k < mines[0].length; k++) {
                // if the Mine tile does not have a mine
                if (!mines[j][k].hasMine()) {
                    // then it is an empty Mine tile
                    mines[j][k] = new Mine('E', j, k);
                }
            }
        }
    }
    
    /* This method reveals the Mine tile that is pressed
     * and, based on its state, reveals different tiles.
     * 
     * Input: a Mine object which is the tile located at
     * the mouse click. 
     * Output: void
     * Byproduct: If center is a number tile, then that tile only
     * is revealed, if it's a blank, then reveals until
     * the surroudning tiles are all number tiles, and if
     * it's a tile with a mine, then it reveals all mines (in
     * Minesweeper.java this will end the game)
     */
    public void revealAround(Mine center) {
        // get the indices at the center Mine
        int x = center.getX();
        int y = center.getY();
        // if tile clicked is blank
        if (center.getCharState() == 'B') {
            // iterate through all tiles around it
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    // don't iterate out of bounds of board
                    if (i >= 0 && i <= 8 && j >= 0 && j <= 8) {
                        // if that tile hasn't already been revealed
                        if (!mines[i][j].isRevealed()) {
                            // reveal mine
                            mines[i][j].revealState();
                            // if there is blank around the blank tile
                            if (mines[i][j].getCharState() == 'B') {
                                // reveal around that blank as well
                                revealAround(mines[i][j]);
                            }
                        }
                    }
                }
            }
            // if mine tile is number tile 
        } else if (center.getCharState() != 'M') {
            // reveal that tile only
            center.revealState();
        } else {
            // iterate through all tiles 
            for (int i = 0; i < mines.length; i++) {
                for (int j = 0; j < mines[8].length; j++) {
                    // reveal all mines
                    if (mines[i][j].getCharState() == 'M') {
                        mines[i][j].revealState();
                    }
                }
            }
        }
    }
    // testing
    public static void main(String[] args) {
        /*
        PennDraw.setXscale(0, 9);
        PennDraw.setYscale(0, 9);
        PennDraw.clear(PennDraw.BLACK);
        MineField field = new MineField();
        field.putMines(4, 4);
        
        for (int row = 0; row < field.mines[0].length; row++) {
            for (int col = 0; col < field.mines.length; col++) {
                System.out.print(field.mines[row][col].getCharState() + ", ");
                field.findState(row, col);
            }
        }
        field.revealAround(field.mines[2][2]);
        field.revealAround(field.mines[5][8]);
        field.revealAround(field.mines[3][4]);
        field.revealAround(field.mines[7][0]);
        */
    }
}
