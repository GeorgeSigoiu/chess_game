package auxiliar;

import pieces.*;

import java.util.ArrayList;

/**
 * Clasa care retine daca o pozitie de pe tabla de joc este atacata de vreo piesa de sah.
 */
public class AttackingPiece {
    /**
     * tipul piesei care ataca pozitia
     */
    private Piece.TipPiesa type;
    /**
     * coordonatele piesei care ataca pozitita de pe tabla
     */
    private Coordinates position;
    /**
     * daca pozitia este atacata
     */
    private boolean isAttacking;
    /**
     * daca ataca aceeasi pozitie mai multe piese
     */
    private int attackingPiecesNumber;
    /**
     * coordonatele tuturor pieselor care ataca aceeasi pozitie
     */
    private ArrayList<Coordinates> coordinates;

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setType(Piece.TipPiesa type) {
        this.type = type;
    }

    public void setPosition(Coordinates position) {
        this.position = position;
    }

    public void setAttacking(boolean attacking) {
        this.isAttacking = attacking;
    }

    public void incCateAtaca() {
        this.attackingPiecesNumber++;
    }

    public void setAttackingPiecesNumber(int attackingPiecesNumber) {
        this.attackingPiecesNumber = attackingPiecesNumber;
    }

    public void setCoordinates(ArrayList<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }

}
