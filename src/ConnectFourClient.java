
import java.util.Scanner;

public class ConnectFourClient {

    public static void main(String[] args) throws connectfour.InvalidMoveException {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter player 1 name");
        String player1Name = input.nextLine();

        System.out.println("Please enter player 2 name");
        String player2Name = input.nextLine();

        connectfour.ConnectFour connectFour = new connectfour.ConnectFour(player1Name, player2Name);

        connectFour.startGame(input);

    }
}

