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

    public void putter(Block blockToPut,int player) {
        for (Block block:gameBoardNow.getBlocks()) {
            if(block.equals(blockToPut)){
                //if(block.getState()==(-player)){
                    block.fullBlock(player);
                //}
            }
        }
    }

    public void gettingReady(){
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (4-1)).fullBlock(1);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (5-1)).fullBlock(1);
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (5-1)).fullBlock(2);
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (4-1)).fullBlock(2);
        gameBoardNow.getBlocks().get((3-1)*Board.SIZE + (4-1)).availableBlock(1);//
        gameBoardNow.getBlocks().get((4-1)*Board.SIZE + (3-1)).availableBlock(1);//
        gameBoardNow.getBlocks().get((6-1)*Board.SIZE + (5-1)).availableBlock(1);//
        gameBoardNow.getBlocks().get((5-1)*Board.SIZE + (6-1)).availableBlock(1);//
    }

    public void findAvailableBlocks(int player){

    }

    //public Board undo(int time){

    //}

    public Board getGameBoard() {
        return gameBoardNow;
    }

    public void setGameBoard(Board gameBoardNow) {
        this.gameBoardNow = gameBoardNow;
    }
}
