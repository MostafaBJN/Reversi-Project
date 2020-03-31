import java.util.Scanner;

/**
 *
 */
public abstract class Run {
    private static Scanner scan = new Scanner(System.in);
    private static Game game = new Game();
    private static int time;
    //White = 0 , Black = 1
    private static int turn;
    private static boolean redoAvailable;
    private static boolean autoSave;
    private static boolean autoScoreShow;

    public static void main(String[] args) {
        game.gettingReady();
        for (int i = 0; i<4; i++) {
            time = i;
            turn = 0;
            int player = (time + turn)%2 + 1;
            game.findAvailableBlocks(player);
            game.getGameBoard().print();
            //gameMenu();
            while(!game.putter(inputGetter(), player));
            game.removeAvailableBlocks();
        }
    }

    /**
     *
     */
    public static void gameMenu() {
        int choice;
        int player = (time + turn)%2 + 1;
        if (player == 1) {
            System.out.println("White Turn");
        }
        else {
            System.out.println("Black Turn");
        }
        int i = 0;
        System.out.println("1)Put");
        System.out.println("2)Undo");
        if(!redoAvailable) {
            System.out.println((3) + ")Redo");
            i++;
        }
        if(!autoScoreShow){
            System.out.println((3 + i) + ")Show Score");
            i++;
        }
        if(!autoSave){
            System.out.println((3 + i) + ")Save");
            i++;
        }
        System.out.println((3 + i) + ")Main Menu");
        //get input for game do choice
        while (true) {
            try {
                choice = scan.nextInt();
                //for being out of menu
                if(choice < 1 || choice > (3 + i)){
                        System.out.println("!!! It's Unavailable !!!");
                        continue;
                }
                //for Main Menu
                if(choice > (2 + i))
                    choice = 6;
                //for Redo
                else if(!autoScoreShow && ((!redoAvailable && choice == 4) || (redoAvailable && choice == 3)))
                    choice = 4;
                //for Save
                else if(!autoSave && (choice == 2 + i))
                    choice = 5;
                break;
            } catch (Exception e) {
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
        switch (choice) {
            case 1:
                game.putter(inputGetter(), player);
                break;
            case 2:
                //game.undo(time);
                break;
            case 3:
                //game.redo();
                break;
            case 4:
                //game.showScore();
                break;
            case 5:
                //game.save();
                break;
            case 6:
                mainMenu();
        }
    }

    /**
     *
     */
    public static void mainMenu() {
        int choice = 0;
        switch (choice) {
            case 1:
                //resume();
                break;
            case 2:
                //newGame();
                break;
            case 3:
                //Load();
                break;
            case 4:
                //gameHistory();
                break;
            case 5:
                //options();
                break;
            case 6:
                System.exit(0);
                return;
        }
    }

    public abstract void Save();

    public abstract void Load();

    public static Block inputGetter() {
        char c = 0;
        int n = 0;
        while(true) {//getting input
            System.out.print("Enter Position : ");
            try {
                c = scan.next().charAt(0);
                if (c < 65 || c > 72){
                    System.out.println("!!! WRONG INPUT !!!");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("!!! WRONG INPUT !!!");
                continue;
            }
            try {
                n = scan.nextInt();
                if (n < 1 || n > 8){
                    System.out.println("!!! OUT OF RANGE !!!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
        return new Block(c, n);
    }

}

