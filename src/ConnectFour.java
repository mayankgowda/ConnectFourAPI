import java.security.KeyPair;
import java.util.*;

public class ConnectFour {

    /**
     * Set to store player 1 moves
     */
    private Set<Map.Entry<Integer, Integer>> player1Moves;

    /**
     * Set to store player 2 moves
     */
    private Set<Map.Entry<Integer, Integer>> player2Moves;
<<<<<<< Updated upstream

    /**
     * List to represent and store the board
     */
    private ArrayList<ArrayList<Integer>> board;
=======
    
    private int[] currentColumnHeight;
>>>>>>> Stashed changes

    /**
     * Player 1 Name
     */
    private String player1;

    /**
     * Player 2 Name
     */
    private String player2;

    private List<Integer> firstAvailableSlot = new ArrayList<>(7); 0 0 2 2 0 1 1

            0 0 0 0 0 0 0
             0 0 0 0 0 0 0
             0 0 0 0 0 0 0
             0 0 1 1 0 0 0
             0 0 1 2 0 2 2

    private static int currentPlayer = 1;

    public ConnectFour(String player1Name, String player2Name) {

        player1 = player1Name;
        player2 = player2Name;
        player1Moves = new HashSet<Map.Entry<Integer, Integer>>();
        player2Moves = new HashSet<Map.Entry<Integer, Integer>>();
        currentColumnHeight = new int[7];

    }

    /**
     * This methods determines if a potential move is valid by the ConnectFour rules.
     * @param column, an integer which corresponds to the column the next chip would be placed in
     * @return a boolean, true if the move is to a valid place on the board.
     */
    public boolean isValidMove(Integer column) {
        List<Integer> validMoves = validNextMoves();
        return validMoves.contains(column);
    }
    /**
     * This method places a chip in the specified column on the board
     * if and only if the specified column is a valid column to place a chip
     * @param column is the column which the next chip is attempting to be placed in
     * @return a boolean, true if the move was successfully made, false otherwise
     */
<<<<<<< Updated upstream
    public boolean makeMove(Map.Entry<Integer,Integer> move) {
        if (!isValidMove(currentPlayer(), move)) {
=======
    public boolean makeMove(Integer column) {
        if (!isValidMove(column)) {
>>>>>>> Stashed changes
            return false;
        } else {
            Integer columnLength = currentColumnHeight[column];
            Map.Entry<Integer,Integer> move = new AbstractMap.SimpleEntry<>(column, columnLength);
            getCurrentPlayerMoves().add(move);
            currentColumnHeight[column] += 1;
            updateCurrentPlayer();
            return true;
        }
    }
    public void updateCurrentPlayer () {
        if(currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }
    private Set<Map.Entry<Integer,Integer>> getCurrentPlayerMoves () {
        if(currentPlayer == 1) {
            return player1Moves;
        } else {
            return player2Moves;
        }
    }
    /**
     * This methods returns all the valid columns, which the player can place a chip.
     * @return a list of integers which correspond to valid columns where the current player can place a chip
     */
    public List<Integer> validNextMoves() {
        List<Integer> validMoves = new ArrayList<>();
        for (int i = 0; i < 7 ; i++) {
            int columnLength = currentColumnHeight[i];
            if (columnLength < 6) {
                validMoves.add(i);
            }
        }
        return validMoves;
    }

    /**
     * returns the current player who is allowed to currently place a new chip on the board
     * @return the string associated with the player whose turn it it.
     */
    public String currentPlayer() {
        if(currentPlayer == 1) {
            return player1;
        } else {
            return player2;
        }
    }

    /**
     * returns the string of the board with all moves of all players
     * @return the string of the board with all moves of all players
     */
    public String displayBoard() {
        StringBuilder board = new StringBuilder();

        for(int i = 0; i < 6; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for(int j = 0; j < 7; j++) {
                if(player1Moves.contains(Map.entry(i, j))) sb.append("X ");
                else if(player2Moves.contains(Map.entry(i, j))) sb.append("Y ");
                else sb.append("O ");
            }
            sb.append("]");
            board.append(sb);
            board.append('\n');
        }
        return board.toString();

    }

    /**
     * Returns 'true' if the current player wins the game, 'false' otherwise. Checks all moves of the current player to see if there are 4
     * consecutive winning moves made by the player.
     * @return 'true' if the current player wins the game, 'false' otherwise.
     */
    public boolean didPlayerWin() {
        Set<Map.Entry<Integer, Integer>> playerMoves = currentPlayer().equals(player1) ? player1Moves : player2Moves;

        for(Map.Entry<Integer, Integer> e: playerMoves) {
            if(checkIfPlayerWins(playerMoves, e.getKey(), e.getValue())) return true;
        }

        return false;
    }

    private boolean checkIfPlayerWins(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y) {

        return checkForwardDiagonalUpward(playerMoves, x, y, 1) ||
        checkForwardDiagonalDownward(playerMoves, x, y, 1) ||
        checkBackwardDiagonalUpward(playerMoves, x, y, 1) ||
        checkBackwardDiagonalDownward(playerMoves, x, y, 1) ||
        checkRowForward(playerMoves, x, y, 1) ||
        checkRowBackward(playerMoves, x, y, 1) ||
        checkColumnDownward(playerMoves, x, y, 1) ||
        checkColumnUpward(playerMoves, x, y, 1);
    }

    private boolean checkForwardDiagonalUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {

        if(!playerMoves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkForwardDiagonalUpward(playerMoves, x+1, y+1, count+1);
    }

    private boolean checkForwardDiagonalDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkForwardDiagonalDownward(playerMoves, x-1, y+1, count+1);
    }

    private boolean checkBackwardDiagonalUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {

        if(!playerMoves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkBackwardDiagonalUpward(playerMoves, x+1, y+1, count+1);
    }

    private boolean checkBackwardDiagonalDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkBackwardDiagonalDownward(playerMoves, x-1, y-1, count+1);
    }

    private boolean checkRowForward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkRowForward(playerMoves, x, y+1, count+1);
    }

    private boolean checkRowBackward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkRowBackward(playerMoves, x, y-1, count+1);
    }

    private boolean checkColumnDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkColumnDownward(playerMoves, x+1, y, count+1);
    }

    private boolean checkColumnUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkColumnUpward(playerMoves, x-1, y, count+1);
    }






}