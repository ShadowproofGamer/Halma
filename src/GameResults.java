public class GameResults {
    private final Player winner;
    private final long timeElapsed;
    private final int nodesVisited;

    public GameResults(Player winner, long timeElapsed, int nodesVisited) {
        this.winner = winner;
        this.timeElapsed = timeElapsed;
        this.nodesVisited = nodesVisited;
    }

    public Player getWinner() {
        return winner;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    @Override
    public String toString() {
        return "Winner:"+winner + "    time elapsed:" + timeElapsed/1000000 + "ms    nodes visited:"+ nodesVisited;
    }
}
