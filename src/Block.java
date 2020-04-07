import java.util.Objects;

/**
 * This Class is for each Block in
 *
 * @version 0.6
 * @author Mostafa_BJN
 */
public class Block {
    private static final String BLACK_CIRCLE = "◉";
    private static final String WHITE_CIRCLE = "◎";
    private static final String EMPTY_BLOCK = "▢";
    private static final String AVAILABLE_BLOCK = "◍";
    private static boolean guideForPutting;
    //Sign which shows in Board
    private String sign;
    //Coordinates Number : 1,2,3,4,5,6,7,8
    private int coordinatesNum;
    //Coordinates Character : A,B,C,D,E,F,G,H
    private char coordinatesChar;
    //State of block : Empty = 0 , White = 1 , Black = 2 , Available for White = -1 , Available For Black = -2
    private int state;

    /**
     * Make a new empty Block with given coordinates
     *
     * @param coordinatesChar Horizontal coordinates
     * @param coordinatesNum Vertical coordinates
     */
    public Block(char coordinatesChar, int coordinatesNum) {
        sign = "▢";
        this.coordinatesChar = coordinatesChar;
        this.coordinatesNum = coordinatesNum;
        state = 0;
    }

    /**
     * Make a block colored
     *
     * @param player the player which block fulled with its color
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
     * Make a block available for putting
     *
     * @param player the player which block available for
     */
    public void availableBlock(int player){
        state = -player;
        if(guideForPutting)
            sign = AVAILABLE_BLOCK;
        else
            sign = EMPTY_BLOCK;
    }

    /**
     * Make a block empty
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

    /**
     * getter for sign
     *
     * @return sign
     */
    public String getSign() {
        return sign;
    }

    /**
     * getter for state
     *
     * @return state parameter
     */
    public int getState() {
        return state;
    }

    /**
     * getter for Horizontal Coordinates of Block
     *
     * @return coordinatesNum parameter
     */
    public int getCoordinatesNum() {
        return coordinatesNum;
    }

    /**
     * getter for Vertical Coordinates of Block
     *
     * @return coordinatesChar parameter
     */
    public char getCoordinatesChar() {
        return coordinatesChar;
    }

    /**
     * setter for guideForPutting parameter
     */
    public static void setGuideForPutting(boolean guideForPutting) {
        Block.guideForPutting = guideForPutting;
    }
}
