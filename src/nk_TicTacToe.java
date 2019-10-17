public class nk_TicTacToe {

    private char[][] gameBoard;
    private int inline;         // Number of symbols in-line needed to win the game

    /**
     * Initialize gameBoard
     * @param boardsize of tic-tac-toe
     * @param inline number of symbols in-line needed to win the game
     * @param maxlevels max number of levels for gametree to explore
     */
    public nk_TicTacToe (int boardsize, int inline, int maxlevels) throws IllegalArgumentException {
        if (inline > boardsize)
            throw new IllegalArgumentException("Inline can't be greater than boardsize");
        this.inline = inline;
        gameBoard = new char[boardsize][boardsize];
        for (int i = 0; i < boardsize; i++)
            for (int j = 0; j < boardsize; j++){
                gameBoard[i][j] = ' ';
            }
    }


    /**
     * Creates a dictionary of a prime number size between 6000 - 8000
     * @return dictionary
     */
    public Dictionary createDictionary(){
        Dictionary dictionary = new Dictionary(6343);
        return dictionary;
    }

    /**
     * Checks if gameboard string exists in dictionary
     * @param configurations dictionary of gametree
     * @return score if configuration exists or -1 if it doesn't
     */
    public int repeatedConfig(Dictionary configurations){
        // Obtain string of current gameboard
        String gameboard = gameBoardString();
        // Obtain entry in dictionary
        return configurations.get(gameboard);
    }

    /**
     * Inserts gameboard into dictionary
     * @param configurations dictionary of gametree
     * @param score score based off current gameboard
     */
    public void insertConfig(Dictionary configurations, int score){
        // Obtain string of current gameboard
        String gameboard = gameBoardString();
        // Insert configuration within dictionary, may throw error if it already exists, so catch
        try {
            configurations.insert(new Record(gameboard, score));
        }
        catch (DictionaryException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Stores symbol within gameboard
     * @param row of gameboard
     * @param col of gameboard
     * @param symbol being stored
     */
    public void storePlay(int row, int col, char symbol){
        gameBoard[row][col] = symbol;
    }

    /**
     * Check if spot in gameboard is empty
     * @param row of gameboard
     * @param col of gameboard
     * @return true if empty, false otherwise
     */
    public boolean squareIsEmpty(int row, int col){
        return gameBoard[row][col] == ' ';
    }

    /**
     * Returns true if there are inline adjacent occurences of symbol in same row, col, or diagonal of gameboard
     * @param symbol representing player
     * @return true if won, false otherwise
     */
    public boolean wins(char symbol){
        int inlinecount = 0;

        // Check rows to see if someone won
        for (int i = 0; i < gameBoard.length; i++) {
            // Reset to 0, no inline adjacent occurences within row found
            inlinecount = 0;
            for (int j = 0; j < gameBoard.length; j++) {
                // Mathing symbol found, increase count and compare with inline
                if (gameBoard[i][j] == symbol)
                    inlinecount++;
                else
                    inlinecount = 0;        // Symbol not adjacent
                if (inlinecount == inline)
                    return true;
            }
        }

        // Check cols to see if someone won
        for (int i = 0; i < gameBoard.length; i++) {
            // Reset to 0, no inline adjacent occurences within col found
            inlinecount = 0;
            for (int j = 0; j < gameBoard.length; j++) {
                // Mathing symbol found, increase count and compare with inline
                if (gameBoard[j][i] == symbol)
                    inlinecount++;
                else
                    inlinecount = 0;        // Symbol not adjacent
                if (inlinecount == inline)
                    return true;
            }
        }

        // Check diagonal from topleft to bottomright (upper diagonal elements) starting from row 0
        for (int i = 0; i < gameBoard.length; i++){
            inlinecount = 0;
            // if a diagonal hasn't been found, and the height is less than inline, then a diagonal doesn't exist
            if (gameBoard.length - i >= inline)
                for (int j = 0; j < gameBoard.length; j++){
                    // since i + j is used to check the column, make sure it stays in bound
                    if ((i + j) < gameBoard.length) {
                        // Check diagonal by checking row 0 first
                        // Row 0 is always checked first during each iteration of outer loop
                        // i + j corresponds to the 1st column being checked first during the first iteration of the outer
                        // loop, and then the 2nd column and so on since the upper diagonal is being checked
                        if (gameBoard[j][i + j] == symbol)
                            inlinecount++;
                        else
                            inlinecount = 0;
                        if (inlinecount == inline)
                            return true;
                    }
                }
        }

        // Check diagonal from topleft to bottomright (lower diagonal elements)
        // Start at second row, since loop above just checked upper diagonal
        for (int i = 1; i < gameBoard.length; i++){
            inlinecount = 0;
            // if a diagonal hasn't been found, and the height is less than inline, then a diagonal doesn't exist
            if (gameBoard.length - i >= inline)
                for (int j = 0; j < gameBoard.length; j++){
                    // since i + j is used to check the column, make sure it stays in bound
                    if ((i + j) < gameBoard.length) {
                        // Check diagonal by checking each 1 first during irst iteration of outer loop, then 2 and so on
                        // Since only the lower diagonal is being checked, the second index is j since it has to be checked
                        // From the 1st column each time
                        if (gameBoard[i + j][j] == symbol)
                            inlinecount++;
                        else
                            inlinecount = 0;
                        if (inlinecount == inline)
                            return true;
                    }
                }
        }

        // Check diagonal from top right to bottom left (upper diagonal elements) starting at row 0
        for (int i = 0; i < gameBoard.length; i++){
            inlinecount = 0;
            // if a diagonal hasn't been found, and the height is less than inline, then a diagonal doesn't exist
            if (gameBoard.length - i >= inline)
                for (int j = gameBoard.length - 1; j >= 0; j--){
                    // Make sure array is within bounds
                    if ((j - i) >= 0) {
                        // Check diagonal by checking row 0 first
                        // gameboard.length - j - 1 corresponds to first row during each iteration of outer loop
                        // j - i corresponds to  5th column during first iteration of outer loop, then 4th, and so on
                        // Sinnce the upper diagonal is being checked
                        if (gameBoard[gameBoard.length - j - 1][j - i] == symbol)
                            inlinecount++;
                        else
                            inlinecount = 0;
                        if (inlinecount == inline)
                            return true;
                    }
                }
        }

        // Check diagonal from top right to bottom left (lower diagonal elements) starting at row 1
        for (int i = 0; i < gameBoard.length; i++){
            inlinecount = 0;
            // if a diagonal hasn't been found, and the height is less than inline, then a diagonal doesn't exist
            if (gameBoard.length - i >= inline)
                for (int j = gameBoard.length - 1; j >= 0; j--){
                    // Make sure array is within bounds
                    if ((gameBoard.length - j + i) < gameBoard.length ) {
                        // gameboard.length - j + i corresponds to second row, then third and so on during each
                        // iteration of outer loop
                        // since the lower diagonal is being checked, the column is just j since it's just being
                        // checked from right to left
                        if (gameBoard[gameBoard.length - j + i][j] == symbol)
                            inlinecount++;
                        else
                            inlinecount = 0;
                        if (inlinecount == inline)
                            return true;
                    }
                }
        }

        // No win found
        return false;
    }

    /**
     * Check to see if there are any playable spots on the board
     * @return true if no positions left to play, false otherwise
     */
    public boolean isDraw(){
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard.length; j++){
                // If any spot in gameboard is blank, then return false since there's a playable spot
                if (gameBoard[i][j] == ' ')
                    return false;
            }
        return true;
    }

    /**
     * Determine current state of the board
     * @return 0 if human wins, 1 if game is undecided, 2 if draw, 3 if computer wins
     */
    public int evalBoard(){
        if (wins('O'))
            return 3;
        if (wins('X'))
            return 0;
        if (isDraw())
            return 2;
        return 1;
    }

    // Private utilities

    /**
     * Constructs a string of the gameboard to be used as a key for the dictionary entry
     * @return string of the gameboard, row by row, from left to right
     */
    private String gameBoardString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < gameBoard.length; i++)
            for (int j = 0; j < gameBoard.length; j++){
                sb.append(gameBoard[i][j]);
            }
        return sb.toString();
    }

}
