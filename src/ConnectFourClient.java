import java.sql.SQLOutput;
import java.util.Scanner;

public class ConnectFourClient {
    private static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter player 1 name");
        String player1Name = input.nextLine();

        System.out.println("Please enter player 2 name");
        String player2Name = input.nextLine();

        ConnectFour connectFour = new ConnectFour(player1Name, player2Name);

        while(true) {
            System.out.println(connectFour.displayBoard());

            System.out.printf("Player %s move. Select a column to make a move", connectFour.currentPlayer());
            int col = input.nextInt();

            if(connectFour.isValidMove(col)) throw InvalidMoveException("Invalid move made. Please enter a valid move.");

            connectFour.makeMove(col);

            if(connectFour.didPlayerWin()) {
                System.out.printf("Player %s WON!!!", connectFour.currentPlayer());
                break;
            }

            connectFour.updateCurrentPlayer();


        }



    }
}
