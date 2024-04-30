import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HeuristicSimple implements Heuristic{
    public int calculate(Board2 board, Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> playerTiles = board.state.keySet().stream().filter(c -> board.state.get(c).equals(player)).toList();
        List<Tile> player1Base = board.player1Base;
        List<Tile> player2Base = board.player2Base;
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)){
            playerTiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-15);
                } else if (player2Base.contains(tile)) {
                    result.getAndAdd(10);
                }
                result.getAndAdd((MAXLEN - tile.getX() + MAXLEN - tile.getY())*2);
            });
            return result.get();
        }
        else {
            playerTiles.forEach(tile -> {
                if (player2Base.contains(tile)) {
                    result.getAndAdd(-15);
                } else if (player1Base.contains(tile)) {
                    result.getAndAdd(10);
                }
                result.getAndAdd(tile.getX() + tile.getY());
            });
            return result.get();
        }
    }
}
