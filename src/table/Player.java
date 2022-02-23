package table;

import auxiliar.*;

import java.util.ArrayList;

/**
 * Clasa care descrie un jucator de sah.
 */
public class Player {
    /**
     * retine cate mutari a efectuat jucatorul
     */
    private int numberMoves;
    /**
     * culoarea cu care joaca jucatorul
     */
    private final String color;
    /**
     * retine pozitia regelui
     */
    private Coordinates kingPosition;
    /**
     * daca este in sah regele jucatorului
     */
    private boolean isCheck;
    /**
     * de ce piesa este atacat regele jucatorului
     */
    private AttackingPiece attackingPiece;

    public Player(String culoare, Coordinates pozitieRege) {
        this.color = culoare;
        this.kingPosition = pozitieRege;
        this.isCheck = false;
        numberMoves = 0;
    }

    /**
     * Determina culoarea cu care joaca adversarul
     *
     * @return String, reprezinta culoarea adversarului
     */
    public String getOtherCuloare() {
        if (color.equals("alb")) return "negru";
        else return "alb";
    }

    /**
     * Metoda ce returneaza true daca regele este in sah.
     *
     * @param t tabla de joc
     * @return true daca regele jucatorului este in sah, false in caz contrar
     */
    public boolean kingInCheck(ChessTable t) {
        attackingPiece = t.attackingPosition(kingPosition, getOtherCuloare());
        isCheck = t.attackingPosition(kingPosition, getOtherCuloare()).isAttacking();
        return isCheck;
    }

    /**
     * Metoda care returneaza true daca jucatorul este in sah mat.
     *
     * @param table ChessTable, tabla de joc
     * @return true daca jucatorul este in sah mat, false in caz contrar
     */
    public boolean checkmate(ChessTable table) {
        boolean sah = kingInCheck(table);
        if (!sah) return false;
        if (moveKing(table)) return false;
        return !protectKing(table);
    }

    /**
     * Metoda care returneaza true daca se pot muta alte piese astfel incat regele sa nu mai fie in sah.
     * <p>
     * Caut in tabla de joc toate piesele de aceeasi culoare cu a jucatorului si verific daca pot muta piesa
     * respectiva in asa fel incat regele sa nu mai fie in sah.
     * </p>
     *
     * @param table ChessTable, tabla de joc
     * @return true daca se pot muta piese de pe tabla de joc ca sa apere regele, false in caz contrar
     */
    public boolean protectKing(ChessTable table) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (!(i == kingPosition.getX() && j == kingPosition.getY()))
                    if (table.getTable()[i][j] != null && table.getTable()[i][j].getColor().equals(color)) {
                        ArrayList<Move> m = table.getTable()[i][j].legalMoves(table, this);
                        if (m.size() != 0) return true;
                    }
        return false;
    }

    /**
     * Metoda care returneaza true daca pot muta regele pe alta pozitie care nu este atacata de vreo piesa de alta
     * culoare.
     *
     * @param table ChessTable, tabla de joc
     * @return true daca pot muta regele pe o alta pozitie
     */
    public boolean moveKing(ChessTable table) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (!(i == 0 && j == 0) && !table.attackingPosition(new Coordinates(kingPosition.getX() + i, kingPosition.getY() + j), getOtherCuloare()).isAttacking()) {
                    if (table.isLocLiber(new Coordinates(kingPosition.getX() + i, kingPosition.getY() + j)))
                        return true;
                    else if (table.isPiesa(new Coordinates(kingPosition.getX() + i, kingPosition.getY() + j), getOtherCuloare()))
                        return true;
                }
        return false;
    }

    public String getColor() {
        return color;
    }

    public void setKingPosition(Coordinates kingPosition) {
        this.kingPosition = kingPosition;
    }

    public int getNumberMoves() {
        return numberMoves;
    }

    public void incNrMutari() {
        this.numberMoves++;
    }

    public void setNumberMoves(int numberMoves) {
        this.numberMoves = numberMoves;
    }

    public Coordinates getKingPosition() {
        return kingPosition;
    }

    /**
     * Metoda care cloneaza un jucator.
     * <p>
     * Se creeaza un nou jucator cu aceleasi atribute si se returneaza jucatorul nou creat.
     * </p>
     *
     * @return un jucator clonats identic cu cel original
     */
    public Player clonePlayer() {
        Player newJucator = new Player(this.getColor(), new Coordinates(kingPosition.getX(), kingPosition.getY()));
        newJucator.isCheck = this.isCheck;
        newJucator.numberMoves = this.numberMoves;
        return newJucator;
    }
}
