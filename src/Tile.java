import java.util.Objects;

public class Tile {
    private final int x;
    private final int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Tile of(int x, int y) {
        return new Tile(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return x == tile.x && y == tile.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+ x +
                "," + y +
                ')';
    }
}
