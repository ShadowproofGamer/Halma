import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Heuristic2 implements Heuristic{
    public int calculate(Board2 board, Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> player1Tiles = board.state.keySet().stream().filter(c -> board.state.get(c).equals(Player.PLAYER1)).toList();
        List<Tile> player2Tiles = board.state.keySet().stream().filter(c -> board.state.get(c).equals(Player.PLAYER2)).toList();
        List<Tile> player1Base = board.player1Base;
        List<Tile> player2Base = board.player2Base;
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)){
            player1Tiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-10);
                } else if (player2Base.contains(tile)) {
                    result.getAndAdd(5);
                }
                result.getAndAdd((MAXLEN - tile.getX() + MAXLEN - tile.getY()));
            });
            player2Tiles.forEach(tile -> {
                if (player2Base.contains(tile)) {
                    result.getAndAdd(2);
                }
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-3);
                }
                result.getAndAdd(-tile.getX() - tile.getY());
            });
            return result.get();
        }
        else {
            player1Tiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(2);
                } else if (player2Base.contains(tile)) {
                    result.getAndAdd(-3);
                }
                result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY());
            });
            player2Tiles.forEach(tile -> {
                if (player2Base.contains(tile)) {
                    result.getAndAdd(-10);
                }
                if (player1Base.contains(tile)) {
                    result.getAndAdd(5);
                }
                result.getAndAdd((-tile.getX() - tile.getY()));
            });
            return result.get();
        }
    }
}
