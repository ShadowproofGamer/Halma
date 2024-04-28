public class MoveAndScore {
    private final Move move;
    private final int score;
    private int nodesVisited;

    public MoveAndScore(Move move, int score) {
        this.move = move;
        this.score = score;
        this.nodesVisited = 1;
    }

    public MoveAndScore(Move move, int score, int nodesVisited) {
        this.move = move;
        this.score = score;
        this.nodesVisited = nodesVisited;
    }

    public Move getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }

    public int getNodesVisited() {
        return nodesVisited;
    }

    @Override
    public String toString() {
        return getMove() + "    eval:" + getScore() + "    nodes:"+ getNodesVisited();
    }
}
