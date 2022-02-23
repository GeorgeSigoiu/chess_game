package pieces;

import auxiliar.*;
import table.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de sah: queen
 */
public class Queen extends Piece {
    public Queen(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.QUEEN, culoare,true);
    }

    /**
     * Metoda care returneaza toate mutarile care se pot executa de o piesa de tip regina.
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist cu toate mutarile posibile a unei piese de tip regina
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player) {
        ArrayList<Move> moves=new ArrayList<Move>();
        Coordinates currPosition=new Coordinates(getCurrPosition().getX(), getCurrPosition().getY());
        int dx,dy;
        boolean ok=true;

        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx<=7 && ok){
            dx++;
            if(isEmptySpace(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else
            {
                ok=false;
                if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            }
        }

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dy<=7 && ok){
            dy++;
            if(isEmptySpace(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else
            {
                ok=false;
                if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            }
        }

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx>=0 && ok){
            dx--;
            if(isEmptySpace(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else
            {
                ok=false;
                if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            }
        }

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dy>=0 && ok){
            dy--;
            if(isEmptySpace(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else
            {
                ok=false;
                if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            }
        }

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx>=0 && dy>=0 && ok) {
            dx--;dy--;
            if(isEmptySpace(table,new Coordinates(dx,dy)))
                moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else
                ok=false;
        }
        if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx>=0 && dy<=7 && ok){
            dx--;
            dy++;
            if(isEmptySpace(table,new Coordinates(dx,dy)))
                moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else ok=false;

        }
        if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx<=7 && dy<=7 && ok){
            dx++;dy++;
            if(isEmptySpace(table,new Coordinates(dx,dy)))
                moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else ok=false;

        }
        if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));

        ok=true;
        dx= getCurrPosition().getX();
        dy= getCurrPosition().getY();
        while(dx<=7 && dy>=0 && ok){
            dx++;dy--;
            if(isEmptySpace(table,new Coordinates(dx,dy)))
                moves.add(new Move(currPosition,new Coordinates(dx,dy)));
            else ok=false;

        }
        if(isPieceOtherColor(table,new Coordinates(dx,dy)))moves.add(new Move(currPosition,new Coordinates(dx,dy)));

        return moves;
    }

}
