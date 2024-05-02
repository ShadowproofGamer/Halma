import java.util.List;
import java.util.Vector;

public class GameLoop extends Thread{
    private final int depth;
    private final Heuristic heuristic1;
    private final Heuristic heuristic2;
    private final boolean visualMode;
    private Vector<GameResults> allResults;
    private Board2 startBoard;

    public GameLoop(int depth, Heuristic heuristic1, Heuristic heuristic2, boolean visualMode, Vector<GameResults> allResults) {
        this.depth = depth;
        this.heuristic1 = heuristic1;
        this.heuristic2 = heuristic2;
        this.visualMode = visualMode;
        this.allResults = allResults;
        this.startBoard = new Board2();
    }
    public GameLoop(int depth, Heuristic heuristic1, Heuristic heuristic2, boolean visualMode, Vector<GameResults> allResults, Board2 startBoard) {
        this.depth = depth;
        this.heuristic1 = heuristic1;
        this.heuristic2 = heuristic2;
        this.visualMode = visualMode;
        this.allResults = allResults;
        this.startBoard = startBoard;
    }
    public GameLoop(){
        this.depth = 2;
        this.heuristic1 = new Heuristic1();
        this.heuristic2 = new Heuristic1();
        this.visualMode = true;
        this.allResults = new Vector<>();
        this.startBoard = new Board2();
    }


    public void fullGame(){
        Board2 board = startBoard;
        int iter = 0;
        long startGame = System.nanoTime();
        int nodesVisited = 0;

        MoveAndScore result;
        while (!board.isGameOverMod() && iter<500) {
            //player1
            long start = System.nanoTime();
            result = board.alfabeta(Player.PLAYER1, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, heuristic1);
            board.movePiece(result.getMove());
            nodesVisited+=result.getNodesVisited();
            iter++;
            if (visualMode){
                System.out.println("Round "+(iter+1)+":  "+ Player.PLAYER1 + " " + result);
                board.printBoard();

                long finish = System.nanoTime();
                long timeElapsed = finish - start;
                System.out.println(timeElapsed/1000000+"ms\n");
            }

            //check if player1 won
            if (board.isGameOverMod()) break;
            //player2
            start = System.nanoTime();
            result = board.alfabeta(Player.PLAYER2, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER2, heuristic2);
            board.movePiece(result.getMove());
            nodesVisited+=result.getNodesVisited();
            iter++;
            if (visualMode){
                System.out.println("Round "+(iter+1)+":  "+ Player.PLAYER2 + " " + result);
                board.printBoard();

                long finish = System.nanoTime();
                long timeElapsed = finish - start;
                System.out.println(timeElapsed/1000000+"ms\n");
            }
        }
        long endGame = System.nanoTime();
        long gameTimeElapsed = endGame-startGame;
        GameResults gameResults = new GameResults(board.whoWonMod(), gameTimeElapsed, nodesVisited);
        if (visualMode){
            System.out.println(gameResults);
        }
        allResults.add(gameResults);
        System.out.println(this+"Game finished");
    }
    @Override
    public void run() {
        fullGame();
    }
}
