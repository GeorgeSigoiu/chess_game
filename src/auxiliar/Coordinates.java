package auxiliar;

/**
 * Reprezinta coordonatele de pe tabla de joc a unei piese
 */
public class Coordinates {
    /**
     * coordonatele verticale si orizontale ale unei piese de sah
     */
    private int x, y;

    public Coordinates() {
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
