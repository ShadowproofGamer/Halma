import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Board {
    private final List<int[]> moves = new ArrayList<>(8);

    public void initMoves() {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                moves.add(new int[]{i, j});
            }
        }
    }

    public Map<Tile, Player> board = new HashMap<>();
    public List<Tile> player1Base = new ArrayList<>();
    public List<Tile> player2Base = new ArrayList<>();

    public Board() {
        initMoves();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                board.put(Tile.of(j, i), Player.NONE);
            }
        }
        initBoard();
    }

    public Board(String state) {
        initMoves();
        int iter = 0;
        char[] newState = state.toCharArray();
        for (char c : newState) {
//            System.out.println(c +" "+ iter % 16 +" "+ iter / 16);
            switch (c) {
                case '_':
                    board.put(Tile.of(iter % 16, iter / 16), Player.NONE);
                    iter++;
                    break;
                case '1':
                    board.put(Tile.of(iter % 16, iter / 16), Player.PLAYER1);
                    iter++;
                    break;
                case '2':
                    board.put(Tile.of(iter % 16, iter / 16), Player.PLAYER2);
                    iter++;
                    break;
            }


        }

    }

    private void initBoard() {
        // Define constants
        final int BOARD_SIZE = 16;
        final int PLAYER_PIECES = 5;

        // Place player pieces
        for (int i = 0; i < PLAYER_PIECES; i++) {
            board.put(Tile.of(i, 0), Player.PLAYER2);  // Change to PLAYER2
            board.put(Tile.of(BOARD_SIZE - 1 - i, BOARD_SIZE - 1), Player.PLAYER1);  // Change to PLAYER1
        }
        for (int i = 0; i < PLAYER_PIECES; i++) {
            board.put(Tile.of(i, 1), Player.PLAYER2);  // Change to PLAYER2
            board.put(Tile.of(BOARD_SIZE - 1 - i, BOARD_SIZE - 2), Player.PLAYER1);  // Change to PLAYER1
        }
        for (int i = 0; i < PLAYER_PIECES - 1; i++) {
            board.put(Tile.of(i, 2), Player.PLAYER2);  // Change to PLAYER2
            board.put(Tile.of(BOARD_SIZE - 1 - i, BOARD_SIZE - 3), Player.PLAYER1);  // Change to PLAYER1
        }
        for (int i = 0; i < PLAYER_PIECES - 2; i++) {
            board.put(Tile.of(i, 3), Player.PLAYER2);  // Change to PLAYER2
            board.put(Tile.of(BOARD_SIZE - 1 - i, BOARD_SIZE - 4), Player.PLAYER1);  // Change to PLAYER1
        }
        for (int i = 0; i < PLAYER_PIECES - 3; i++) {
            board.put(Tile.of(i, 4), Player.PLAYER2);  // Change to PLAYER2
            board.put(Tile.of(BOARD_SIZE - 1 - i, BOARD_SIZE - 5), Player.PLAYER1);  // Change to PLAYER1
        }
        player1Base.addAll(board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER1).toList());
        player2Base.addAll(board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER2).toList());
    }


    public void printBoard() {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(board.get(Tile.of(j, i)).getValue() + " ");
            }
            System.out.println();
        }
    }

    public Map<Tile, Player> getState() {
        return board;
    }

    public void movePiece(Move move) {
        Tile startTile = move.getTileFrom();
        Player player = board.get(startTile);
        board.put(startTile, Player.NONE);
        board.put(move.getTileTo(), player);
    }

    public void reversePiece(Move move) {
        Tile endTile = move.getTileTo();
        Player player = board.get(endTile);
        board.put(endTile, Player.NONE);
        board.put(move.getTileFrom(), player);
    }

    public ArrayList<Move> possibleMoves(Player player) {
        ArrayList<Move> resultMoves = new ArrayList<>();
        resultMoves.addAll(jumpMoves(player));
        resultMoves.addAll(simpleMoves(player));
        return resultMoves;
    }

    public Set<Move> simpleMoves(Player player) {
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        Set<Move> resultMoves = new HashSet<>();
        //System.out.println(playerTiles);
        for (Tile tile : playerTiles) {
            int loc_x = tile.getX();
            int loc_y = tile.getY();
            for (int[] vec : moves) {
                if (isValidMove(Move.of(tile, Tile.of(loc_x + vec[0], loc_y + vec[1])))) {
                    resultMoves.add(Move.of(tile, Tile.of(loc_x + vec[0], loc_y + vec[1])));
                }
            }
        }
        return resultMoves;
    }


    public Set<Move> singleJumpsForMove(Move move) {
        Set<Move> resultMoves = new HashSet<>();
        Tile tile = move.getTileTo();
        int loc_x = tile.getX();
        int loc_y = tile.getY();
        for (int[] vec : moves) {
            //debug
//            System.out.println((loc_x + 2 * vec[0]) + "," + (loc_y + 2 * vec[1])
//                               + " : "
//                               + isValidMove(Move.of(tile, Tile.of((loc_x + 2 * vec[0]), (loc_y + 2 * vec[1]))))
//                               + " : "
//                               + (board.get(Tile.of(loc_x + vec[0], loc_y + vec[1])) != Player.NONE)
//                               + " : " + (Tile.of((loc_x + 2 * vec[0]), (loc_y + 2 * vec[1])).equals(move.getTileFrom())));

            if (isValidMove(Move.of(move.getTileFrom(), Tile.of((loc_x + 2 * vec[0]), (loc_y + 2 * vec[1])))) && board.get(Tile.of((loc_x + vec[0]), (loc_y + vec[1]))) != Player.NONE && !Tile.of((loc_x + 2 * vec[0]), (loc_y + 2 * vec[1])).equals(move.getTileFrom())) {
                resultMoves.add(Move.of(move.getTileFrom(), Tile.of((loc_x + 2 * vec[0]), (loc_y + 2 * vec[1]))));
            }
        }
//        System.out.println(tile+": "+resultMoves);
        return resultMoves;
    }

    public Set<Move> jumpMoves(Player player) {
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        Set<Move> resultMoves = new HashSet<>();
        Queue<Move> frontier = new ArrayDeque<>();
        //System.out.println(playerTiles);
        for (Tile tile : playerTiles) {
            Set<Move> temp = singleJumpsForMove(Move.of(tile, tile));
            resultMoves.addAll(temp);
            frontier.addAll(temp);
//            if (tile.equals(Tile.of(13, 15))) {
//                System.out.println(temp);
//            }
            while (!frontier.isEmpty()) {
                Move current = frontier.poll();
//                if (tile.equals(Tile.of(13, 15))) {
//                    System.out.println(current);
//                }
                Set<Move> locTemp = singleJumpsForMove(current);
//                if (tile.equals(Tile.of(13, 15))) {
//                    System.out.println(locTemp);
//                }
                for (Move move : locTemp) {
                    if (!resultMoves.contains(move)) frontier.add(move);
                }
                resultMoves.addAll(locTemp);


            }

        }
        return resultMoves;
    }


    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 16 && y >= 0 && y < 16;
    }

    private boolean isValidMove(Move move) {
        Tile startTile = move.getTileFrom();
        Tile endTile = move.getTileTo();
        Player startPlayer = board.get(startTile);
        Player endPlayer = board.get(endTile);
//        System.out.println(startPlayer + " " + endPlayer);
        if (isValidPosition(endTile.getX(), endTile.getY()) && endPlayer.equals(Player.NONE)) {
//            System.out.println(move + " valid end position");
            //Tutaj zwracamy implikację "czy gracz próbuje opuścić bazę przeciwnika?" (jest w bazie => chce wyjść)
            if (startPlayer.equals(Player.PLAYER1)) {
                return !player2Base.contains(startTile) || player2Base.contains(endTile);
            } else if (startPlayer.equals(Player.PLAYER2)) {
                return !player1Base.contains(startTile) || player1Base.contains(endTile);
            }
        }
        return false;
    }


    //heuristics
    public int heuristic1(Player player) {
        AtomicInteger result = new AtomicInteger();
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)) {
            playerTiles.forEach(tile -> result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY()));
        } else {
            playerTiles.forEach(tile -> result.getAndAdd(tile.getX() + tile.getY()));
        }
        return result.get();
    }


    public int heuristic2(Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)) {
            playerTiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-5);
                }
                result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY());
//                        System.out.println(result.get());
            });
        } else if (player.equals(Player.PLAYER2)) {
            playerTiles.forEach(tile -> {
                        if (player2Base.contains(tile)) {
                            result.getAndAdd(-5);
                        }
                        result.getAndAdd(tile.getX() + tile.getY());
//                        System.out.println(player+"   "+tile+"   "+result.get());
                    }

            );
        }
//        printBoard();
//        System.out.println(result.get()+"\n");
        return result.get();
    }

    public int heuristic3(Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        List<Tile> enemyTiles = board.keySet().stream().filter(c -> board.get(c) == player).toList();
        int MAXLEN = 15;
        if (player.equals(Player.PLAYER1)) {
            playerTiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-5);
                } else if (player2Base.contains(tile)) {
                    result.getAndAdd(1);
                }
                result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY());
            });
            enemyTiles.forEach(tile -> {
                if (player2Base.contains(tile)) {
                    result.getAndAdd(3);
                }
                if (player1Base.contains(tile)) {
                    result.getAndAdd(-2);
                }
                result.getAndAdd(-tile.getX() - tile.getY());
            });
        } else if (player.equals(Player.PLAYER2)) {
            playerTiles.forEach(tile -> {
                        if (player2Base.contains(tile)) {
                            result.getAndAdd(-5);
                        } else if (player1Base.contains(tile)) {
                            result.getAndAdd(1);
                        }
                        result.getAndAdd(tile.getX() + tile.getY());
                    }

            );
            enemyTiles.forEach(tile -> {
                if (player1Base.contains(tile)) {
                    result.getAndAdd(3);
                }
                if (player2Base.contains(tile)) {
                    result.getAndAdd(-2);
                }
                result.getAndAdd(tile.getX() - MAXLEN + tile.getY() - MAXLEN);
            });
        }
//        printBoard();
//        System.out.println(result.get()+"\n");
        return result.get();
    }


    public int heuristic4(Board board, Player player) {
        AtomicInteger result = new AtomicInteger(0);
        List<Tile> player1Tiles = board.board.keySet().stream().filter(c -> board.board.get(c).equals(Player.PLAYER1)).toList();
        List<Tile> player2Tiles = board.board.keySet().stream().filter(c -> board.board.get(c).equals(Player.PLAYER2)).toList();
        int MAXLEN = 15;
        player1Tiles.forEach(tile -> {
            if (player1Base.contains(tile)) {
                result.getAndAdd(-5);
            } else if (player2Base.contains(tile)) {
                result.getAndAdd(3);
            }
            result.getAndAdd(MAXLEN - tile.getX() + MAXLEN - tile.getY());
        });
        player2Tiles.forEach(tile -> {
            if (player2Base.contains(tile)) {
                result.getAndAdd(5);
            }
            if (player1Base.contains(tile)) {
                result.getAndAdd(-3);
            }
            result.getAndAdd(-tile.getX() - tile.getY());
        });
        return result.get();
    }


    public MoveAndScore minimax(Board board, Player player, int depth, Player originalPlayer) {
        // Check if we reached the terminal state (game over or depth limit reached)
        Player otherPlayer = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
        if (isGameOver(board)) {
            boolean thisPlayerWon = player.equals(Player.PLAYER1) ? player2Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList()) : player1Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList());
            if (thisPlayerWon) return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MIN_VALUE);
            else return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MAX_VALUE);
        } else if (depth == 0) {
            return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), heuristic(board, otherPlayer));
        }

        List<Move> possibleMoves = board.possibleMoves(player);

        // This variable will store the best score found so far
        int bestScore;
        Move bestMove = null;

        if (player.equals(originalPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.movePiece(move);
                MoveAndScore moveAndScore = minimax(board, otherPlayer, depth - 1, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
//                Board newBoard = copyBoard(board);
//                newBoard.movePiece(move);
                board.movePiece(move);
                MoveAndScore moveAndScore = minimax(board, otherPlayer, depth - 1, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;

                }
            }
        }
        return new MoveAndScore(bestMove, bestScore);
    }

    public MoveAndScore minimax2(Board board, Player player, int depth, Player originalPlayer) {
        // Check if we reached the terminal state (game over or depth limit reached)
        Player otherPlayer = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
        if (isGameOver(board)) {
            boolean thisPlayerWon = player.equals(Player.PLAYER1) ? player2Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList()) : player1Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList());
            if (thisPlayerWon) return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MIN_VALUE);
            else return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MAX_VALUE);
        } else if (depth == 0) {
            return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), heuristic(board, otherPlayer));
        }

        List<Move> possibleMoves = board.possibleMoves(player);

        // This variable will store the best score found so far
        int bestScore;
        Move bestMove = null;

        if (player.equals(originalPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                Board newBoard = copyBoard(board); // Create a copy of the board to avoid modifying the original
                newBoard.movePiece(move);
                MoveAndScore moveAndScore = minimax2(newBoard, Player.PLAYER2, depth - 1, originalPlayer);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }

            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                Board newBoard = copyBoard(board);
                newBoard.movePiece(move);
                MoveAndScore moveAndScore = minimax2(newBoard, Player.PLAYER1, depth - 1, originalPlayer);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }
            }
        }
        return new MoveAndScore(bestMove, bestScore);
    }


    public MoveAndScore alfabeta(Board board, Player player, int depth, int alpha, int beta, Player originalPlayer) {
        // Check if we reached the terminal state (game over or depth limit reached)
        Player otherPlayer = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
        if (isGameOver(board)) {
            boolean thisPlayerWon = player.equals(Player.PLAYER1) ? player2Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList()) : player1Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList());
            if (thisPlayerWon) return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MIN_VALUE);
            else return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MAX_VALUE);
        } else if (depth == 0) {
            return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), board.heuristic3(originalPlayer));
        }

        List<Move> possibleMoves = board.possibleMoves(player);

        // This variable will store the best score found so far
        int bestScore;
        Move bestMove = null;

        if (player.equals(originalPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.movePiece(move);
                MoveAndScore moveAndScore = alfabeta(board, otherPlayer, depth - 1, alpha, beta, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " :: "+ originalPlayer + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }
                alpha = Math.max(alpha, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if alpha exceeds beta (maximizing player can't do worse)
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                board.movePiece(move);
                MoveAndScore moveAndScore = alfabeta(board, otherPlayer, depth - 1, alpha, beta, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;

                }
                beta = Math.min(beta, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if beta falls below alpha (minimizing player can't do better)
                }
            }
        }
        return new MoveAndScore(bestMove, bestScore);
    }

    public MoveAndScore alfabeta2(Board board, Player player, int depth, int alpha, int beta, Player originalPlayer) {
        // Check if we reached the terminal state (game over or depth limit reached)
        Player otherPlayer = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
        if (isGameOver(board)) {
            boolean thisPlayerWon = player.equals(Player.PLAYER1) ? player2Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList()) : player1Base.equals(board.board.keySet().stream().filter(c -> board.board.get(c) == player).toList());
            if (thisPlayerWon) return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MIN_VALUE);
            else return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MAX_VALUE);
        } else if (depth == 0) {
            return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), heuristic(board, otherPlayer));
        }

        List<Move> possibleMoves = board.possibleMoves(player);

        // This variable will store the best score found so far
        int bestScore;
        Move bestMove = null;

        if (player.equals(originalPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                board.movePiece(move);
                MoveAndScore moveAndScore = alfabeta2(board, otherPlayer, depth - 1, alpha, beta, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " :: "+ originalPlayer + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }
                alpha = Math.max(alpha, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if alpha exceeds beta (maximizing player can't do worse)
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                board.movePiece(move);
                MoveAndScore moveAndScore = alfabeta2(board, otherPlayer, depth - 1, alpha, beta, originalPlayer);
                board.reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;

                }
                beta = Math.min(beta, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if beta falls below alpha (minimizing player can't do better)
                }
            }
        }
        return new MoveAndScore(bestMove, bestScore);
    }


    public MoveAndScore alfabetaMod(Player player, int depth, int alpha, int beta, Player originalPlayer) {
        // Check if we reached the terminal state (game over or depth limit reached)
        if (isGameOverMod()) {
            boolean thisPlayerWon = player.equals(whoWonMod());
            if (thisPlayerWon) return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MIN_VALUE);
            else return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), Integer.MAX_VALUE);
        } else if (depth == 0) {
            return new MoveAndScore(Move.of(Tile.of(0, 0), Tile.of(0, 0)), heuristic4(this, originalPlayer) * (originalPlayer.equals(Player.PLAYER1) ? 1 : -1));
        }
        Player otherPlayer = player.equals(Player.PLAYER1) ? Player.PLAYER2 : Player.PLAYER1;
        List<Move> possibleMoves = possibleMoves(player);

        // This variable will store the best score found so far
        int bestScore;
        Move bestMove = null;
        int nodesVisited = 0;

        if (player.equals(originalPlayer)) {
            bestScore = Integer.MIN_VALUE;
            for (Move move : possibleMoves) {
                movePiece(move);
                MoveAndScore moveAndScore = alfabetaMod(otherPlayer, depth - 1, alpha, beta, originalPlayer);
                nodesVisited+= moveAndScore.getNodesVisited();
                reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " :: "+ originalPlayer + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
//                    System.out.println(player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                }
                alpha = Math.max(alpha, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if alpha exceeds beta (maximizing player can't do worse)
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (Move move : possibleMoves) {
                movePiece(move);
                MoveAndScore moveAndScore = alfabetaMod(otherPlayer, depth - 1, alpha, beta, originalPlayer);
                nodesVisited+= moveAndScore.getNodesVisited();
                reversePiece(move);
                int score = moveAndScore.getScore();
//                System.out.println("entered " + move + " :: " + score + " :: " + player + " ::  " + bestMove + " :: " + bestScore + " :: " + depth);
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;

                }
                beta = Math.min(beta, score);
//                System.out.println("alpha:"+alpha+" ::: beta:"+beta);
                if (beta <= alpha) {
//                    System.out.println("prunned:"+move+" a:"+alpha+" b:"+beta+" depth:"+depth+" ");
                    break; // Prune the branch if beta falls below alpha (minimizing player can't do better)
                }
            }
        }
        return new MoveAndScore(bestMove, bestScore, nodesVisited);
    }


    // Helper methods to check game over, copy the board, and choose the heuristic
    public boolean isGameOver(Board board) {
        // TODO Implement your logic to check if either player can't move or some other win condition is met
        List<Tile> player1Tiles = board.board.keySet().stream().filter(c -> board.board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.board.keySet().stream().filter(c -> board.board.get(c) == Player.PLAYER2).toList();
        return player1Tiles.equals(player2Base) || player1Base.equals(player2Tiles);
    }

    public Player whoWon(Board board) {
        List<Tile> player1Tiles = board.board.keySet().stream().filter(c -> board.board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.board.keySet().stream().filter(c -> board.board.get(c) == Player.PLAYER2).toList();
        if (player1Tiles.equals(player2Base)) return Player.PLAYER1;
        else if (player1Base.equals(player2Tiles)) return Player.PLAYER2;
        else return Player.NONE;
    }

    public boolean isGameOver() {
        // TODO Implement your logic to check if either player can't move or some other win condition is met
        List<Tile> player1Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER2).toList();
        return player1Tiles.equals(player2Base) || player1Base.equals(player2Tiles);
    }

    public boolean isGameOverMod() {
        // TODO Implement your logic to check if either player can't move or some other win condition is met
        List<Tile> player1Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER2).toList();
        if (player1Tiles.equals(player2Base) || player1Base.equals(player2Tiles)) {
            return true;
        } else if (player1Base.stream().anyMatch(tile -> board.get(tile).equals(Player.PLAYER2))
                   && player1Base.stream().noneMatch(tile -> board.get(tile).equals(Player.NONE))) {
            return true;
        } else return player2Base.stream().anyMatch(tile -> board.get(tile).equals(Player.PLAYER1))
                      && player2Base.stream().noneMatch(tile -> board.get(tile).equals(Player.NONE));
    }

    public Player whoWon() {
        List<Tile> player1Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER2).toList();
        if (player1Tiles.equals(player2Base)) return Player.PLAYER1;
        else if (player1Base.equals(player2Tiles)) return Player.PLAYER2;
        else return Player.NONE;
    }

    public Player whoWonMod() {
        List<Tile> player1Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER1).toList();
        List<Tile> player2Tiles = board.keySet().stream().filter(c -> board.get(c) == Player.PLAYER2).toList();
        if (player1Tiles.equals(player2Base)) return Player.PLAYER1;
        else if (player1Base.equals(player2Tiles)) return Player.PLAYER2;
        else if (player1Base.stream().anyMatch(tile -> board.get(tile).equals(Player.PLAYER2))
                 && player1Base.stream().noneMatch(tile -> board.get(tile).equals(Player.NONE))) {
            return Player.PLAYER2;
        } else if (player2Base.stream().anyMatch(tile -> board.get(tile).equals(Player.PLAYER1))
                   && player2Base.stream().noneMatch(tile -> board.get(tile).equals(Player.NONE))) {
            return Player.PLAYER1;
        }else return Player.NONE;
    }

    //depreciated - used in minimax2
    private Board copyBoard(Board board) {
        Board newBoard = new Board();
        // Copy the board state (tiles and player pieces) to the new board
        for (Tile tile : board.board.keySet()) {
            newBoard.board.put(tile, board.board.get(tile));
        }
        return newBoard;
    }

    private int heuristic(Board board, Player player) {
        // Choose the appropriate heuristic function (heuristic1, heuristic2, or heuristic3)
        return board.heuristic2(player); // Replace with your preferred heuristic
    }


}
