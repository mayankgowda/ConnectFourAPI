import java.security.KeyPair;
import java.util.*;

public class ConnectFour {

    private Set<Map.Entry<Integer, Integer>> player1Moves;

    private Set<Map.Entry<Integer, Integer>> player2Moves;
    
    private ArrayList<ArrayList<Integer>> board;

    private String player1;

    private String player2;

    private static int currentPlayer = 1;

    public ConnectFour(String player1Name, String player2Name) {

        player1 = player1Name;
        player2 = player2Name;
        player1Moves = new HashSet<Map.Entry<Integer, Integer>>();
        player2Moves = new HashSet<Map.Entry<Integer, Integer>>();

        board = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < 7; i++) {
            board.add(new ArrayList<Integer>());
        }

    }

    /**
     * This methods determines if a potential move is valid by the ConnectFour rules.
     * @param player is the name assigned to the player that would make this potential move.
     * @param move is the pairing (column, row) which the next chip would be placed in
     * @return a boolean, true if the move is to a valid place on the board and if it is the specified players turn,
     * false otherwise
     */
    public boolean isValidMove(String player, Map.Entry<Integer,Integer> move) {
        List<Map.Entry<Integer,Integer>> validMoves = validNextMoves();
        boolean isValid = validMoves.contains(move);
        boolean isCorrectPlayer = currentPlayer().equals(player);
        return isValid && isCorrectPlayer;
    }
    /**
     * This method attempts to make a move to the specified spot on the board for the specified player,
     * if and only if it is the specified player's turn and the specified move is a valid move
     * @param player is the name assigned to the player making the move
     * @param move is the pairing (column, row) which the next chip is to be placed in
     * @return a boolean, true if the move was successfully made, false otherwise
     */
    public boolean makeMove(String player, Map.Entry<Integer,Integer> move) {
        if (!isValidMove(player, move)) {
            return false;
        } else {
            getCurrentPlayerMoves().add(move);
            board.get(move.getKey()).add(currentPlayer);
            updateCurrentPlayer();
            return true;
        }
    }
    private void updateCurrentPlayer () {
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
     * This methods returns all the valid places on the board the player can move to.
     * @return a List of pairings,(column, row) which correspond to valid places on the board
     * a player can make a move to according to the Connect Four rules.
     */
    public List<Map.Entry<Integer,Integer>> validNextMoves() {
        List<Map.Entry<Integer,Integer>> validMoves = new ArrayList<>();
        for (int i = 0; i < 7 ; i++) {
            int columnLength = board.get(i).size();
            if (columnLength < 6) {
                Map.Entry<Integer,Integer> move = new AbstractMap.SimpleEntry<>(i,columnLength);
                validMoves.add(move);
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