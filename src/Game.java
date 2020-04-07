import java.util.ArrayList;

/**
 * Reversi game class
 *
 * @version 0.8
 * @author Mostafa_BJN
 */
public class Game {
    private Board gameBoard;

    /**
     * Start a new Game
     */
    public Game(){
        gameBoard = new Board();
    }

    /**
     * Revers all opposite colors in all possible directions when player put a dot.
     *
     * @param block putted block
     * @param player player's turn
     */
    public void reverser(Block block, int player) {
        int blockCoordinatesH = block.getCoordinatesChar() - 65;
        int blockCoordinatesV = block.getCoordinatesNum() - 1;
        int size = Board.SIZE;
        //I<0 Up , I>0 Down , J<0 Left , J>0 Right
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, 1, 0);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, -1, 0);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, 0, -1);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, 0, 1);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, 1, -1);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, -1, 1);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, -1, -1);
        directionReverse(blockCoordinatesH, blockCoordinatesV, size, player, 1, 1);

    }

    /**
     * Revers all opposite colors in a selected row when reverser call it.
     *
     * @param horizontal Horizontal coordinates
     * @param vertical Vertical coordinates
     * @param size size of each row
     * @param player player's turn
     * @param dirI Vertical direction
     * @param dirJ Horizontal direction
     */
    private void directionReverse (int horizontal, int vertical, int size, int player, int dirI, int dirJ){
        for(int j = horizontal + dirJ, i = vertical + dirI;j >=0 && j < 8 && i >= 0 && i < 8; j+=dirJ, i+=dirI) {
            Block blockToCheck = gameBoard.getBlocks().get(i * size + j);
            if(blockToCheck.getState() > 0) {
                if(blockToCheck.getState() == player) {
                    for (j-=dirJ, i-=dirI; ((j*dirJ) >= (horizontal*dirJ + dirJ*dirJ)) && ((i*dirI) >= (vertical*dirI + dirI*dirI)); j-=dirJ, i-=dirI) {
                        Block blockToChange = gameBoard.getBlocks().get(i * size + j);
                        blockToChange.colorBlock(player);
                    }
                    return;
                }
            }
            else{
                return;
            }
        }
    }

    /**
     * count score of player.
     *
     * @param player player's turn
     * @return score of player
     */
    public int score(int player) {
        int score = 0;
        for(Block block:gameBoard.getBlocks()){
            if(block.getState() == player)
                score++;
        }
        return score;
    }

    /**
     * Show score of both players.
     */
    public void showScores() {
        System.out.println("White : " + score(1));
        System.out.println("Black : " + score(2));
    }

    /**
     * check if board is full or not.
     *
     * @return is board full
     */
    public boolean endCheck() {
        for(Block block:gameBoard.getBlocks()){
            if(block.getState() < 1)
                return false;
        }
        return true;
    }

    /**
     * check if a player can put in a block or not.
     *
     * @param player player's turn
     * @return is game passed
     */
    public boolean pass(int player){
        for(Block block:gameBoard.getBlocks()){
            if(block.getState() == -player)
                return false;
        }
        return true;
    }

    /**
     * put a dot in an available block.
     *
     * @param blockToPut Block to put
     * @param player player's turn
     * @return is it possible to put at this block
     */
    public boolean putter(Block blockToPut, int player) {
        for (Block block:gameBoard.getBlocks()) {
            if(block.equals(blockToPut)){
                if(block.getState()==(-player)) {
                    block.colorBlock(player);
                    reverser(block, player);
                    return true;
                }
                else{
                    System.out.println("Not Allowed");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * remove available blocks of last player. (clean the board from extras)
     */
    public void removeAvailableBlocks() {
        for (Block block:gameBoard.getBlocks()) {
            if(block.getState() < 0)
                block.emptyBlock();
        }
    }

    /**
     * Find all available blocks witch can player put.
     *
     * @param player player's turn
     */
    public void findAvailableBlocks(int player){
        for (Block block:gameBoard.getBlocks()) {
            //be empty
            if(block.getState() == 0) {

                int blockCoordinatesH = block.getCoordinatesChar() - 65;
                int blockCoordinatesV = block.getCoordinatesNum() - 1;
                int size = Board.SIZE;
                if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, 0, 1));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, 0, -1));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, 1, 0));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, 1, 1));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, 1, -1));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, -1, 0));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, -1, 1));
                else if(directionChecker(blockCoordinatesH, blockCoordinatesV, size, player, block, -1, -1));
            }
        }
    }

    /**
     * Check all opposite colors in a selected row when finder call it.
     *
     *
     * @param horizontal Horizontal coordinates
     * @param vertical Vertical coordinates
     * @param size size of each row
     * @param player player's turn
     * @param block Block to check for availability
     * @param dirI Vertical direction
     * @param dirJ Horizontal direction
     * @return if
     */
    private boolean directionChecker(int horizontal, int vertical, int size, int player, Block block,int dirI, int dirJ){
        for(int i = vertical + dirI, j = horizontal + dirJ, count = 0; j >=0 && j < 8 && i >= 0 && i < 8; j+=dirJ, i+=dirI, count++) {
            Block blockToCheck = gameBoard.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if (check == 1)
                return true;
            else if (check == -1)
                break;
        }
        return false;
    }

    /**
     * Check if player can put in this block or not.
     *
     * @param block Block to check for availability
     * @param blockToCheck Block to check inside of it
     * @param player player's turn
     * @param count number of calling this function in a row.
     * @return
     */
    private int checkBlock(Block block, Block blockToCheck, int player, int count){ ;
        if (count == 0 && blockToCheck.getState() == player) {
            return -1;
        }
        if (blockToCheck.getState() > 0) {
            if (checkPlayer(block, blockToCheck, player)) {
                return 1;
            }
            else {
                return 0;
            }
        } else
            return -1;
    }

    /**
     * Ready block for put availability.
     *
     * @param block Block to check for availability
     * @param blockToCheck Block to check inside of it
     * @param player player's turn
     * @return if
     */
    private boolean checkPlayer(Block block, Block blockToCheck, int player){
        if(blockToCheck.getState() == player){
            block.availableBlock(player);
            return true;
        }
        return false;
    }

    /**
     * Put 4 Dots in the middle of board.
     */
    public void gettingReady(){
        gameBoard.getBlocks().get((4-1)*Board.SIZE + (4-1)).colorBlock(1);
        gameBoard.getBlocks().get((5-1)*Board.SIZE + (5-1)).colorBlock(1);
        gameBoard.getBlocks().get((4-1)*Board.SIZE + (5-1)).colorBlock(2);
        gameBoard.getBlocks().get((5-1)*Board.SIZE + (4-1)).colorBlock(2);
    }

    /**
     * an AI for playing with players.
     * It has a simple strategy for putting, but it works well.
     *
     * @return block to put.
     */
    public Block playWithSystem(){
        char c = 0;
        int n = 0;
        int size = Board.SIZE;
        //4 corner
        for(c = 0; c < 8; c+=7)
            for (n = 0; n < 8; n+=7)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //1 & 8 lines
        for(c = 0; c < 8; c+=7)
            for(n = 1; n < 7; n++)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        for(n = 0; n < 8; n+=7)
            for(c = 1; c < 7; c++)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //4*4 squer
        for(c = 2; c < 6; c++)
            for (n = 2; n < 6; n++)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //2 & 7 lines
        for(c = 1; c < 7; c+=5)
            for(n = 1; n < 7; n++)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        for(n = 1; n < 7; n+=5)
            for(c = 1; c < 7; c++)
                if(gameBoard.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        return null;
    }

    /**
     * getter for Board of game
     *
     * @return gameBoard parameter
     */
    public Board getGameBoard() {
        return gameBoard;
    }

}
