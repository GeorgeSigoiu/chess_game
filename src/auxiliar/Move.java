package auxiliar;

/**
 * Reprezinta mutarea efectuata de o piesa de sah
 */
public class Move {
    /**
     * pozitia curenta a piesei
     */
    private final Coordinates currPosition;
    /**
     * pozitia urmatoare a piesei
     */
    private final Coordinates nextPosition;

    public Move(Coordinates pozCurenta, Coordinates pozUrmatoare) {
        this.currPosition = pozCurenta;
        this.nextPosition = pozUrmatoare;
    }

    public Coordinates getCurrPosition() {
        return currPosition;
    }

    public Coordinates getNextPosition() {
        return nextPosition;
    }

    @Override
    public String toString() {
        return nextPosition.toString();
    }
}
