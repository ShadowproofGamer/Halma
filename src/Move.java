import java.util.Objects;

public class Move {
    private Tile moveFrom;
    private Tile moveTo;

    public Move(Tile moveFrom, Tile moveTo) {
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
    }

    public Tile getTileFrom() {
        return moveFrom;
    }

    public Tile getTileTo() {
        return moveTo;
    }

    public int[] getMoveLength(){
        return new int[]{moveTo.getX() - moveFrom.getX(), moveTo.getY() - moveFrom.getY()};
    }

    public static Move of(Tile moveFrom, Tile moveTo){
        return new Move(moveFrom, moveTo);
    }

    @Override
    public String toString() {
        return moveFrom + "-->" + moveTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(moveFrom, move.moveFrom) && Objects.equals(moveTo, move.moveTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moveFrom, moveTo);
    }
}
