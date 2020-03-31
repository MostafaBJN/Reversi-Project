import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;

/**
 *
 */
public class Game {
    private Board gameBoardNow;
    private ArrayList<Board> boards;

    public Game(){
        gameBoardNow = new Board();
        boards = new ArrayList<Board>();
        boards.add(gameBoardNow);
    }

    public boolean putter(Block blockToPut,int player) {
        for (Block block:gameBoardNow.getBlocks()) {
            if(block.equals(blockToPut)){
                //if(block.getState()==(-player)) {
                    block.fullBlock(player);
                    System.out.println("Putting Completed");
                    return true;
                //}
                //else{
                  //  System.out.println("Not Allowed");
                    //return false;
                //}
            }
        }
        return false;
    }

    public void gettingReady(){
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (4-1)).fullBlock(1);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (5-1)).fullBlock(1);
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (5-1)).fullBlock(2);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (4-1)).fullBlock(2);
        gameBoardNow.getBlocks().get((3-1)*Board.SIZE + (5-1)).fullBlock(2);
        gameBoardNow.getBlocks().get((3-1)*Board.SIZE + (4-1)).fullBlock(2);
    }

    public void findAvailableBlocks(int player){
        for (Block block:gameBoardNow.getBlocks()) {
            //be empty
            if(block.getState() == 0) {//////////////HHHHHHHHHHH=CCCCCCCCCCCC      VVVVVVVVVVVV=NNNNNNNNNNN

                int blockCoordinatesH = block.getCoordinatesChar() - 65;
                int blockCoordinatesV = block.getCoordinatesNum() - 1;
                int size = Board.SIZE;
                //Up
                if(up(blockCoordinatesH, blockCoordinatesV, size, player, block))
                  continue;
                //Down
                if(down(blockCoordinatesH, blockCoordinatesV, size, player, block))
                    continue;
                //Right
                if(right(blockCoordinatesH, blockCoordinatesV, size, player, block))
                  continue;
                //Left
                if(left(blockCoordinatesH, blockCoordinatesV, size, player, block))
                   continue;
                //Up-Right
                if(upRight(blockCoordinatesH, blockCoordinatesV, size, player, block))
                    continue;
                //Up-Left
                if(upLeft(blockCoordinatesH, blockCoordinatesV, size, player, block))
                    continue;
                //Down-Right
                if(downRight(blockCoordinatesH, blockCoordinatesV, size, player, block))
                    continue;
                //Down-Left
                if(downLeft(blockCoordinatesH, blockCoordinatesV, size, player, block))
                    continue;

            }
        }
    }

    private boolean down(int horizontal, int vertical, int size, int player, Block block){
        //down
        for(int i = vertical - 1, count = 0; i >= 0; i--, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + horizontal);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean up(int horizontal, int vertical, int size, int player, Block block){
        //Up
        for(int i = vertical + 1, count = 0; i < 8; i++, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + horizontal);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean left(int horizontal, int vertical, int size, int player, Block block){
        //Left
        for(int j = horizontal + 1, count = 0; j < 8; j++, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(vertical * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean right(int horizontal, int vertical, int size, int player, Block block){
        //Right
        for(int j = horizontal - 1, count = 0; j >= 0; j--, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(vertical * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean downRight(int horizontal, int vertical, int size, int player, Block block){
        //Down - Right
        for(int j = horizontal - 1, i = vertical - 1, count = 0; j >= 0 && i >= 0; j--, i--, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean upLeft(int horizontal, int vertical, int size, int player, Block block){
        //Up - Left
        for(int j = horizontal + 1, i = vertical - 1, count = 0; j < 8 && i >= 0; j++, i--, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean upRight(int horizontal, int vertical, int size, int player, Block block){
        //Up - Right
        for(int j = horizontal - 1, i = vertical + 1, count = 0; j >= 0 && i < 8; j--, i++, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private boolean downLeft(int horizontal, int vertical, int size, int player, Block block){
        //Down - Left
        for(int j = horizontal + 1, i = vertical + 1, count = 0; j < 8 && i < 8; j++, i++, count++) {
            Block blockToCheck = gameBoardNow.getBlocks().get(i * size + j);
            int check = checkBlock(block, blockToCheck, player, count);
            if(check == 1)
                return true;
            else if(check == -1)
                break;
        }
        return false;
    }

    private int checkBlock(Block block, Block blockToCheck, int player, int count){
        if (count == 0 && blockToCheck.getState() == player) {
            return -1;
        }
        if (blockToCheck.getState() > 0) {
            if(checkPlayer(block, blockToCheck, player))
                return 1;
            else
                return 0;
        }
        else
            return -1;
    }

    private boolean checkPlayer(Block block, Block blockToCheck, int player){
        if(blockToCheck.getState() == player){
            block.availableBlock(player);
            return true;
        }
        else
            return false;
    }


    public void Reverser() {

    }

    public void removeAvailableBlocks() {
        for (Block block:gameBoardNow.getBlocks()) {
            if(block.getState() < 0)
                block.emptyBlock();
        }
    }

    public Board undo(int time){
        return boards.get(time);
    }

    public Board getGameBoard() {
        return gameBoardNow;
    }

    public void setGameBoard(Board gameBoardNow) {
        this.gameBoardNow = gameBoardNow;
    }
}
