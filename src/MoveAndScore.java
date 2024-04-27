public class MoveAndScore {
    private final Move move;
    private final int score;

    public MoveAndScore(Move move, int score) {
        this.move = move;
        this.score = score;
    }

    public Move getMove() {
        return move;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return getMove() + " :: " + getScore();
    }
}
