package pieces;

import table.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de sah: rook
 */
public class Rook extends Piece {
    public Rook(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.ROOK, culoare,true);
    }

    /**
     * Metoda care returneaza toate mutarile posibile pe care le poate executa o puiesa de tip turn.
     * @param table ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist care contine toate mutarile pe care un turn le poate executa
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player){
        ArrayList<Move> moves=new ArrayList<>();
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
        return moves;
    }

}
