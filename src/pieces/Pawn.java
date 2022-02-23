package pieces;

import auxiliar.*;
import table.*;

import java.util.ArrayList;

/**
 * Reprezinta piesa de sah: pawn
 */
public class Pawn extends Piece {
    /**
     * daca pionul respectiv are posibilitatea de a ataca un alt pion in en passant
     */
    private boolean enPassant = false;
    /**
     * daca pionul respectiv poate fi atacat de catre un alt pion in en passant
     */
    private boolean takenEnPassant = false;

    public Pawn(Coordinates pozitie, String culoare) {
        super(pozitie, TipPiesa.PAWN, culoare, true);
    }

    /**
     * Metoda returneaza toate mutarile posibile care pot fi executate de pion.
     *
     * @param table  ChessTable, tabla de joc
     * @param player Player, jucatorul care executa mutarea
     * @return un arraylist care contine toate mutarile posibile ale unui pion
     */
    @Override
    public ArrayList<Move> possibleMoves(ChessTable table, Player player) {
        ArrayList<Move> moves = new ArrayList<>();
        int dx;
        if (getColor().equals("alb")) dx = -1;
        else dx = 1;

        if (isEmptySpace(table, new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY()))) {
            moves.add(new Move(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY()), new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY())));
            if (isFirstMove()) {
                if (isEmptySpace(table, new Coordinates(getCurrPosition().getX() + 2 * dx, getCurrPosition().getY()))) {
                    moves.add(new Move(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY()), new Coordinates(getCurrPosition().getX() + 2 * dx, getCurrPosition().getY())));
                    takenEnPassant = true;
                }
            }
        }
        if (isPieceOtherColor(table, new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() + 1)))
            moves.add(new Move(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY()), new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() + 1)));

        if (isPieceOtherColor(table, new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() - 1)))
            moves.add(new Move(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY()), new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() - 1)));


        int numMove = player.getNumberMoves();
        if (player.getColor().equals("negru")) numMove++;

        if (table.isPiesa(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY() + 1), TipPiesa.PAWN, player.getOtherCuloare())) { //daca in dreapta lui ii un pion de culoare diferita
            if (table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() + 1].getMoveNumber() == numMove) {//daca mutarea pionului de culoare diferita a fost efectuata runda trecuta
                if (Math.abs(table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() + 1].getCurrPosition().getX() - table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() + 1].getPrevPosition().getX()) == 2) {//daca s-a mutat 2 spatii
                    enPassant = true;
                    moves.add(new Move(getCurrPosition(), new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() + 1)));
                }
            }
        }

        if (table.isPiesa(new Coordinates(getCurrPosition().getX(), getCurrPosition().getY() - 1), TipPiesa.PAWN, player.getOtherCuloare())) { //daca in dreapta lui ii un pion de culoare diferita
            if (table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() - 1].getMoveNumber() == numMove) {//daca mutarea pionului de culoare diferita a fost efectuata runda trecuta
                if (Math.abs(table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() - 1].getCurrPosition().getX() - table.getTable()[getCurrPosition().getX()][getCurrPosition().getY() - 1].getPrevPosition().getX()) == 2) {//daca s-a mutat 2 spatii
                    enPassant = true;
                    moves.add(new Move(getCurrPosition(), new Coordinates(getCurrPosition().getX() + dx, getCurrPosition().getY() - 1)));
                }
            }
        }

        return moves;
    }

    public boolean isEnPassant() {
        return enPassant;
    }

    public boolean isTakenEnPassant() {
        return takenEnPassant;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public void setTakenEnPassant(boolean takenEnPassant) {
        this.takenEnPassant = takenEnPassant;
    }
}
