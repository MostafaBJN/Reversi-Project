import java.util.ArrayList;

/**
 * Board class is for the board of Reversi game.
 *
 * @version 0.2
 * @author Mostafa_BJN
 */
public class Board {
    //number of Blocks in row for square of board of game
    static final int SIZE = 8;
    //blocks of board
    private ArrayList<Block> blocks;


    /**
     * Making a new Board for Game.
     */
    public Board(){
        blocks = new ArrayList<Block>();
        emptyBoardMaker();
    }


    /**
     * Make a board with SIZE*SIZE Blocks.
     */
    private void emptyBoardMaker(){
        for (int i = 1; i < SIZE + 1; i++) {
            for (int j = 1; j < SIZE + 1; j++) {
                blocks.add(new Block((char)(64 + j), i));
            }
        }
    }

    /**
     * Show board to players.
     */
    public void print(){
        for (int i = 0; i < SIZE ; i++) {
            if(i==0){
                System.out.print("\r  A◡B◡C◡D◡E◡F◡G◡H");
                System.out.println();
            }
            for (int j = 0; j < SIZE ; j++) {
                if(j==0) {
                    System.out.print((i + 1) + " ");
                }
                System.out.print(blocks.get(i*SIZE+j).getSign() + " ");
            }
            System.out.println();
        }
    }

    /**
     * getter for blocks arraylist.
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
