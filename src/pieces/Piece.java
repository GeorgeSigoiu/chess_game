package pieces;

import table.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Clasa care descrie o piesa de sah.
 */
public abstract class Piece implements Cloneable {
    /**
     * retine numarul ultimei mutari a jucatorului
     */
    private int moveNumber;
    /**
     * pozitia de pe care piesa de sah a ajuns pe pozitia curenta
     */
    private Coordinates prevPosition;
    /**
     * pozitia pe care se alfa piesa de sah
     */
    private Coordinates currPosition;
    /**
     * tipul piesei
     */
    private final TipPiesa type;
    /**
     * culoarea piesei
     */
    private final String color;
    /**
     * daca este prima mutare (folosita pentru pion, rege, tura)
     */
    private boolean firstMove;

    public Piece(Coordinates pozitieCurenta, TipPiesa tipPiesa, String culoare, boolean firtstMove) {
        this.currPosition = pozitieCurenta;
        this.type = tipPiesa;
        this.color = culoare;
        this.firstMove = firtstMove;
        this.prevPosition = pozitieCurenta;
    }

    /**
     * Metoda returneaza toate mutarile pe care o piesa le poate executa.
     *
     * @param table  ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist cu toate mutarile pe care o piesa le poate face (fara a tine cont daca regele ramane in sah
     * dupa executarea mutarii)
     */
    public abstract ArrayList<Move> possibleMoves(ChessTable table, Player player);

    /**
     * Gaseste mutarile legale pe care le poate executa o piesa de sah.
     * <p>
     * Se determina toate mutarile pe care o piesa de acest tip le poate executa netinand cont daca regele ramane
     * in sah dupa executarea mutarii, dupa care se incearca fiecare mutare pe o tabla de joc clonata (identica cu
     * cea curenta) si cu un jucator clonat (identic cu jucatorul curent) pentru a verifica daca in urma unei mutari
     * regele va ramane in sah. Se retin intr-un arraylist noi mutarile legale care nu lasa regele in sah. Se
     * verifica si daca se poate efectua rocada mica sau rocada mare.
     * </p>
     *
     * @param table  ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist care retine doar mutarile legale ale unei piese
     */
    public ArrayList<Move> legalMoves(ChessTable table, Player player) {
        ArrayList<Move> moves = possibleMoves(table, player);
        ArrayList<Move> legalMoves1 = new ArrayList<>();

        if (moves.size() == 0) return legalMoves1;

        int var = player.getNumberMoves();

        for (Move mutare : moves) {
            Player player1 = player.clonePlayer();
            ChessTable tab = table.cloneTablaJoc();

            Piece piece = tab.getTable()[mutare.getCurrPosition().getX()][mutare.getCurrPosition().getY()];

            if (piece.getType().equals(TipPiesa.KING)) {
                player1.setKingPosition(mutare.getNextPosition());
            }
            tab.executaMutarea(mutare, player1);
            if (!player1.kingInCheck(tab)) legalMoves1.add(mutare);
        }

        player.setNumberMoves(var);

        if (this.getType().equals(TipPiesa.KING)) {
            King king = (King) this;
            int xx;
            if (player.getColor().equals("alb")) xx = 7;
            else xx = 0;
            if (king.littleCast(table, player)) legalMoves1.add(new Move(currPosition, new Coordinates(xx, 6)));
            if (king.bigCast(table, player)) legalMoves1.add(new Move(currPosition, new Coordinates(xx, 2)));
        }
        return legalMoves1;
    }

    /**
     * Determina culoarea adversarului.
     *
     * @return String, reprezinta culoarea jucatorului advers
     */
    public String getOtherColor() {
        if (color.equals("alb")) return "negru";
        else return "alb";
    }

    /**
     * Metoda care returneaza true daca pe pozitia respectiva din tabla de joc nu exista vreo piesa sau false
     * daca exista.
     *
     * @param table   ChessTable, tabla de joc
     * @param pozitie Coordinates, coordonatele pozitie care va fi verificata
     * @return boolean, true daca pozitia respectiva este null sau false in caz contrar
     */
    public boolean isEmptySpace(ChessTable table, Coordinates pozitie) {
        if (0 <= pozitie.getX() && pozitie.getX() <= 7 && 0 <= pozitie.getY() && pozitie.getY() <= 7) {
            if (table.getTable()[pozitie.getX()][pozitie.getY()] == null) return true;
            else return false;
        } else return false;
    }

    /**
     * Metoda care returneaza true daca piesa de pe pozitia "pozitie" este de alta cu culoare cu piesa respectiva.
     *
     * @param table   ChessTable, tabla de joc
     * @param pozitie Coordinates, coordonatele pozitiei care va fi verificata
     * @return true daca pe pozitia respectiva este o piesa de culoare diferita fata de piesa respectiva sau false daca
     * piesele sunt de aceeasi culoare
     */
    public boolean isPieceOtherColor(ChessTable table, Coordinates pozitie) {
        if (0 <= pozitie.getX() && pozitie.getX() <= 7 && 0 <= pozitie.getY() && pozitie.getY() <= 7) {
            if (table.getTable()[pozitie.getX()][pozitie.getY()] != null) {
                if (table.getTable()[pozitie.getX()][pozitie.getY()].getOtherColor().equals(getColor()))
                    return true;
                else return false;
            } else return false;
        } else return false;
    }

    public TipPiesa getType() {
        return type;
    }

    public Coordinates getCurrPosition() {
        return currPosition;
    }

    public String getColor() {
        return color;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setCurrPosition(Coordinates currPosition) {
        this.currPosition = currPosition;
    }

    public Coordinates getPrevPosition() {
        return prevPosition;
    }

    public void setPrevPosition(Coordinates prevPosition) {
        this.prevPosition = prevPosition;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    /**
     * Metoda care cloneaza o piesa de sah.
     * <p>
     * Se creeaza o noua piesa cu atributele piesei care apeleaza functia si se returneaza clona ei.
     * </p>
     *
     * @return returneaza clona piesei de sah
     */
    public Piece clonePiece() {
        Piece newPiesa;
        Piece thiss = this;
        if (thiss.getType().equals(Piece.TipPiesa.PAWN)) {
            newPiesa = new Pawn(thiss.getCurrPosition(), thiss.getColor());
            ((Pawn) newPiesa).setEnPassant(((Pawn) thiss).isEnPassant());
            ((Pawn) newPiesa).setTakenEnPassant(((Pawn) thiss).isTakenEnPassant());
        } else if (thiss.getType().equals(Piece.TipPiesa.ROOK)) {
            newPiesa = new Rook(thiss.getCurrPosition(), thiss.getColor());
        } else if (thiss.getType().equals(Piece.TipPiesa.BISHOP)) {
            newPiesa = new Bishop(thiss.getCurrPosition(), thiss.getColor());
        } else if (thiss.getType().equals(Piece.TipPiesa.KNIGHT)) {
            newPiesa = new Knight(thiss.getCurrPosition(), thiss.getColor());
        } else if (thiss.getType().equals(Piece.TipPiesa.QUEEN)) {
            newPiesa = new Queen(thiss.getCurrPosition(), thiss.getColor());
        } else {
            newPiesa = new King(thiss.getCurrPosition(), thiss.getColor());
        }
        Coordinates clonedPozitieAnterioara = new Coordinates(thiss.getPrevPosition().getX(), thiss.getPrevPosition().getY());
        newPiesa.setPrevPosition(clonedPozitieAnterioara);
        newPiesa.setMoveNumber(thiss.getMoveNumber());
        newPiesa.setFirstMove(thiss.isFirstMove());
        return newPiesa;
    }

    @Override
    public String toString() {
        if (color.equals("negru")) return type.name.toLowerCase();
        return type.name;
    }

    /**
     * Enumerare a tuturor pieselor de sah.
     */
    public enum TipPiesa {
        PAWN("P"),
        KING("K"),
        QUEEN("Q"),
        BISHOP("B"),
        ROOK("R"),
        KNIGHT("N");

        private final String name;

        TipPiesa(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

    }
}
