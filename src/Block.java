import java.util.Objects;

/**
 *
 */
public class Block {
    private static final String BLACK_CIRCLE = "◉";
    private static final String WHITE_CIRCLE = "◎";
    private static final String EMPTY_BLOCK = "▢";
    private static final String AVAILABLE_BLOCK = "◍";
    private static boolean guideForPutting;
    private String sign;
    //Coordinates Number : 1,2,3,4,5,6,7,8
    private int coordinatesNum;
    //Coordinates Character : A,B,C,D,E,F,G,H
    private char coordinatesChar;
    //State of block : Empty = 0 , White = 1 , Black = 2 , Available for White = -1 , Available For Black = -2
    private int state;

    /**
     *
     */
    public Block(char coordinatesChar, int coordinatesNum) {
        sign = "▢";
        this.coordinatesChar = coordinatesChar;
        this.coordinatesNum = coordinatesNum;
        state = 0;
        guideForPutting = true;
    }

    /**
     *
     */
    public void colorBlock(int player){
        state = player;
        if(player == 1) {
            sign = WHITE_CIRCLE;
        }
        else if(player == 2) {
            sign = BLACK_CIRCLE;
        }
    }

    /**
     *
     */
    public void availableBlock(int player){
        state = -player;
        if(guideForPutting)
            sign = AVAILABLE_BLOCK;
        else
            sign = EMPTY_BLOCK;
    }

    /**
     *
     */
    public void emptyBlock(){
        state = 0;
        sign = EMPTY_BLOCK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;
        Block block = (Block) o;
        return coordinatesNum == block.coordinatesNum &&
                coordinatesChar == block.coordinatesChar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinatesNum, coordinatesChar);
    }

    public String getSign() {
        return sign;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCoordinatesNum() {
        return coordinatesNum;
    }

    public char getCoordinatesChar() {
        return coordinatesChar;
    }

    public static void setGuideForPutting(boolean guideForPutting) {
        Block.guideForPutting = guideForPutting;
    }
}
