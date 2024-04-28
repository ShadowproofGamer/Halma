import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface Heuristic {
    int heuristic(Board board, Player player);
}
