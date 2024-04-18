import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Board board = new Board();
        //board.printBoard();
        board.initBoard();
        board.printBoard();
        //System.out.println(board.possibleMoves(Player.PLAYER1));
        System.out.println(board.board.get(Tile.of(13,11)));
        Set<Move> simple = board.simpleMoves(Player.PLAYER1);
        System.out.println(simple);
        System.out.println(simple.size());
        System.out.println("--");
        Set<Move> jumps = board.jumpMoves(Player.PLAYER1);
        System.out.println(jumps);
        System.out.println(jumps.size());

    }
}
