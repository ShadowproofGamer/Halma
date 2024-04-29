import java.util.Random;

public class HeuristicRandom implements Heuristic{
    @Override
    public int calculate(Board2 board, Player player) {
        return Random.from(new Random()).nextInt(-10000, 10001);
    }
}
