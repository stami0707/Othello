import game.engine.utils.Utils;
import game.oth.OthelloAction;
import game.oth.OthelloGame;
import game.oth.OthelloPlayer;

import java.util.Random;

/**
 * @author Süli Tamara
 * @version 1.0.0.
 */

/**
 * Az OthelloPlayer egy megvalositasa, ami a minimax algoritmus alapján határozza meg a kovetkezo lepeset.
 */
public class Agent extends OthelloPlayer{

    /**
     * Maximalis keresesi melyseg.
     */
    private final int maxDepth = 5;

    /**
     * Letrehez egy Agenst, a super class-a konstruktoranak hivasaval.
     * @param color - Agens szine
     * @param board - Jatektabla
     * @param random - Random szam generator
     */
    public Agent(int color, int[][] board, Random random) {
        super(color, board, random);
    }

    /**
     * Meghatarozza a parameterben kapott jatektabla erteket.
     * @param prev_board - Jatektabla
     * @return - Az adott tablan mennyivel ural tobb mezot az Agens.
     */
    public int getScore(int [][] prev_board) {
        int ret = 0;

        for (int[] ints : prev_board) {
            for (int anInt : ints) {
                if (anInt == this.color) {
                    ret += 1;
                } else if (anInt == (1 - this.color)) {
                    ret -= 1;
                }

            }
        }

        return ret;
    }

    /**
     * Meglepi az ellenfel lepeset a kapott parameterek alapjan. Teszteli, hogy vege van-e a jateknak, ha nem,
     * akkor noveli a melyseget, kivalasztja a feljovo min ertekek maximumat és megfeleleoen beallitja alfa erteket.
     * @param prev_board - Jatektabla
     * @param prev_i - Elozo lepes i erteke
     * @param prev_j - Elozo lepes j erteke
     * @param depth - Aktualis meyseg
     * @param alpha - Alfa ertek a vagashoz
     * @param beta - Beta ertek a vagashoz
     * @return - Min ertekek maximuma
     */
    public int Max(int [][] prev_board, int prev_i, int prev_j, int depth, int alpha, int beta) {
        OthelloGame.setAction(prev_board, prev_i, prev_j, 1-color);

        if (depth == maxDepth || isFinished(prev_board, color)){
            return getScore(prev_board);
        }

        depth++;

        int max_score = Integer.MIN_VALUE;
        int value;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (OthelloGame.isValid(prev_board, i, j, color)) {
                    value = Min(Utils.copy(prev_board), i, j, depth, alpha, beta);
                    max_score = Math.max(value, max_score);
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha) return max_score;
                }
            }
        }

        return max_score;
    }

    /**
     * Meglepi az ellenfel lepeset a kapott parameterek alapjan. Teszteli, hogy vege van-e a jateknak, ha nem,
     * akkor noveli a melyseget, kivalasztja a feljovo max ertekek minimumat és megfeleleoen beallitja beta erteket.
     * @param prev_board - Jatektabla
     * @param prev_i - Elozo lepes i erteke
     * @param prev_j - Elozo lepes j erteke
     * @param depth - Aktualis meyseg
     * @param alpha - Alfa ertek a vagashoz
     * @param beta - Beta ertek a vagashoz
     * @return - Max ertekek minimumaja
     */
    public int Min(int [][] prev_board, int prev_i, int prev_j, int depth, int alpha, int beta){
        OthelloGame.setAction(prev_board, prev_i, prev_j, color);

        if (depth == maxDepth || isFinished(prev_board, 1-color)){
            return getScore(prev_board);
        }

        int min_score = Integer.MAX_VALUE;
        int value;

        depth++;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (OthelloGame.isValid(prev_board, i, j, 1-color)) {
                    value = Max(Utils.copy(prev_board), i, j, depth, alpha, beta);
                    min_score = Math.min(value, min_score);
                    beta = Math.min(beta, value);
                    if (beta <= alpha) return min_score;
                }
            }
        }

        return min_score;

    }

    /**
     * Eldonti, hogy veget ert-e a jatek az adott tablan az alapjan, hogy van-e meg lepese aa adott jatekosoknak.
     * @param prev_board - Jatektabla
     * @return - igen/nem
     */
    public boolean isFinished(int [][] prev_board, int color) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (OthelloGame.isValid(prev_board, i, j, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * A getAction fgv. megvalositasa, ami megadja az Agans kovetkezo akciojat.
     * Frissiti a tablajat a kapott parameter alapjan, ezutan elkezdi futtatni a minimax algoritmust és
     * kivalasztja, melyik lepesnek a legnagyobb a pontja, azt fogja valasztani és visszaadni.
     * @param prevAction - Elozo lepes.
     * @param remainingTimes - Gondolkodasi ido
     * @return - Az Agens lepese.
     */
    @Override
    public OthelloAction getAction(OthelloAction prevAction, long[] remainingTimes) {
        if (prevAction != null) {
            OthelloGame.setAction(board, prevAction.i, prevAction.j, 1 - color);
        }

        OthelloAction myAction = null;
        int actionScore = Integer.MIN_VALUE;
        int value;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (OthelloGame.isValid(Utils.copy(board), i, j, color)) {
                    value = Min(Utils.copy(board), i, j, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    if (value > actionScore) {
                        myAction = new OthelloAction(i, j);
                        actionScore = value;
                        }
                }
            }
        }

        OthelloGame.setAction(board, myAction.i, myAction.j, color);

        return myAction;
    }
}
