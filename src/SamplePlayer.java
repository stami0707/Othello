import java.util.Random;
import game.oth.OthelloAction;
import game.oth.OthelloPlayer;

public class SamplePlayer extends OthelloPlayer {
    public SamplePlayer(int color, int[][] board, Random random) {
        super(color, board, random);
    }
    @Override
    public OthelloAction getAction(OthelloAction prevAction, long[] remainingTimes) {
        int i = random.nextInt(board.length);
        int j = random.nextInt(board[i].length);
        return new OthelloAction(i, j);
    }
}