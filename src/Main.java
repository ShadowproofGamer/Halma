import java.util.Vector;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        runConcurrentGames(2, new HeuristicSimple(), new HeuristicSimpleWeight(), 50);


    }

    private static void runConcurrentGames(int depth, Heuristic h1, Heuristic h2, int iterations) throws InterruptedException {
        Vector<GameResults> gameResultsVector = new Vector<>();

        int inlineIterations = iterations / 5;
        for (int i = 0; i < inlineIterations; i++) {
            //starting concurrent games
            GameLoop gameLoop = new GameLoop(depth, h1, h2, false, gameResultsVector, getRandBoard());
            gameLoop.start();
            GameLoop gameLoop2 = new GameLoop(depth, h1, h2, false, gameResultsVector, getRandBoard());
            gameLoop2.start();
            GameLoop gameLoop3 = new GameLoop(depth, h1, h2, false, gameResultsVector, getRandBoard());
            gameLoop3.start();
            GameLoop gameLoop4 = new GameLoop(depth, h1, h2, false, gameResultsVector, getRandBoard());
            gameLoop4.start();
            GameLoop gameLoop5 = new GameLoop(depth, h1, h2, false, gameResultsVector, getRandBoard());
            gameLoop5.start();

            //waiting for games to end before running the next ones
            gameLoop.join();
            gameLoop2.join();
            gameLoop3.join();
            gameLoop4.join();
            gameLoop5.join();

        }
        System.out.println(gameResultsVector);
        printGamesStatistics(gameResultsVector);


    }

    private static void runGames(int depth, Heuristic h1, Heuristic h2, int iterations) throws InterruptedException {
        Vector<GameResults> gameResultsVector = new Vector<>();

        for (int i = 0; i < iterations; i++) {
            //starting concurrent games
            GameLoop gameLoop = new GameLoop(depth, h1, h2, false, gameResultsVector);
            gameLoop.start();
            //waiting for games to end before running the next ones
            gameLoop.join();
        }
        System.out.println(gameResultsVector);
        printGamesStatistics(gameResultsVector);
    }

    private static void printGamesStatistics(Vector<GameResults> gameResultsVector) {
        int player1Wins = 0;
        int player2Wins = 0;
        long timeElapsed = 0;
        int nodesVisited = 0;
        for (GameResults gameResults : gameResultsVector) {
            if (gameResults.getWinner().equals(Player.PLAYER1)) {
                player1Wins++;
            } else {
                player2Wins++;
            }
            timeElapsed+= (gameResults.getTimeElapsed()/1000000);
            nodesVisited+= gameResults.getNodesVisited();

        }
        System.out.println("Player 1 wins: "+player1Wins+ ",   Player 2 wins: "+player2Wins+",   Player 1 winrate:"+((float)player1Wins/((float) (player1Wins+player2Wins)))+ ",   average time elapsed: "+(timeElapsed/gameResultsVector.size())+"ms,   average nodes visited: "+ (nodesVisited/gameResultsVector.size()) );
    }

    private static Board2 getRandBoard(){
        Board2 board2 = new Board2();
        for (int i = 0; i < 7; i++) {
            Move temp = board2.randomMove(Player.PLAYER1);
            board2.movePiece(temp);
            board2.mirrorPiece(temp);
        }
        return board2;
    }
}
