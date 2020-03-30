import java.util.ArrayList;

/**
 *
 */
public class Board {
    static final int SIZE = 8;
    private ArrayList<Block> blocks;


    public Board(){
        blocks = new ArrayList<Block>();
        emptyBoardMaker();
    }

    private void emptyBoardMaker(){
        for (int i = 1; i < SIZE + 1; i++) {
            for (int j = 1; j < SIZE + 1; j++) {
                blocks.add(new Block((char)(64 + j), i));
            }
        }
    }

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

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

}
