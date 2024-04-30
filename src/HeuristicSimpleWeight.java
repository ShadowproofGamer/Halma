import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HeuristicSimpleWeight implements Heuristic{

    @Override
    public int calculate(Board2 board, Player player) {
        float resultX = 0;
        float resultY = 0;
        List<Tile> playerTiles = board.state.keySet().stream().filter(c -> board.state.get(c).equals(player)).toList();
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)){
            for (Tile tile:playerTiles){
                resultX+= tile.getX();
                resultY+= tile.getY();
            }
            return (int) (2*MAXLEN - (resultX/19) - (resultY/19));
        }
        else {
            for (Tile tile:playerTiles){
                resultX+= tile.getX();
                resultY+= tile.getY();
            }
            return (int) (10*(resultX/19) + 10*(resultY/19));
        }
    }
}
