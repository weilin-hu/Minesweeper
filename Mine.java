public class Mine {
    private char charState; // field represents the state of the tile
    private int xIndex; // holds the x coor in array
    private int yIndex; // holds y coor in array
    private boolean isRevealed; // boolean for whether tile has been opened
    /* This is the constructor.
     * It takes in three inputs, the state that it's in
     * and the coordinates in the 2D array that the tile 
     * is located.
     * 
     * It initializes its fields fields according to the inputs
     * with a default 'false' for isRevealed (i.e. the tile
     * should not be revealed()) and draws a square at 
     * the location of the tile
     */
    public Mine(char charState, int xIndex, int yIndex) {
        // initialize variables
        this.charState = charState;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        isRevealed = false;
        
        // draw gray tile for the class Mine
        PennDraw.setPenColor(PennDraw.LIGHT_GRAY);
        PennDraw.filledSquare(xIndex + 0.5, yIndex + 0.5, 0.49);
    }
    /* This is a getter method for private field xIndex
     * 
     * Inputs: none
     * Outputs: integer represents coordinate x
     * and the first index in 2d array
     * 
     */
    public int getX() {
        return xIndex;
    }
    /* This is a getter method for private field yIndex
     * 
     * Inputs: none
     * Outputs: integer representing coordinate y
     * and the second index in 2D array
     * 
     */
    public int getY() {
        return yIndex;
    }
    /* This is a getter method for private field isRevealed
     * 
     * Inputs: none
     * Outputs: boolean true if the tile has been revealed
     * boolean false if it has not
     */
    public boolean isRevealed() {
        return isRevealed;
    }
    /*This is a getter method for private field isRevealed
     * 
     * Inputs: none
     * Outputs: a char representing the state of the tile
     * '1' - '8' for numbertiles, M for mines, and B for blank
     */
    public char getCharState() {
        return charState;
    }
    /* This function helped with analyzing whether a tile
     * has a mine or not.
     * 
     * Inputs: none
     * Outputs: boolean true if mine has mine
     * boolean false if mine is empty
     */
    public boolean hasMine() {
        // if Mine has mine
        if (charState == 'M') {
            return true; 
        } 
        return false;
    }
    /* This method helps in changing the state of the tiles
     * when the field is created and mines are placed on
     * the board.
     * 
     * Input: char representing the new state of the tile
     * Output: void
     * Product: the charState field is changed to the 
     * corresponding input
     */
    public void changeCharState(char newCharState) {
        // field charState is set to new charState
        charState = newCharState;
    }
    /* The method reveals the tile that is pressed
     * 
     * Input: none
     * Output: void
     * Byproduct: This method opens the tile that is 
     * pressed an reveals either 
     * 1) nothing if the tile has already been revealed
     * 2) a number representing # of proximity mines
     * 3) blank tile for no proximity mines
     * 4) a red circle for a mine
     * 
     * Once activated, the tile that is revealed now
     * has boolean isRevealed to be true.
     */
    public void revealState() {
        // don't reveal if tile is already revealed
        if (isRevealed()) {
            return;
        }
        // if executed, isRevealed is true
        isRevealed = true;
        
        // base change the tile to a darker color
        PennDraw.setPenColor(PennDraw.DARK_GRAY);
        PennDraw.filledSquare(xIndex + 0.5, yIndex + 0.5, 0.49);
        
        // if tile has mine, draw a red circle
        if (charState == 'M') {
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.filledCircle(xIndex + 0.5, yIndex + 0.5, 0.20);
        } else if (charState == 'B') {
            // leave blank
        } else {
            /* for tile has at least one mine around it
             * draw the corresponding number of mines around it
             * and corresponding color
             */
            PennDraw.setFontSize(30);
            String s = charState + "";
            if (charState == '1') {
                PennDraw.setPenColor(PennDraw.BLUE);
                PennDraw.text(xIndex + 0.5, yIndex + 0.5, s);
            } else if (charState == '2') {
                PennDraw.setPenColor(PennDraw.GREEN);
                PennDraw.text(xIndex + 0.5, yIndex + 0.5, s);
            } else if (charState == '3') {
                PennDraw.setPenColor(PennDraw.RED);
                PennDraw.text(xIndex + 0.5, yIndex + 0.5, s);
            } else if (charState == '4') {
                PennDraw.setPenColor(PennDraw.MAGENTA);
                PennDraw.text(xIndex + 0.5, yIndex + 0.5, s);
            } else {
                PennDraw.setPenColor(PennDraw.CYAN);
                PennDraw.text(xIndex + 0.5, yIndex + 0.5, s);
            }
        }
    }
}
