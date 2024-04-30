import org.junit.Test;


import java.util.*;

public class UnitTests {

    @Test
    public void test1() {
        Board board = new Board();
        board.printBoard();
        List<Move> allMoves = board.possibleMoves(Player.PLAYER1);
        System.out.println(allMoves);
        System.out.println(allMoves.size());
    }

    @Test
    public void shouldRepresentEmptyBoard() {
        Board board = new Board();
        board.board.forEach((tile, player) -> board.board.put(tile, Player.NONE));
        Map<Tile, Player> emptyBoard = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                emptyBoard.put(Tile.of(i, j), Player.NONE);
            }
        }
        assert board.board.equals(emptyBoard);
    }

    @Test
    public void singleJumpTest() {
        Board board = new Board();
        board.board.forEach((tile, player) -> board.board.put(tile, Player.NONE));
    }

    @Test
    public void singleMoveChange() {
        Board board = new Board();
        ArrayList<Move> moves = board.possibleMoves(Player.PLAYER1);
        board.movePiece(moves.getFirst());
        board.printBoard();
    }



    @Test
    public void checkHeuristic1() {
        Board board = new Board();
        int h2 = board.heuristic1(Player.PLAYER1);
        assert h2 == 40;
    }

    @Test
    public void checkHeuristic1_2() {
        Board board = new Board();
        ArrayList<Move> moves = board.possibleMoves(Player.PLAYER1);
        board.movePiece(moves.getFirst());
        int h2 = board.heuristic1(Player.PLAYER1);
        System.out.println(h2);
    }

    @Test
    public void checkHeuristic2() {
        Board board = new Board();
        ArrayList<Move> moves = board.possibleMoves(Player.PLAYER1);
        board.movePiece(moves.getFirst());
        int h3 = board.heuristic2(Player.PLAYER1);
        System.out.println(h3);
    }

    @Test
    public void checkMinimax() {
        Board board = new Board();
        MoveAndScore result = board.minimax(new Board(), Player.PLAYER1, 1, Player.PLAYER1);
        System.out.println(result);
        board.movePiece(result.getMove());
        board.printBoard();
    }

    @Test
    public void checkMinimax_2() {
        Board board = new Board();
//        System.out.println(board.heuristic3(Player.PLAYER1));
//        System.out.println(board.heuristic3(Player.PLAYER2));

        MoveAndScore result = board.minimax(board, Player.PLAYER1, 1, Player.PLAYER1);
        System.out.println(result);
        board.movePiece(result.getMove());
        board.printBoard();


        result = board.minimax(board, Player.PLAYER2, 1, Player.PLAYER2);
        System.out.println(result);
        board.movePiece(result.getMove());
        board.printBoard();
//        System.out.println(board.heuristic3(Player.PLAYER1));
//        System.out.println(board.heuristic3(Player.PLAYER2));
    }

    @Test
    public void heuristicTest() {
        Board board = new Board();
        System.out.println(board.heuristic2(Player.PLAYER1));
        System.out.println(board.heuristic2(Player.PLAYER2));
        board.movePiece(Move.of(Tile.of(12, 15), Tile.of(10, 13)));
        board.movePiece(Move.of(Tile.of(2, 1), Tile.of(4, 3)));
        System.out.println(board.heuristic2(Player.PLAYER1));
        System.out.println(board.heuristic2(Player.PLAYER2));
        assert board.heuristic2(Player.PLAYER1) == board.heuristic2(Player.PLAYER2);
    }

    @Test
    public void checkSimpleGame() {
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.minimax(board, Player.PLAYER1, 3, Player.PLAYER1);
        System.out.println("Round 1:  "+Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (result.getScore() < Integer.MAX_VALUE && iter < 20) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.minimax(board, currentPlayer, 3, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }

    }

    @Test
    public void checkSimpleGame2() {
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.minimax2(board, Player.PLAYER1, 3, Player.PLAYER1);
        System.out.println(Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (result.getScore() < Integer.MAX_VALUE && iter < 20) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.minimax2(board, currentPlayer, 3, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlayerFromChar() {
        assert Player.valueOf(String.valueOf('_')) == Player.PLAYER1;
    }

    @Test
    public void boardFrom() {
        Board board = new Board(
                """
                        2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                        _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");
        board.printBoard();
    }

    @Test
    public void checkBestMove() {
        Board board = new Board(
                """
                        2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                        _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");
        Board board1 = new Board(
                """
                        2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                        _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                        _ _ _ _ _ _ _ _ _ _ 1 1 _ 1 _ 1\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 1\s""");
        Board board2 = new Board(
                """
                        2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                        _ _ _ _ _ _ _ _ _ 1 1 _ 1 _ _ 1\s
                        _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                        _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                        _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                        _ _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 1\s""");
        System.out.println(board.heuristic2(Player.PLAYER1) + " : " + board1.heuristic2(Player.PLAYER1) + " : " + board2.heuristic2(Player.PLAYER1));
    }

    @Test
    public void checkMinimax_3() {
        Board board = new Board("""
                2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");
        System.out.println(board.heuristic2(Player.PLAYER1));
        System.out.println(board.heuristic2(Player.PLAYER2));

        MoveAndScore result = board.minimax(board, Player.PLAYER1, 1, Player.PLAYER1);
        System.out.println(result);
        board.movePiece(result.getMove());
        board.printBoard();


//        result = board.minimax(new Board(), Player.PLAYER2, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER2);
//        System.out.println(result);
//        board.movePiece(result.getMove());
//        board.printBoard();
//        System.out.println(board.heuristic3(Player.PLAYER1));
//        System.out.println(board.heuristic3(Player.PLAYER2));
    }

    @Test
    public void testMoves() {
        Board board = new Board("""
                2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");
        assert board.possibleMoves(Player.PLAYER1).contains(Move.of(Tile.of(13, 15), Tile.of(9, 11)));
    }

    @Test
    public void testMoves2() {
        Board board = new Board("""
                2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");

        List<Move> moves = board.possibleMoves(Player.PLAYER1);
//        board.possibleMoves(Player.PLAYER1);
        System.out.println(moves + "\n" + moves.size());
//        System.out.println(board.board.get(Tile.of(13, 15)));
//        Move.of(Tile.of(13, 15), Tile.of(9, 11)
    }
    @Test
    public void testMoves3() {
        Board board = new Board("""
                2 2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 2 _ _ _ _ _ _ _ _ _ _ _ _ _\s
                2 2 _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ 2 _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1\s
                _ _ _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _ 1\s
                _ _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ 1 1 1\s""");
//        board.possibleMoves(Player.PLAYER1);
//        System.out.println(board.possibleMoves(Player.PLAYER1));
//        System.out.println(board.board.get(Tile.of(13, 15)));
//        Move.of(Tile.of(13, 15), Tile.of(9, 11)
        System.out.println(Tile.of(11, 13));
        Set<Move> returnedMoves = board.singleJumpsForMove(Move.of(Tile.of(13,15), Tile.of(11, 13)));
        Set<Move> testMoves = new HashSet<>();
        testMoves.add(Move.of(Tile.of(13, 15), Tile.of(9, 13)));
        testMoves.add(Move.of(Tile.of(13, 15), Tile.of(11, 11)));
        System.out.println(testMoves);
        assert returnedMoves.equals(testMoves);
    }

    @Test
    public void testMoves4(){
        Board board = new Board("""
                _ 2 _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                2 _ _ 2 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ 2 _ _ _ 2 _ _ _ _ _ _ _ _ _\s
                _ _ 2 _ 2 2 _ _ _ _ _ _ _ _ _ _\s
                2 _ _ _ 2 2 2 2 _ _ _ _ _ _ _ _\s
                _ _ 1 _ _ 2 _ _ _ _ _ _ _ _ _ _\s
                _ _ _ 2 _ 2 _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 _ 1 _ _ 1 _ 1 _\s
                _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _ 1\s
                _ _ _ _ _ _ _ _ 2 _ _ 1 1 _ 1 _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ 1 _ _ 1 _\s
                """);
        board.printBoard();
        List<Move> moves = board.possibleMoves(Player.PLAYER2);
        System.out.println(moves +"\n"+moves.size());

    }

    @Test
    public void checkSimpleGame3() {
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.alfabeta(board, Player.PLAYER1, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1);
        System.out.println(Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (result.getScore() < Integer.MAX_VALUE && iter < 20) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.alfabeta(board, currentPlayer, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }

    }

    @Test
    public void fullGame1(){
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.alfabeta(board, Player.PLAYER1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1);
        System.out.println(Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (!board.isGameOver(board)) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.alfabeta(board, currentPlayer, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }
        System.out.println(board.whoWon(board));
        //depth 4 - 26min 28sec
        //depth 3 - 2min 27sec
        //depth 2 - 5sec 406ms
        //depth 1 - 979ms
    }

    @Test
    public void fullGame2(){
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = board.alfabeta2(board, Player.PLAYER1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1);
        System.out.println(Player.PLAYER1 + " " + result);
        board.movePiece(result.getMove());
        board.printBoard();
        iter++;

        while (!board.isGameOver(board)) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.alfabeta2(board, currentPlayer, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }
        //depth 4 - 26min 28sec
        //depth 3 - 2min 27sec
        //depth 2 - 5sec 406ms
        //depth 1 - 979ms
    }

    @Test
    public void fullGame3(){
        Board board = new Board();
        int iter = 0;

        MoveAndScore result = null;
        while (!board.isGameOverMod()) {
            long start = System.nanoTime();
            Player currentPlayer = iter % 2 == 0 ? Player.PLAYER1 : Player.PLAYER2;
            result = board.alfabetaMod(currentPlayer, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, currentPlayer);
            System.out.println("Round "+(iter+1)+":  "+ currentPlayer + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }
        System.out.println(board.whoWonMod());
    }

    @Test
    public void fullGame4(){
        Board2 board = new Board2();
        int iter = 0;

        MoveAndScore result = null;
        while (!board.isGameOverMod()) {
            //player1
            long start = System.nanoTime();
            result = board.alfabeta(Player.PLAYER1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
            System.out.println("Round "+(iter+1)+":  "+ Player.PLAYER1 + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            long finish = System.nanoTime();
            long timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
            //check if player1 won
            if (board.isGameOverMod()) break;
            //player2
            start = System.nanoTime();
            result = board.alfabeta(Player.PLAYER2, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER2, new Heuristic1());
            System.out.println("Round "+(iter+1)+":  "+ Player.PLAYER2 + " " + result);
            board.movePiece(result.getMove());
            board.printBoard();
            iter++;
            finish = System.nanoTime();
            timeElapsed = finish - start;
            System.out.println(timeElapsed/1000000+"ms\n");
        }
        System.out.println(board.whoWonMod());
    }

    @Test
    public void fullGameHeuristics12() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new Heuristic1(), new HeuristicJumps(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void mirrorPieceTest(){
        Board2 board2 = new Board2();
        Move testMove = Move.of(Tile.of(12, 15), Tile.of(10, 13));
        board2.movePiece(testMove);
        board2.printBoard();
        board2.mirrorPiece(testMove);
        board2.printBoard();
    }


    @Test
    public void boardTest5(){
        Board2 board2 = new Board2("""
                1 1 1 1 1 _ _ _ _ _ _ _ _ _ _ _\s
                1 1 1 1 1 _ _ _ _ _ _ _ _ _ _ _\s
                1 1 1 1 _ _ _ _ 2 _ _ _ _ _ _ _\s
                1 1 1 _ _ _ 2 2 _ _ _ _ _ _ _ _\s
                1 _ _ _ 2 _ _ _ 2 2 _ _ _ _ _ _\s
                _ _ _ _ 2 _ 2 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ 2 _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ _ _ _ _ _\s
                _ _ _ _ 2 2 _ 2 _ _ _ _ _ _ _ _\s
                _ 2 2 _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        System.out.println(board2.possibleMoves(Player.PLAYER1));
        Tile tileFrom = Tile.of(1, 2);
        Tile tileTo = Tile.of(1, 4);
        System.out.println(board2.isValidMove(Move.of(tileFrom, tileTo)));
    }

    @Test
    public void fullGameHeuristics1R() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new Heuristic1(), new HeuristicRandom(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void fullGameHeuristics21() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new HeuristicJumps(), new Heuristic1(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void fgStartRand() throws InterruptedException {
        Board2 board2 = new Board2();
        for (int i = 0; i < 7; i++) {
            Move temp = board2.randomMove(Player.PLAYER1);
            board2.movePiece(temp);
            board2.mirrorPiece(temp);
        }
        board2.printBoard();
        GameLoop gameLoop = new GameLoop(2, new HeuristicJumps(), new Heuristic1(), true, new Vector<>(), board2);
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void fullGameHeuristics1S() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new Heuristic1(), new HeuristicSimple(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void fullGameHeuristics1WS() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new Heuristic1(), new HeuristicSimpleWeight(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void fullGameHeuristicsSWS() throws InterruptedException {
        GameLoop gameLoop = new GameLoop(2, new HeuristicSimple(), new HeuristicSimpleWeight(), true, new Vector<>());
        gameLoop.start();
        gameLoop.join();
    }

    @Test
    public void checkMinimax4() {
        Board2 board2 = new Board2("""
                _ 2 _ _ _ 2 _ _ _ 2 _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                2 _ _ _ _ 2 _ 1 _ 2 2 _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ 2 2 _ _ _\s
                _ _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 2 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ 1 1 _ 1 _ 2 _ 1 _ _\s
                _ _ _ _ _ _ _ _ 2 1 _ _ _ 1 2 _\s
                _ _ _ _ _ 1 1 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ 1 _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        long start = System.nanoTime();
        MoveAndScore result = board2.minimax(board2, Player.PLAYER1, 1, Player.PLAYER1);
        long end = System.nanoTime();
        System.out.println("time elapsed: "+((end-start)/1000000)+"ms    result: "+result);
        Board2 board3 = new Board2("""
                _ 2 _ _ _ 2 _ _ _ 2 _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                2 _ _ _ _ 2 _ 1 _ 2 2 _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ 2 2 _ _ _\s
                _ _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 2 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ 1 1 _ 1 _ 2 _ 1 _ _\s
                _ _ _ _ _ _ _ _ 2 1 _ _ _ 1 2 _\s
                _ _ _ _ _ 1 1 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ 1 _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        long start2 = System.nanoTime();
        MoveAndScore result2 = board3.minimax(board3, Player.PLAYER1, 2, Player.PLAYER1);
        long end2 = System.nanoTime();
        System.out.println("time elapsed: "+((end2-start2)/1000000)+"ms    result: "+result2);

        Board2 board4 = new Board2("""
                _ 2 _ _ _ 2 _ _ _ 2 _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                2 _ _ _ _ 2 _ 1 _ 2 2 _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ 2 2 _ _ _\s
                _ _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 2 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ 1 1 _ 1 _ 2 _ 1 _ _\s
                _ _ _ _ _ _ _ _ 2 1 _ _ _ 1 2 _\s
                _ _ _ _ _ 1 1 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ 1 _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        long start4 = System.nanoTime();
        MoveAndScore result4 = board4.minimax(board4, Player.PLAYER1, 3, Player.PLAYER1);
        long end4 = System.nanoTime();
        System.out.println("time elapsed: "+((end4-start4)/1000000)+"ms    result: "+result4);

        Board2 board5 = new Board2("""
                _ 2 _ _ _ 2 _ _ _ 2 _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                2 _ _ _ _ 2 _ 1 _ 2 2 _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ 2 2 _ _ _\s
                _ _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 2 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ 1 1 _ 1 _ 2 _ 1 _ _\s
                _ _ _ _ _ _ _ _ 2 1 _ _ _ 1 2 _\s
                _ _ _ _ _ 1 1 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ 1 _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        long start5 = System.nanoTime();
        MoveAndScore result5 = board2.minimax(board5, Player.PLAYER1, 4, Player.PLAYER1);
        long end5 = System.nanoTime();
        System.out.println("time elapsed: "+((end5-start5)/1000000)+"ms    result: "+result5);

        System.out.println(result5);
//        board2.printBoard();
    }

    @Test
    public void checkAlfabeta() {
        Board2 board2 = new Board2("""
                _ 2 _ _ _ 2 _ _ _ 2 _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                2 _ _ _ _ 2 _ 1 _ 2 2 _ _ _ _ _\s
                _ _ _ 2 _ _ _ 2 _ _ _ 2 2 _ _ _\s
                _ _ _ _ 2 _ _ _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 2 _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 2 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ _ 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ 1 1 _ 1 _ 2 _ 1 _ _\s
                _ _ _ _ _ _ _ _ 2 1 _ _ _ 1 2 _\s
                _ _ _ _ _ 1 1 _ _ _ _ _ _ _ _ _\s
                _ _ _ _ _ _ _ 1 1 _ _ _ 1 _ _ _\s
                _ _ _ _ _ _ _ _ _ _ 1 _ _ _ _ _\s
                _ _ _ _ _ _ _ 2 _ _ 1 _ _ 1 _ _\s
                _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _\s""");
        long start = System.nanoTime();
        MoveAndScore result = board2.alfabeta(Player.PLAYER1, 1, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
        long end = System.nanoTime();
        System.out.println("time elapsed: "+((end-start)/1000000)+"ms    result: "+result);

        long start2 = System.nanoTime();
        MoveAndScore result2 = board2.alfabeta(Player.PLAYER1, 2, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
        long end2 = System.nanoTime();
        System.out.println("time elapsed: "+((end2-start2)/1000000)+"ms    result: "+result2);

        long start4 = System.nanoTime();
        MoveAndScore result4 = board2.alfabeta(Player.PLAYER1, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
        long end4 = System.nanoTime();
        System.out.println("time elapsed: "+((end4-start4)/1000000)+"ms    result: "+result4);


        long start5 = System.nanoTime();
        MoveAndScore result5 = board2.alfabeta(Player.PLAYER1, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
        long end5 = System.nanoTime();
        System.out.println("time elapsed: "+((end5-start5)/1000000)+"ms    result: "+result5);

        long start6 = System.nanoTime();
        MoveAndScore result6 = board2.alfabeta(Player.PLAYER1, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.PLAYER1, new Heuristic1());
        long end6 = System.nanoTime();
        System.out.println("time elapsed: "+((end6-start6)/1000000)+"ms    result: "+result6);

//        System.out.println(result5);
//        board2.printBoard();
    }

}
