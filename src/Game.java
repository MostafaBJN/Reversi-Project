import java.util.ArrayList;

/**
 *
 */
public class Game {
    private Board gameBoardNow;
    private ArrayList<Board> boards;

    /**
     *
     */
    public Game(){
        gameBoardNow = new Board();
        boards = new ArrayList<Board>();
    }

    /**
     *
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

    private void directionReverse (int horizontal, int vertical, int size, int player, int dirI, int dirJ){
        for(int j = horizontal + dirJ, i = vertical + dirI;j >=0 && j < 8 && i >= 0 && i < 8; j+=dirJ, i+=dirI) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            if(blockToCheck.getState() > 0) {
                if(blockToCheck.getState() == player) {
                    for (j-=dirJ, i-=dirI; ((j*dirJ) >= (horizontal*dirJ + dirJ*dirJ)) && ((i*dirI) >= (vertical*dirI + dirI*dirI)); j-=dirJ, i-=dirI) {
                        Block blockToChange = gameBoardNow.getBlocks().get(i * size + j);
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
     *
     */
    public int score(int player) {
        int score = 0;
        for(Block block:gameBoardNow.getBlocks()){
            if(block.getState() == player)
                score++;
        }
        return score;
    }

    /**
     *
     */
    public void showScores() {
        System.out.println("White : " + score(1));
        System.out.println("Black : " + score(2));
    }

    /**
     *
     */
    public boolean endCheck() {
        for(Block block:gameBoardNow.getBlocks()){
            if(block.getState() < 1)
                return false;
        }
        return true;
    }

    /**
     *
     */
    public boolean pass(int player){
        for(Block block:gameBoardNow.getBlocks()){
            if(block.getState() == -player)
                return false;
        }
        return true;
    }

    /**
     *
     */
    public boolean putter(Block blockToPut, int player) {
        for (Block block:gameBoardNow.getBlocks()) {
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
     *
     */
    public void removeAvailableBlocks() {
        for (Block block:gameBoardNow.getBlocks()) {
            if(block.getState() < 0)
                block.emptyBlock();
        }
    }

    /**
     *
     */
    public void findAvailableBlocks(int player){
        for (Block block:gameBoardNow.getBlocks()) {
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

    private boolean directionChecker(int horizontal, int vertical, int size, int player, Block block,int dirI, int dirJ){
        for(int i = vertical + dirI, j = horizontal + dirJ, count = 0; j >=0 && j < 8 && i >= 0 && i < 8; j+=dirJ, i+=dirI, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if (check == 1)
                return true;
            else if (check == -1)
                break;
        }
        return false;
    }

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

    private boolean checkPlayer(Block block, Block blockToCheck, int player){
        if(blockToCheck.getState() == player){
            block.availableBlock(player);
            return true;
        }
        return false;
    }

    /**
     *
     */
    public void gettingReady(){
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (4-1)).colorBlock(1);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (5-1)).colorBlock(1);
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (5-1)).colorBlock(2);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (4-1)).colorBlock(2);
    }

    public Block playWithSystem(){
        char c = 0;
        int n = 0;
        int size = Board.SIZE;
        //4 corner
        for(c = 0; c < 8; c+=7)
            for (n = 0; n < 8; n+=7)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //1 & 8 lines
        for(c = 0; c < 8; c+=7)
            for(n = 1; n < 7; n++)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        for(n = 0; n < 8; n+=7)
            for(c = 1; c < 7; c++)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //4*4 squer
        for(c = 2; c < 6; c++)
            for (n = 2; n < 6; n++)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        //2 & 7 lines
        for(c = 1; c < 7; c+=5)
            for(n = 1; n < 7; n++)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        for(n = 1; n < 7; n+=5)
            for(c = 1; c < 7; c++)
                if(gameBoardNow.getBlocks().get(n * size + c).getState() < 0)
                    return new Block((char)(c + 65), n + 1);
        return null;
    }

    public Board getGameBoardNow() {
        return gameBoardNow;
    }

    public void setGameBoardNow(Board gameBoardNow) {
        this.gameBoardNow = gameBoardNow;
    }

    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }

}
