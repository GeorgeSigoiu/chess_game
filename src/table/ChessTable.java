package table;

import pieces.*;
import auxiliar.*;

import java.util.ArrayList;

/**
 * Reprezinta tabla de joc
 */
public class ChessTable {
    /**
     * matrice in care sunt plasate piesele de joc
     */
    private final Piece[][] table = new Piece[8][8];

    /**
     * Se creeaza tabla de joc si se plaseaza piesele pe tabla
     */
    public ChessTable() {
        for (int i = 0; i < 8; i++) {
            table[1][i] = new Pawn(new Coordinates(1, i), "negru");
            table[6][i] = new Pawn(new Coordinates(6, i), "alb");
        }
        table[0][0] = new Rook(new Coordinates(0, 0), "negru");
        table[0][1] = new Knight(new Coordinates(0, 1), "negru");
        table[0][2] = new Bishop(new Coordinates(0, 2), "negru");
        table[0][3] = new Queen(new Coordinates(0, 3), "negru");
        table[0][4] = new King(new Coordinates(0, 4), "negru");
        table[0][5] = new Bishop(new Coordinates(0, 5), "negru");
        table[0][6] = new Knight(new Coordinates(0, 6), "negru");
        table[0][7] = new Rook(new Coordinates(0, 7), "negru");
        table[7][0] = new Rook(new Coordinates(7, 0), "alb");
        table[7][1] = new Knight(new Coordinates(7, 1), "alb");
        table[7][2] = new Bishop(new Coordinates(7, 2), "alb");
        table[7][3] = new Queen(new Coordinates(7, 3), "alb");
        table[7][4] = new King(new Coordinates(7, 4), "alb");
        table[7][5] = new Bishop(new Coordinates(7, 5), "alb");
        table[7][6] = new Knight(new Coordinates(7, 6), "alb");
        table[7][7] = new Rook(new Coordinates(7, 7), "alb");
    }

    public ChessTable(String s) {
    }

    /**
     * Metoda care afiseaza tabla de joc.
     */
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
            System.out.print("\t\t\t\t\t\t\t\t");
            for (int j = 0; j < 8; j++)
                if (table[i][j] != null) System.out.print(table[i][j].toString() + " ");
                else System.out.print("- ");
            System.out.println(i);
        }
        System.out.print("\t\t\t\t\t\t\t\t");
        for (int i = 0; i < 8; i++)
            System.out.print(i + " ");
        System.out.println();
    }

    public Piece[][] getTable() {
        return table;
    }

    /**
     * Metoda care returneaza true daca o anumita pozitie de pe tabla de joc este goala.
     * @param pozitie Coordinates, coordonatele unui spatiu de pe tabla de joc
     * @return true daca pe pozitia respectiva nu se afla o piesa
     */
    public boolean isLocLiber(Coordinates pozitie) {
        if (0 <= pozitie.getX() && pozitie.getX() <= 7 && 0 <= pozitie.getY() && pozitie.getY() <= 7) {
            return table[pozitie.getX()][pozitie.getY()] == null;
        } else return false;
    }

    /**
     * Metoda care executa mutarea unei piese de pe o pozitie curenta pe o pozitie urmatoare.
     * <p>
     *     Prima data mutam piesa pe pozitia dorita dupa care se verifica daca piesa mutata este pion si daca s-a mutat
     *     in en passant. Se verifica si daca un pion ajunge pe ultima pozitie si promoveaza la regina. Daca se muta
     *     regele modificam coordonatele si in "jucator".
     * </p>
     * @param move Move, mutarea care va fi executata
     * @param player Player, jucatorul care executa mutarea
     */
    public void executaMutarea(Move move, Player player) {
        player.incNrMutari();//a cata mutare a jucatorului este
        table[move.getCurrPosition().getX()][move.getCurrPosition().getY()].setMoveNumber(player.getNumberMoves());//numarul mutarii la care s-a mutat piesa respectiva
        table[move.getNextPosition().getX()][move.getNextPosition().getY()] = table[move.getCurrPosition().getX()][move.getCurrPosition().getY()];//mutam piesa
        table[move.getNextPosition().getX()][move.getNextPosition().getY()].setPrevPosition(move.getCurrPosition());//setam pozitia de unde am mutat piesa
        table[move.getNextPosition().getX()][move.getNextPosition().getY()].setCurrPosition(move.getNextPosition());//setam pozitia curenta unde se afla piesa
        table[move.getNextPosition().getX()][move.getNextPosition().getY()].setFirstMove(false);//prima mutare s-a efectuat
        table[move.getCurrPosition().getX()][move.getCurrPosition().getY()] = null;//pe pozitia de unde am mutat nu mai exista nimic

        if (table[move.getNextPosition().getX()][move.getNextPosition().getY()].getType().equals(Piece.TipPiesa.PAWN)) {//daca piesa mutata este pion
            Piece piesa = table[move.getNextPosition().getX()][move.getNextPosition().getY()];
            Pawn pion = (Pawn) piesa;
            Piece pST = move.getNextPosition().getX() - 1 >= 0 ? table[move.getNextPosition().getX() - 1][move.getNextPosition().getY()] : null;
            Piece pDR = move.getNextPosition().getX() + 1 < 8 ? table[move.getNextPosition().getX() + 1][move.getNextPosition().getY()] : null;
            if (pion.isEnPassant()) {//daca am mutat en passant
                if (player.getColor().equals("alb") && pDR != null && pDR.getType().equals(Piece.TipPiesa.PAWN) && ((Pawn) pDR).isTakenEnPassant()) {
                    table[move.getNextPosition().getX() + 1][move.getNextPosition().getY()] = null;
                } else if (player.getColor().equals("negru") && pST != null && pST.getType().equals(Piece.TipPiesa.PAWN) && ((Pawn) pST).isTakenEnPassant()) {
                    table[move.getNextPosition().getX() - 1][move.getNextPosition().getY()] = null;
                }
            }
        }

        //promovare pion la regina
        if (table[move.getNextPosition().getX()][move.getNextPosition().getY()].getType().equals(Piece.TipPiesa.PAWN)) {
            if (move.getNextPosition().getX() == 7 || move.getNextPosition().getX() == 0)
                table[move.getNextPosition().getX()][move.getNextPosition().getY()] = new Queen(new Coordinates(move.getNextPosition().getX(), move.getNextPosition().getY()), table[move.getNextPosition().getX()][move.getNextPosition().getY()].getColor());
        }

        if (table[move.getNextPosition().getX()][move.getNextPosition().getY()].getType().equals(Piece.TipPiesa.KING)){
            player.setKingPosition(move.getNextPosition());
            int d=Math.abs( move.getCurrPosition().getY()-move.getNextPosition().getY());
            int xx;
            if(player.getColor().equals("alb"))xx=7;
            else xx=0;
            if(d==2){//daca s-a efectuat o rocada
                if(move.getNextPosition().getY()==6){//rocada mica
                    table[xx][5] = table[xx][0];//mutam tura
                    table[xx][5].setPrevPosition(new Coordinates(xx,7));//setam pozitia de unde am mutat tura
                    table[xx][5].setCurrPosition(new Coordinates(xx,5));//setam pozitia curenta unde se afla tura
                    table[xx][5].setFirstMove(false);//prima mutare s-a efectuat
                    table[xx][7] = null;//pe pozitia de unde am mutat nu mai exista nimic
                }
                if(move.getNextPosition().getY()==2){//rocada mare
                    table[xx][3] = table[xx][0];//mutam tura
                    table[xx][3].setPrevPosition(new Coordinates(xx,0));//setam pozitia de unde am mutat tura
                    table[xx][3].setCurrPosition(new Coordinates(xx,3));//setam pozitia curenta unde se afla tura
                    table[xx][3].setFirstMove(false);//prima mutare s-a efectuat
                    table[xx][0] = null;//pe pozitia de unde am mutat nu mai exista nimic
                }
            }

        }
    }

    /**
     * Metoda verifica daca coordonatele se afla in interiorul tablei.
     * @param x int, coordonata orizontala
     * @param y int, coordonata verticala
     * @return true daca x si y au valori cuprinse intre 0 si 7
     */
    public boolean isOnTable(int x, int y) {
        return 0 <= x && x <= 7 && 0 <= y && y <= 7;
    }


    /**
     * Metoda care returneaza informatii referitoare la piesa care ataca poitita poz.
     * <p>
     *     Se verifica prima daca daca pozitia este atacata de tura sau regina, verificand pe orizontala sau pe
     *     verticala pornind de la "poz" daca se gaseste o tura sau o regina de culoare diferita, dupa care se verifica
     *     pe diagonale daca pozitia "poz" este atacata de un nebun sau o regina. In continuare verificam daca este
     *     atacata de cal sau de pion.
     * </p>
     * @param pos Coordinates, coordonatele unei pozitii de pe tabla de joc
     * @param attackingPlayer String, culoarea jucatorului care ataca jucatorul care executa mutarea
     * @return un obiect de tip PiesaAtaca care reprezinta piesa care ataca pozitia poz
     */
    public AttackingPiece attackingPosition(Coordinates pos, String attackingPlayer) {
        AttackingPiece ps = new AttackingPiece();
        ps.setType(null);
        ps.setPosition(null);
        ps.setAttacking(false);
        ps.setAttackingPiecesNumber(0);

        ArrayList<Coordinates> positions = new ArrayList<>();//pozitiile dintre piesa atacata si piesa care ataca

        int x = pos.getX();
        int y = pos.getY();
        int i, j;
        //tura-regina
        for (i = x + 1; isLocLiber(new Coordinates(i, y)); i++)
            positions.add(new Coordinates(i, y));
        if ((isPiesa(new Coordinates(i, y), Piece.TipPiesa.ROOK) || isPiesa(new Coordinates(i, y), Piece.TipPiesa.QUEEN))
                && table[i][y].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, y));
            ps.setType(table[i][y].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = y + 1; isLocLiber(new Coordinates(x, i)); i++)
            positions.add(new Coordinates(x, i));
        if ((isPiesa(new Coordinates(x, i), Piece.TipPiesa.ROOK) || isPiesa(new Coordinates(x, i), Piece.TipPiesa.QUEEN))
                && table[x][i].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(x, i));
            ps.setType(table[x][i].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = x - 1; isLocLiber(new Coordinates(i, y)); i--)
            positions.add(new Coordinates(i, y));
        if ((isPiesa(new Coordinates(i, y), Piece.TipPiesa.ROOK) || isPiesa(new Coordinates(i, y), Piece.TipPiesa.QUEEN))
                && table[i][y].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, y));
            ps.setType(table[i][y].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = y - 1; isLocLiber(new Coordinates(x, i)); i--)
            positions.add(new Coordinates(x, i));
        if ((isPiesa(new Coordinates(x, i), Piece.TipPiesa.ROOK) || isPiesa(new Coordinates(x, i), Piece.TipPiesa.QUEEN))
                && table[x][i].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(x, i));
            ps.setType(table[x][i].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        //nebun-regina
        for (i = x - 1, j = y - 1; isLocLiber(new Coordinates(i, j)); i--, j--)
            positions.add(new Coordinates(i, j));
        if ((isPiesa(new Coordinates(i, j), Piece.TipPiesa.BISHOP) || isPiesa(new Coordinates(i, j), Piece.TipPiesa.QUEEN))
                && table[i][j].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, j));
            ps.setType(table[i][j].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = x - 1, j = y + 1; isLocLiber(new Coordinates(i, j)); i--, j++)
            positions.add(new Coordinates(i, j));
        if ((isPiesa(new Coordinates(i, j), Piece.TipPiesa.BISHOP) || isPiesa(new Coordinates(i, j), Piece.TipPiesa.QUEEN))
                && table[i][j].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, j));
            ps.setType(table[i][j].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = x + 1, j = y - 1; isLocLiber(new Coordinates(i, j)); i++, j--)
            positions.add(new Coordinates(i, j));
        if ((isPiesa(new Coordinates(i, j), Piece.TipPiesa.BISHOP) || isPiesa(new Coordinates(i, j), Piece.TipPiesa.QUEEN))
                && table[i][j].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, j));
            ps.setType(table[i][j].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        for (i = x + 1, j = y + 1; isLocLiber(new Coordinates(i, j)); i++, j++)
            positions.add(new Coordinates(i, j));
        if ((isPiesa(new Coordinates(i, j), Piece.TipPiesa.BISHOP) || isPiesa(new Coordinates(i, j), Piece.TipPiesa.QUEEN))
                && table[i][j].getColor().equals(attackingPlayer)) {
            ArrayList<Coordinates> positions2;
            positions2 = (ArrayList<Coordinates>) positions.clone();
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(i, j));
            ps.setType(table[i][j].getType());
            ps.incCateAtaca();
            ps.setCoordinates(positions2);
        }
        positions.clear();

        //cal
        if (isPiesa(new Coordinates(x - 2, y - 1), Piece.TipPiesa.KNIGHT)
                && table[x - 2][y - 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x - 2, y - 1));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x - 2, y + 1), Piece.TipPiesa.KNIGHT)
                && table[x - 2][y + 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x - 2, y + 1));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x - 1, y - 2), Piece.TipPiesa.KNIGHT)
                && table[x - 1][y - 2].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x - 1, y - 2));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x + 1, y - 2), Piece.TipPiesa.KNIGHT)
                && table[x + 1][y - 2].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x + 1, y - 2));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x + 2, y - 1), Piece.TipPiesa.KNIGHT)
                && table[x + 2][y - 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x + 2, y - 1));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x + 2, y + 1), Piece.TipPiesa.KNIGHT)
                && table[x + 2][y + 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x + 2, y + 1));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x + 1, y + 2), Piece.TipPiesa.KNIGHT)
                && table[x + 1][y + 2].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x + 1, y + 2));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x - 1, y + 2), Piece.TipPiesa.KNIGHT)
                && table[x - 1][y + 2].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setType(Piece.TipPiesa.KNIGHT);
            ps.setPosition(new Coordinates(x - 1, y + 2));
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }

        //pion
        int dx = attackingPlayer.equals("alb") ? 1 : -1;
        if (isPiesa(new Coordinates(x + dx, y - 1), Piece.TipPiesa.PAWN)
                && table[x + dx][y - 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(x + dx, y - 1));
            ps.setType(Piece.TipPiesa.PAWN);
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }
        if (isPiesa(new Coordinates(x + dx, y + 1), Piece.TipPiesa.PAWN)
                && table[x + dx][y + 1].getColor().equals(attackingPlayer)) {
            ps.setAttacking(true);
            ps.setPosition(new Coordinates(x + dx, y + 1));
            ps.setType(Piece.TipPiesa.PAWN);
            ps.incCateAtaca();
            ps.setCoordinates(null);
        }

        for (i = -1; i <= 1; i++) {
            for (j = -1; j <= 1; j++) {
                if (!(i == 0 && j == 0)) {
                    if (isPiesa(new Coordinates(x+i, y+j), Piece.TipPiesa.KING)
                            && table[x +i][y + j].getColor().equals(attackingPlayer)) {
                        ps.setAttacking(true);
                        ps.setPosition(new Coordinates(x+i, y+j));
                        ps.setType(Piece.TipPiesa.KING);
                        ps.incCateAtaca();
                        ps.setCoordinates(null);
                    }
                }
            }
        }
        return ps;
    }

    /**
     * Metoda care verifica daca este egalitate, daca raman doar regii pe tabla.
     * @return true daca raman doar regii pe tabla.
     */
    public boolean esteEgalitate(){
        int i,j;
        int nrPiese=0;
        for(i=0;i<8;i++){
            for(j=0;j<8;j++){
                if(table[i][j]!=null && !table[i][j].getType().equals(Piece.TipPiesa.KING))
                    nrPiese++;
            }
        }
        return nrPiese == 0;
    }


    /**
     * Metoda care verifica daca pe pozitia "c" se afla o piesa de culoare "culoare".
     * @param c coordonatele unei piese de sah
     * @param culoare string care reprezinta culoarea alb sau negru
     * @return true daca la coordonatele c se afla o piesa de culoare "culoare"
     */
    public boolean isPiesa(Coordinates c, String culoare) {
        if (isOnTable(c.getX(), c.getY())) {
            if (table[c.getX()][c.getY()] != null)
                return table[c.getX()][c.getY()].getColor().equals(culoare);
        }
        return false;
    }

    /**
     * Metoda care verifica daca la pozitia "pozitie" se afla o piesa de tipul "tip".
     * @param pozitie coordonatele unui spatiu de pe tabla de joc
     * @param tip tipul care se verifica
     * @return true daca la pozitia respectiva este o piesa de acest tip
     */
    public boolean isPiesa(Coordinates pozitie, Piece.TipPiesa tip) {
        if (isOnTable(pozitie.getX(), pozitie.getY())) {
            return table[pozitie.getX()][pozitie.getY()] != null && table[pozitie.getX()][pozitie.getY()].getType().equals(tip);
        } else return false;
    }

    /**
     * Metoda care verifica daca la pozitia "pozitie" se afla o piesa de tipul "tip" si culoarea "culoare".
     * @param pozitie coordonatele unui spatiu de pe tabla de joc
     * @param tip tipul care se cere a fi verificat
     * @param culoare culoarea care se verifica
     * @return true daca este o piesa de tipul "|tip" si culoarea "culoare"
     */
    public boolean isPiesa(Coordinates pozitie, Piece.TipPiesa tip, String culoare) {
        if (isOnTable(pozitie.getX(), pozitie.getY())) {
            return table[pozitie.getX()][pozitie.getY()] != null
                    && table[pozitie.getX()][pozitie.getY()].getType().equals(tip)
                    && culoare.equals(table[pozitie.getX()][pozitie.getY()].getColor());
        } else return false;
    }

    /**
     * Clonarea tablei de joc.
     * @return o clona a tablei de joc
     */
    public ChessTable cloneTablaJoc() {
        //copiem this -> cloned
        ChessTable cloned = new ChessTable("goala");
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {

                if (table[i][j] == null) cloned.table[i][j] = null;
                else
                    cloned.table[i][j] = table[i][j].clonePiece();
            }
        return cloned;
    }
}
