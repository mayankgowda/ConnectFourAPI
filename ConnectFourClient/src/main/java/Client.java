import org.game.connect4.ConnectFourGame;
import org.game.connect4.ConnectFourInitializer;
import org.game.connect4.util.GameStatus;

import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        String player1Name = "M";
        String player2Name = "G";

        ConnectFourInitializer initializer = new ConnectFourInitializer();
        Scanner input = new Scanner(System.in);
        ConnectFourGame game = initializer.initializeDefaultPlayerVsPlayer(player1Name, player2Name);
        GameStatus gameStatus = game.checkGameStatus(0);
        while (true) {
            System.out.println(game.getCurrentPlayer().getName() + " please input the column index (from 1 to " + (game.getGameGrid().getWidth()) + ") you want to put a checker: ");

            int colIndex = input.nextInt() - 1;
            game.playMove(colIndex);
            game.getGameGrid().displayGrid();
            gameStatus = game.checkGameStatus(colIndex);
            if (gameStatus != GameStatus.CONTINUE)
                break;
            game.switchPlayer();
        }
        if (gameStatus == GameStatus.PLAYER_1_WINS) {
            System.out.println("Congrats! " + game.getPlayer1().getName() + " has won the game!");
        } else if (gameStatus == GameStatus.PLAYER_2_WINS) {
            System.out.println("Congrats! " + game.getPlayer2().getName() + " has won the game!");
        } else {
            System.out.println("Game Tied!");
        }
    }
}

