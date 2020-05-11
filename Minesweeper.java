public class Minesweeper {
    public static void main(String[] args) {
        // at start no mines have been revealed
        int minesRevealed = 0;
        // set scale
        PennDraw.setXscale(0, 9);
        PennDraw.setYscale(0, 9);
        /* create booleans for entering different loops
         * startAgain - if player has chosen to play again
         * firstTime - at the start of game, first moves
         * hasEnded - player cannot reveal more tiles if he won
         */
        boolean startAgain = true; 
        boolean firstTime = true;
        boolean hasEnded = true;
        // create empty board
        MineField field = new MineField();
        while (firstTime) {
            // if player pressed a key
            if (PennDraw.hasNextKeyTyped()) {
                // reset mines revealed to 0
                minesRevealed = 0;
                // allow user to reenter next loop
                startAgain = true;
                // clear the screen
                PennDraw.clear();
                // create a new empty board
                field = new MineField();
            }
            // enter if player has pressed key
            while (startAgain) {
                // game has not ended
                hasEnded = false;
                // first move is over
                firstTime = false;
                // if player presses on tile
                if (PennDraw.mousePressed()) {
                    // get indices of mouse location
                    int mouse1X = (int) PennDraw.mouseX();
                    int mouse1Y = (int) PennDraw.mouseY();
                    // if mouse clicked is in the bounds
                    if (mouse1X >= 0 && mouse1X < 9 && mouse1Y >= 0 && mouse1Y < 9) {
                        // place mines around that location
                        field.putMines(mouse1X, mouse1Y);
                        // iterate through tiles
                        for (int i = 0; i < field.getMines().length; i++) {
                            for (int j = 0; j < field.getMines()[0].length; j++) {
                                // find the state of each tile
                                field.findState(i, j);
                            }
                        }
                        // enter if game has not ended
                        while (!hasEnded) {
                            // player has not chosen to start new game
                            startAgain = false;
                            // if mouse is pressed
                            if (PennDraw.mousePressed()) {
                                // get location of that mouse click 
                                int idxX = (int) PennDraw.mouseX();
                                int idxY = (int) PennDraw.mouseY();
                                // if mouse location is in bounds
                                if (idxX >= 0 && idxX < 9 && 
                                    idxY >= 0 && idxY < 9) {
                                    // reveal that tile
                                    field.revealAround(field.getMines()[idxX][idxY]);
                                    // create accumulator counts tiles unrevealed
                                    int countNotRevealed = 0;
                                    // iterate through all tiles
                                    for (int r = 0; r < field.getMines().length;
                                         r++) {
                                        for (int c = 0; c < field.getMines().length;
                                             c++) {
                                            // if tiles is not revealed
                                            if (!field.
                                                    getMines()[r][c].isRevealed()) {
                                                // add 1 to the accumulator
                                                countNotRevealed++;
                                            }
                                        }
                                    }
                                    // iterate through all tiles
                                    for (int h = 0; h < field.getMines().length;
                                         h++) {
                                        for (int w = 0; 
                                             w < field.getMines()[0].length; w++) {
                                                 // if tile is revealaed and has mine
                                                 if (field.getMines()[h][w].
                                                     isRevealed() && 
                                                     field.getMines()[h][w].
                                                     hasMine()) {
                                                     // add 1 to minesRevealed
                                                     minesRevealed++;
                                                 }
                                             }
                                    }
                                    // if mines are revealed
                                    if (minesRevealed == 10) {
                                        // Player loses
                                        PennDraw.setPenColor(PennDraw.WHITE);
                                        PennDraw.setFontSize(50);
                                        PennDraw.text(4.5, 5.5, "YOU LOSE");
                                        PennDraw.setFontSize(20);
                                        PennDraw.text(4.5, 3.5, 
                                                      "Press any key to try again");
                                        // Game has ended
                                        hasEnded = true;
                                        // reset keys in stack
                                        while (PennDraw.hasNextKeyTyped()) {
                                            PennDraw.nextKeyTyped();
                                        }
                                        // if number of unrevealed tiles is 10
                                    } else if (countNotRevealed == 10) {
                                        PennDraw.setPenColor(PennDraw.WHITE);
                                        PennDraw.setFontSize(50);
                                        PennDraw.text(4.5, 5.5, "Congratulations!");
                                        PennDraw.text(4.5, 3.5, "YOU WIN!");
                                        PennDraw.setFontSize(20);
                                        PennDraw.text(4.5, 1.5, 
                                                      "Press any key to play again");
                                        // game has ended
                                        hasEnded = true;
                                        // reset keys in stack
                                        while (PennDraw.hasNextKeyTyped()) {
                                            PennDraw.nextKeyTyped();
                                        }
                                    }
                                    // player can choose to play again
                                    firstTime = true;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
