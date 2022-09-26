import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConnectFour {


    private Set<Map.Entry<Integer, Integer>> player1Moves;

    private Set<Map.Entry<Integer, Integer>> player2Moves;

    private String player1;

    private String player2;

    private static int currentPlayer = 1;

    public ConnectFour(String player1Name, String player2Name) {

        player1 = player1Name;
        player2 = player2Name;
        player1Moves = new HashSet<Map.Entry<Integer, Integer>>();
        player2Moves = new HashSet<Map.Entry<Integer, Integer>>();

    }

    // R
    public boolean isValidMove(String player, Map.Entry<Integer,Integer> move) {

    }
    // R
    public boolean makeMove(String player,Map.Entry<Integer,Integer> move) {

    }

    public List<Map.Entry<Integer,Integer>> validNextMoves() {

    }

    public String currentPlayer() {

    }

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

    public boolean didPlayerWin() {



        return true;
    }

    private boolean checkForwardDiagonalUpward(int x, int y, int count) {

        if(!player1Moves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkForwardDiagonalUpward(x+1, y+1, count+1);
    }

    private boolean checkForwardDiagonalDownward(int x, int y, int count) {
        if(count == 4) return true;
        if(!player1Moves.contains(Map.entry(x, y))) return false;

        return checkForwardDiagonalDownward(x-1, y+1, count+1);
    }

    private boolean checkBackwardDiagonalUpward(int x, int y, int count) {

        if(!player1Moves.contains(Map.entry(x, y))) return false;
        if(count == 4) return true;

        return checkBackwardDiagonalUpward(x+1, y+1, count+1);
    }

    private boolean checkBackwardDiagonalDownward(int x, int y, int count) {
        if(count == 4) return true;
        if(!player1Moves.contains(Map.entry(x, y))) return false;

        return checkBackwardDiagonalDownward(x-1, y-1, count+1);
    }






}