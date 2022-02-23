
package pieces;

import table.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de joc: king
 */
public class King extends Piece {
    public King(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.KING, culoare, true);
    }

    /**
     * Metoda returneaza toate mutarile posibile a regelui
     *
     * @param table tabla de joc
     * @param player jucatorul care executa mutarea
     * @return un arraylist cu toate mutarile posibile (nu se ia in calcul daca regele ramane in sah dupa mutarea piesei)
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player) {
        ArrayList<Move> moves = new ArrayList<Move>();
        Coordinates currPosition = new Coordinates(getCurrPosition().getX(), getCurrPosition().getY());

        ArrayList<Coordinates> allMoves = new ArrayList<>();
        allMoves.add(new Coordinates(currPosition.getX() - 1, currPosition.getY() - 1));
        allMoves.add(new Coordinates(currPosition.getX() - 1, currPosition.getY()));
        allMoves.add(new Coordinates(currPosition.getX() - 1, currPosition.getY() + 1));
        allMoves.add(new Coordinates(currPosition.getX() + 1, currPosition.getY() - 1));
        allMoves.add(new Coordinates(currPosition.getX() + 1, currPosition.getY()));
        allMoves.add(new Coordinates(currPosition.getX() + 1, currPosition.getY() + 1));
        allMoves.add(new Coordinates(currPosition.getX(), currPosition.getY() - 1));
        allMoves.add(new Coordinates(currPosition.getX(), currPosition.getY() + 1));

        for (Coordinates coordinates : allMoves) {
            if (isEmptySpace(table, coordinates)) moves.add(new Move(currPosition, coordinates));
            else if (isPieceOtherColor(table, coordinates)) moves.add(new Move(currPosition, coordinates));
        }
        return moves;
    }

    /**
     * Metoda care determina daca se poate efectua rocada mica.
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return true daca daca se poate efectua rocada mica
     */
    public boolean littleCast(ChessTable table, Player player) {
        if (!this.isFirstMove()) return false;
        if (player.getColor().equals("alb")) {
            if (table.isPiesa(new Coordinates(7, 7), TipPiesa.ROOK, "alb")) {
                if (!table.getTable()[7][7].isFirstMove()) return false;
                else {
                    if ((!table.attackingPosition(new Coordinates(7, 6), "negru").isAttacking() &&
                            !table.attackingPosition(new Coordinates(7, 5), "negru").isAttacking() &&
                            !table.attackingPosition(new Coordinates(7, 4), "negru").isAttacking()) &&
                            (table.getTable()[7][6]==null && table.getTable()[7][5]==null))return true;
                    else return false;
                }
            } else return false;
        } else {
            if (table.isPiesa(new Coordinates(0, 7), TipPiesa.ROOK, "negru")) {
                if (!table.getTable()[0][7].isFirstMove()) return false;
                else {
                    if ((!table.attackingPosition(new Coordinates(0, 6), "alb").isAttacking() &&
                            !table.attackingPosition(new Coordinates(0, 5), "alb").isAttacking() &&
                            !table.attackingPosition(new Coordinates(0, 4), "alb").isAttacking())&&
                    (table.getTable()[0][6]==null && table.getTable()[0][5]==null)) return true;
                    else return false;
                }
            } else return false;
        }
    }

    /**
     * Metoda care determina daca se poate efectua rocada mare.
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return true daca daca se poate efectua rocada mare
     */
    public boolean bigCast(ChessTable table, Player player) {
        if (!this.isFirstMove()) return false;
        if (player.getColor().equals("alb")) {
            if(table.isPiesa(new Coordinates(7,0),TipPiesa.ROOK,"alb")){
                if(table.getTable()[7][0].isFirstMove()){
                    if((!table.attackingPosition(new Coordinates(7,2),"negru").isAttacking() &&
                            !table.attackingPosition(new Coordinates(7,3),"negru").isAttacking() &&
                            !table.attackingPosition(new Coordinates(7,4),"negru").isAttacking()) &&
                            (table.getTable()[7][1]==null && table.getTable()[7][2]==null && table.getTable()[7][3]==null))return true;
                    else return false;
                }else return false;
            }else return false;
        } else {
            if(table.isPiesa(new Coordinates(0,0),TipPiesa.ROOK,"negru")){
                if(table.getTable()[0][0].isFirstMove()){
                    if((!table.attackingPosition(new Coordinates(0,2),"alb").isAttacking() &&
                            !table.attackingPosition(new Coordinates(0,3),"alb").isAttacking() &&
                            !table.attackingPosition(new Coordinates(0,4),"alb").isAttacking()) &&
                            (table.getTable()[0][2]==null && table.getTable()[0][3]==null && table.getTable()[0][1]==null))return true;
                    else return false;
                }else return false;
            }else return false;
        }
    }

}
