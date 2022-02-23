package pieces;

import table.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de joc: bishop
 */
public class Bishop extends Piece {
    public Bishop(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.BISHOP, culoare, true);
    }


    /**
     * Calculeaza toate posibilele mutarile piesei
     *
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist care contine toate mutarile pe care un bishop le poate executa
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player) {
        ArrayList<Move> moves = new ArrayList<>();
        Coordinates currPosition = new Coordinates(getCurrPosition().getX(), getCurrPosition().getY());
        int dx, dy;
        boolean ok = true;

        dx = getCurrPosition().getX();
        dy = getCurrPosition().getY();
        while (dx >= 0 && dy >= 0 && ok) {
            dx--;
            dy--;
            if (isEmptySpace(table, new Coordinates(dx, dy)))
                moves.add(new Move(currPosition, new Coordinates(dx, dy)));
            else
                ok = false;
        }
        if (isPieceOtherColor(table, new Coordinates(dx, dy))) moves.add(new Move(currPosition, new Coordinates(dx, dy)));

        ok = true;
        dx = getCurrPosition().getX();
        dy = getCurrPosition().getY();
        while (dx >= 0 && dy <= 7 && ok) {
            dx--;
            dy++;
            if (isEmptySpace(table, new Coordinates(dx, dy)))
                moves.add(new Move(currPosition, new Coordinates(dx, dy)));
            else ok = false;

        }
        if (isPieceOtherColor(table, new Coordinates(dx, dy))) moves.add(new Move(currPosition, new Coordinates(dx, dy)));

        ok = true;
        dx = getCurrPosition().getX();
        dy = getCurrPosition().getY();
        while (dx <= 7 && dy <= 7 && ok) {
            dx++;
            dy++;
            if (isEmptySpace(table, new Coordinates(dx, dy)))
                moves.add(new Move(currPosition, new Coordinates(dx, dy)));
            else ok = false;

        }
        if (isPieceOtherColor(table, new Coordinates(dx, dy))) moves.add(new Move(currPosition, new Coordinates(dx, dy)));

        ok = true;
        dx = getCurrPosition().getX();
        dy = getCurrPosition().getY();
        while (dx <= 7 && dy >= 0 && ok) {
            dx++;
            dy--;
            if (isEmptySpace(table, new Coordinates(dx, dy)))
                moves.add(new Move(currPosition, new Coordinates(dx, dy)));
            else ok = false;

        }
        if (isPieceOtherColor(table, new Coordinates(dx, dy))) moves.add(new Move(currPosition, new Coordinates(dx, dy)));

        return moves;
    }

}
