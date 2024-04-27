import java.util.*;


public class Board {
    private final List<int[]> moves = new ArrayList<>(8);
    public void initMoves(){
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i==0 && j==0) continue;
                moves.add(new int[]{i, j});
            }
        }
    }

    public Map<Tile, Player> board = new HashMap<>();
    public Board() {
        initMoves();
        for (int i=0; i<16; i++) {
            for (int j = 0; j < 16; j++) {
                board.put(Tile.of(j,i), Player.NONE);
            }
        }
//        initBoard();
    }
    public void initBoard() {
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
    }


    public void printBoard(){
        for (int i=0; i<16; i++) {
            for (int j = 0; j < 16; j++) {
                System.out.print(board.get(Tile.of(i, j)).getValue()+" ");
            }
            System.out.println();
        }
    }

    public List<Tuple> possibleMoves(Player player) {
        List<Tuple> moves = new ArrayList<>();
        for (Map.Entry<Tile, Player> entry : board.entrySet()) {
            if (entry.getValue() == player) {
                int x = entry.getKey().getX();
                int y = entry.getKey().getY();
                // Add adjacent tiles as possible moves
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int newX = x + dx;
                        int newY = y + dy;
                        if (isValidPosition(newX, newY) && board.get(Tile.of(newX, newY)) == Player.NONE) {
                            moves.add(Tuple.of(newX, newY));
                        }
                    }
                }
            }
        }
        return moves;
    }

    public Set<Move> simpleMoves(Player player){
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c)==player).toList();
        Set<Move> resultMoves = new HashSet<>();
        //System.out.println(playerTiles);
        for(Tile tile: playerTiles){
            int loc_x = tile.getX();
            int loc_y = tile.getY();
            for(int[] vec: moves) {
                if (isValidPosition(loc_x+vec[0], loc_y+vec[1]) && board.get(Tile.of(loc_x+vec[0], loc_y+vec[1]))==Player.NONE){
                    resultMoves.add(Move.of(tile, Tile.of(loc_x+vec[0], loc_y+vec[1])));
                }
            }
        }
        return resultMoves;
    }

    public Set<Move> singleJumpsForTile(Tile tile){
        Set<Move> resultMoves = new HashSet<>();
        int loc_x = tile.getX();
        int loc_y = tile.getY();
        for(int[] vec: moves) {
            if (isValidPosition(loc_x+2*vec[0], loc_y+2*vec[1])
                    && board.get(Tile.of(loc_x+vec[0], loc_y+vec[1]))!=Player.NONE
                && board.get(Tile.of(loc_x+2*vec[0], loc_y+2*vec[1]))==Player.NONE)
            {
                resultMoves.add(Move.of(tile, Tile.of(loc_x+2*vec[0], loc_y+2*vec[1])));
            }
        }
//        System.out.println(tile+": "+resultMoves);
        return resultMoves;
    }
    public Set<Move> singleJumpsForMove(Move move){
        Set<Move> resultMoves = new HashSet<>();
        Tile tile = move.getTileTo();
        int loc_x = tile.getX();
        int loc_y = tile.getY();
        for(int[] vec: moves) {
            if (isValidPosition(loc_x+2*vec[0], loc_y+2*vec[1])
                    && board.get(Tile.of(loc_x+vec[0], loc_y+vec[1]))!=Player.NONE
                    && board.get(Tile.of(loc_x+2*vec[0], loc_y+2*vec[1]))==Player.NONE)
            {
                resultMoves.add(Move.of(move.getTileFrom(), Tile.of(loc_x+2*vec[0], loc_y+2*vec[1])));
            }
        }
//        System.out.println(tile+": "+resultMoves);
        return resultMoves;
    }

    public Set<Move> jumpMoves(Player player){
        List<Tile> playerTiles = board.keySet().stream().filter(c -> board.get(c)==player).toList();
        Set<Move> resultMoves = new HashSet<>();
        Queue<Move> frontier = new ArrayDeque<>();
        //System.out.println(playerTiles);
        for(Tile tile: playerTiles){
            Set<Move> temp = singleJumpsForTile(tile);
            resultMoves.addAll(temp);
            frontier.addAll(temp);
            while(!frontier.isEmpty()){
                Move current = frontier.poll();
                Set<Move> locTemp = singleJumpsForMove(current);
                resultMoves.addAll(locTemp);
                frontier.addAll(locTemp);
            }
        }
        return resultMoves;
    }



    private boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 16 && y >= 0 && y < 16;
    }
}
