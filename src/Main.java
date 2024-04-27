import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //basic test
//        Board board = new Board();
//        board.printBoard();
//        ArrayList<Move> allMoves = board.possibleMoves(Player.PLAYER1);
//        System.out.println(allMoves);
//        System.out.println(allMoves.size());


        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.alfabeta(board, Player.PLAYER1, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1);
        System.out.println(Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (result.getScore() < Integer.MAX_VALUE) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.alfabeta(board, currentPlayer, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
            System.out.println(currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }
    }
}
