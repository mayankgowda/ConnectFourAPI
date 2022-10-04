package connectfour;

import java.util.*;

/**
 * ConnectFour is a class that is used to play the connect four with 2 players.
 *
 * Sample Code:
 * <pre>
 *
 * import java.util.Scanner;
 *
 * public class ConnectFourClient {
 *
 *     public static void main(String[] args) throws connectfour.InvalidMoveException {
 *         Scanner input = new Scanner(System.in);
 *
 *         System.out.println("Please enter player 1 name");
 *         String player1Name = input.nextLine();
 *
 *         System.out.println("Please enter player 2 name");
 *         String player2Name = input.nextLine();
 *
 *         connectfour.ConnectFour connectFour = new connectfour.ConnectFour(player1Name, player2Name);
 *
 *         connectFour.startGame(input);
 *
 *     }
 * }
 * </pre>
 */
public class ConnectFour {

    /**
     * Set to store player 1 moves
     */
    private Set<Map.Entry<Integer, Integer>> player1Moves;

    /**
     * Set to store player 2 moves
     */
    private Set<Map.Entry<Integer, Integer>> player2Moves;

    /**
     * List to represent and store the board
     */
    private ArrayList<ArrayList<Integer>> board;

    /**
     * Array to store valid empty index in each column
     */
    private int[] currentColumnHeight;

    /**
     * Player 1 Name
     */
    private String player1;

    /**
     * Player 2 Name
     */
    private String player2;

    /**
     * Static integer to hold current player
     */
    private static int currentPlayer = 1;

    /**
     * Constructor method to create an instance of connectfour.ConnectFour.
     * @param player1Name name of player 1
     * @param player2Name name of player 2
     */
    public ConnectFour(String player1Name, String player2Name) {

        player1 = player1Name;
        player2 = player2Name;
        player1Moves = new HashSet<Map.Entry<Integer, Integer>>();
        player2Moves = new HashSet<Map.Entry<Integer, Integer>>();
        currentColumnHeight = new int[7];

    }

    /**
     * Returns the moves made by player1
     * @return the moves made by player1
     */
    public Set<Map.Entry<Integer, Integer>> getPlayer1Moves() {
        return player1Moves;
    }

    /**
     * Returns the moves made by player2
     * @return the moves made by player2
     */
    public Set<Map.Entry<Integer, Integer>> getPlayer2Moves() {
        return player2Moves;
    }

    /**
     * Returns the board as an ArrayList of ArrayList
     * @return the board as an ArrayList of ArrayList
     */
    public ArrayList<ArrayList<Integer>> getBoard() {
        return board;
    }

    /**
     * Returns the player 1 name
     * @return the player 1 name
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Returns the player 2 name
     * @return the player 2 name
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Starts game. Accepts a scanner to get player moves from. The client typically creates a scanner that reads input from the console.
     * @param input Scanner type object to get player moves from
     */
    public void startTextGameBetweenTwoPLayers(Scanner input) {

        while(true) {
            try {
                System.out.println(getBoardString());

                System.out.printf("Player %s move. Select a column to make a move: ", getCurrentPlayer());

                int col = -1;
                try {
                    col = input.nextInt();
                } catch (Exception e) {
                    throw new InvalidMoveException("Invalid move made. Kindly check the move or if the scanner is still active.");
                }

                if (!isValidMoveForCurrentPlayer(col)) {
                    throw new InvalidMoveException("Invalid move made. Please enter a valid move.");
                }

                makeMoveForCurrentPlayer(col);

                if (didCurrentPlayerWin()) {
                    System.out.println(getBoardString());
                    System.out.printf("Player %s WON!!!", getCurrentPlayer());
                    break;
                }

                updateCurrentPlayer();
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }

    /**
     * This methods determines if a potential move is valid by the connectfour.ConnectFour rules.
     * @param column, an integer which corresponds to the column the next chip would be placed in
     * @return a boolean, true if the move is to a valid place on the board.
     */
    public boolean isValidMoveForCurrentPlayer(Integer column) {
        List<Integer> validMoves = validNextMovesForCurrentPlayer();
        return validMoves.contains(column);
    }
    /**
     * This method places a chip in the specified column on the board
     * if and only if the specified column is a valid column to place a chip
     * @param column is the column which the next chip is attempting to be placed in
     * @return a boolean, true if the move was successfully made, false otherwise
     */
    public void makeMoveForCurrentPlayer(Integer column) throws InvalidMoveException{
        if (!isValidMoveForCurrentPlayer(column)) {
            throw new InvalidMoveException("Invalid move made. Please enter a valid column value after referring to the board.");
        } else {
            Integer columnLength = currentColumnHeight[column];
            Map.Entry<Integer,Integer> move = new AbstractMap.SimpleEntry<>(5 - columnLength, column);
            getCurrentPlayerMoves().add(move);
            currentColumnHeight[column] += 1;
        }
    }

    /**
     * Updates/Toggles the current player.
     */
    public void updateCurrentPlayer () {
        if(currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }
    }

    /**
     * Returns the moves made by current player
     * @return the moves made by current player
     */
    public Set<Map.Entry<Integer,Integer>> getCurrentPlayerMoves () {
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
    public List<Integer> validNextMovesForCurrentPlayer() {
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
    public String getCurrentPlayer() {
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
    public String getBoardString() {
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
    public boolean didCurrentPlayerWin() {
        Set<Map.Entry<Integer, Integer>> playerMoves = getCurrentPlayer().equals(player1) ? player1Moves : player2Moves;

        for(Map.Entry<Integer, Integer> e: playerMoves) {
            if(checkIfPlayerWinsWithMoves(playerMoves, e.getKey(), e.getValue())) return true;
        }

        return false;
    }

    /**
     * Returns true, if player has won the game with current moves, else false.
     * @param playerMoves Set of player moves to check
     * @param x start row position to check if 4 sequential moves made
     * @param y start column position to check if 4 sequential moves made
     * @return true, if player has won the game with current moves, else false.
     */
    private boolean checkIfPlayerWinsWithMoves(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y) {

        return checkForwardDiagonalUpward(playerMoves, x, y, 0) ||
        checkForwardDiagonalDownward(playerMoves, x, y, 0) ||
        checkBackwardDiagonalUpward(playerMoves, x, y, 0) ||
        checkBackwardDiagonalDownward(playerMoves, x, y, 0) ||
        checkRowForward(playerMoves, x, y, 0) ||
        checkRowBackward(playerMoves, x, y, 0) ||
        checkColumnDownward(playerMoves, x, y, 0) ||
        checkColumnUpward(playerMoves, x, y, 0);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) diagonally upward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally upward
     */
    private boolean checkForwardDiagonalUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {

        if(!playerMoves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkForwardDiagonalUpward(playerMoves, x+1, y+1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) diagonally forward and upward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally forward and upward
     */
    private boolean checkForwardDiagonalDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkForwardDiagonalDownward(playerMoves, x-1, y+1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) diagonally upward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally upward
     */
    private boolean checkBackwardDiagonalUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {

        if(!playerMoves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkBackwardDiagonalUpward(playerMoves, x+1, y+1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) diagonally backward and downward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally backward and downward
     */
    private boolean checkBackwardDiagonalDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkBackwardDiagonalDownward(playerMoves, x-1, y-1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) horizontally forward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) horizontally forward
     */
    private boolean checkRowForward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkRowForward(playerMoves, x, y+1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) horizontally backward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally upward
     */
    private boolean checkRowBackward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkRowBackward(playerMoves, x, y-1, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) vertically downward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally upward
     */
    private boolean checkColumnDownward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkColumnDownward(playerMoves, x+1, y, count+1);
    }

    /**
     * return true if 4 sequential player moves found starting from (x,y) vertically upward
     * @param playerMoves player moves to check
     * @param x start row
     * @param y start column
     * @param count maintain count to check recursively
     * @return true if 4 sequential player moves found starting from (x,y) diagonally upward
     */
    private boolean checkColumnUpward(Set<Map.Entry<Integer, Integer>> playerMoves, int x, int y, int count) {
        if(count == 4) return true;
        if(!playerMoves.contains(Map.entry(x, y))) return false;

        return checkColumnUpward(playerMoves, x-1, y, count+1);
    }






}