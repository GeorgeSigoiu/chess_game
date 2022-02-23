package pieces;

import table.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de joc: knight
 */
public class Knight extends Piece {
    public Knight(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.KNIGHT, culoare,true);
    }

    /**
     * Metoda care returneaza toate mutarile posibile ale unei piese de tip cal.
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist care contine toate mutarile posibile unei piese de tip cal (nu se tine cont daca regele
     * ramane in sah dupa executarea mutarii)
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player) {
        ArrayList<Move> moves=new ArrayList<Move>();
        ArrayList<Coordinates> allMoves=new ArrayList<>();
        allMoves.add(new Coordinates(getCurrPosition().getX()-2, getCurrPosition().getY()-1));
        allMoves.add(new Coordinates(getCurrPosition().getX()-2, getCurrPosition().getY()+1));
        allMoves.add(new Coordinates(getCurrPosition().getX()-1, getCurrPosition().getY()-2));
        allMoves.add(new Coordinates(getCurrPosition().getX()+1, getCurrPosition().getY()-2));
        allMoves.add(new Coordinates(getCurrPosition().getX()+2, getCurrPosition().getY()-1));
        allMoves.add(new Coordinates(getCurrPosition().getX()+2, getCurrPosition().getY()+1));
        allMoves.add(new Coordinates(getCurrPosition().getX()+1, getCurrPosition().getY()+2));
        allMoves.add(new Coordinates(getCurrPosition().getX()-1, getCurrPosition().getY()+2));

        Coordinates currPosition=new Coordinates(getCurrPosition().getX(), getCurrPosition().getY());
        for (Coordinates coordonate : allMoves) {
            if(isEmptySpace(table,coordonate))moves.add(new Move(currPosition,coordonate));
            else if(isPieceOtherColor(table,coordonate))moves.add(new Move(currPosition,coordonate));
        }
        return moves;
    }

}
