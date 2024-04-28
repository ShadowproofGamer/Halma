import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Heuristic1 implements Heuristic{
    public int heuristic(Board board, Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> player1Tiles = board.board.keySet().stream().filter(c -> board.board.get(c).equals(Player.PLAYER1)).toList();
        List<Tile> player2Tiles = board.board.keySet().stream().filter(c -> board.board.get(c).equals(Player.PLAYER2)).toList();
//        List<Tile> player1Base = board.player1Base;
//        List<Tile> player2Base = board.player2Base;
        int MAXLEN = 15;
        player1Tiles.forEach(tile -> {
            if (board.player1Base.contains(tile)) {
                result.getAndAdd(-5);
            } else if (board.player2Base.contains(tile)) {
                result.getAndAdd(3);
            }
            result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY());
        });
        player2Tiles.forEach(tile -> {
            if (board.player2Base.contains(tile)) {
                result.getAndAdd(5);
            }
            if (board.player1Base.contains(tile)) {
                result.getAndAdd(-3);
            }
            result.getAndAdd(-tile.getX() - tile.getY());
        });
        return result.get();
    }
}
