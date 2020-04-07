import java.util.Scanner;

/**
 * Run of game includes menus
 *
 * @version 1.0
 * @author Mostafa_BJN
 */
public abstract class Run {
    //Scanner for getting input
    private static Scanner scan = new Scanner(System.in);
    //Reversi game
    private static Game game;
    //player which starts the game , 0 = Black , 1 = White
    private static int turn;
    //time of game
    private static int time;
    //AI Color if Turned On
    private static int systemColor;
    //Auto Save
    private static boolean autoSave;
    //Auto Score Show
    private static boolean autoScoreShow;
    //If a game starts, you can resume it
    private static boolean resumeble;
    //AI, On or Off
    private static boolean systemPlayer;
    //In game menu On or Off
    private static boolean inGameMenu;

    /**
     * Start of App
     */
    public static void main(String[] args) {
        autoScoreShow = true;
        autoSave = true;
        inGameMenu = true;
        Block.setGuideForPutting(true);
        mainMenu();
    }

    /**
     * Run of Reversi game
     */
    public static void playGame(){
        //White = 0 , Black = 1
        if(!resumeble) {
            turn = 0;
            systemColor = 0;
            resumeble = true;
            if (systemPlayer) {
                while (true) {
                    System.out.println("*Select Your Color*");
                    System.out.println("1)White");
                    System.out.println("2)Black");
                    try {
                        systemColor = scan.nextInt();
                        //for being out of menu
                        if (systemColor != 2 && systemColor != 1) {
                            System.out.println("!!! It's Unavailable !!!");
                            continue;
                        }
                        systemColor = 3 - systemColor;
                        break;
                    } catch (Exception e) {
                        scan.nextLine();
                        System.out.println("!!! WRONG INPUT !!!");
                    }
                }
            }
            while (true) {
                System.out.println("*Select Who Start Game*");
                System.out.println("1)White");
                System.out.println("2)Black");
                try {
                    turn = scan.nextInt();
                    //for being out of menu
                    if (turn != 2 && turn != 1) {
                        System.out.println("!!! It's Unavailable !!!");
                        continue;
                    }
                    turn--;
                    break;
                } catch (Exception e) {
                    scan.nextLine();
                    System.out.println("!!! WRONG INPUT !!!");
                }
            }
            game.gettingReady();
            time = 0;
        }
        for (int earlyEnd = 0; true; time++) {
            int player = ((time + turn) % 2) + 1;
            game.findAvailableBlocks(player);
            if (game.endCheck()) {
                game.getGameBoard().print();
                break;
            }
            if (game.pass(player)) {
                System.out.println("*PASS*");
                earlyEnd++;
                if (earlyEnd == 2) {
                    game.getGameBoard().print();
                    System.out.println("There is NO Possible Place to Put");
                    break;
                }
                continue;
            } else {
                earlyEnd = 0;
            }
            if (autoScoreShow)
                game.showScores();
            game.getGameBoard().print();
            if(!systemPlayer) {
                if (player == 1)
                    System.out.println("White Turn");
                else
                    System.out.println("Black Turn");
            }
            if(player == systemColor) {
                game.putter(game.playWithSystem(), player);
            }
            else {
               if(systemPlayer)
                   System.out.println("Your Turn");
               if(inGameMenu)
                   gameMenu(player);
               else
                   while (!game.putter(inputGetter(), player));
            }
            game.removeAvailableBlocks();
        }
        resumeble = false;
        game.showScores();
        System.out.println("End of Game");
        if(game.score(1) > game.score(2)){
            System.out.println("***White Wins***");
        }
        else if(game.score(1) < game.score(2)){
            System.out.println("***Black Wins***");
        }
        else {
            System.out.println("***Draw***");
        }
        mainMenu();
    }

    /**
     * Showing in-Game menu dynamical
     */
    private static int dynamicGameMenuShow(){
        int i = 0;
        System.out.println("1)Put");
        if (!autoScoreShow) {
            System.out.println((2 + i) + ")Show Score");
            i++;
        }
        if (!autoSave) {
            System.out.println((2 + i) + ")Save");
            i++;
        }
        System.out.println((2 + i) + ")Main Menu");
        //get input for in-game choice
        while (true) {
            try {
                int choice = scan.nextInt();
                //for being out of menu
                if (choice < 1 || choice > (2 + i)) {
                    System.out.println("!!! It's Unavailable !!!");
                    continue;
                }
                //for Main Menu
                if (choice > (1 + i))
                    choice = 4;
                //for Save
                else if (!autoSave && (choice == 1 + i))
                    choice = 3;
                return choice;
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
    }

    /**
     * In-Game Menu which you can :
     * Put
     * Show Score, If auto Show is off
     * Save, If auto Save is off
     * Back to the Main Menu
     *
     * @param player player who its turn
     */
    public static void gameMenu(int player) {
        int choice = 0;
        while (choice != 1) {
            choice = dynamicGameMenuShow();
            switch (choice) {
                case 1:
                    while (!game.putter(inputGetter(), player)) ;
                    break;
                case 2:
                    game.showScores();
                    break;
                case 3:
                    System.out.println("Not Available Yet");
                    //save();
                    break;
                case 4:
                    game.removeAvailableBlocks();
                    mainMenu();
                    return;
            }
        }
    }

    /**
     * Main Menu which you can :
     * Resume a started game
     * Start a new game with one or two players
     * Change options
     */
    public static void mainMenu() {
        int choice = 0;
        System.out.println("***MAIN MENU***");
        System.out.println("1)Resume");
        System.out.println("2)New 1 Player Game");
        System.out.println("3)New 2 Player Game");
        System.out.println("4)Options");
        System.out.println("0)Exit");
        while (true) {
            try {
                choice = scan.nextInt();
                //for being out of menu
                if (choice < 0 || choice > 4) {
                    System.out.println("!!! It's Unavailable !!!");
                    continue;
                }
                if(choice == 1 && !resumeble){
                    System.out.println("You Haven't Started a Game Yet !");
                    continue;
                }
                break;
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
        switch (choice) {
            case 1:
                playGame();
                break;
            case 2:
                resumeble = false;
                game = new Game();
                systemPlayer = true;
                playGame();
                break;
            case 3:
                resumeble = false;
                game = new Game();
                systemPlayer = false;
                playGame();
                break;
            //case 4:
                //load();
               //break;
            //case 5:
                //gamesHistory();
                //break;
            case 4:
                options();
                break;
            case 0:
                System.out.println("***Good Bye***");
                System.out.println(":-)");
                System.exit(0);
                break;
        }
    }

    /**
     * Options Menu which you can turn On or Off :
     * Auto Save
     * Auto Score Show
     * Guide for Putting
     * In-Game Menu
     */
    public static void options(){
        int choice = 0;
        while (true) {
            System.out.println("***OPTIONS***");
            System.out.println("1)Auto Save On");
            System.out.println("-1)Auto Save Off");
            System.out.println("2)Auto Score Show On");
            System.out.println("-2)Auto Score Show Off");
            System.out.println("3)Guide for Putting On");
            System.out.println("-3)Guide for Putting Off");
            System.out.println("4)In-Game Menu On");
            System.out.println("-4)In-Game Menu Off");
            System.out.println("0)Back to Main Menu");
            while (true) {
                try {
                    choice = scan.nextInt();
                    //for being out of menu
                    if ((choice < -5 || choice > 5)) {
                        System.out.println("!!! It's Unavailable !!!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("!!! WRONG INPUT !!!");
                }
            }
            switch (choice) {
                case 1:
                    System.out.println("This Feature is Unavailable Now");
                    autoSave = true;
                    break;
                case -1:
                    System.out.println("This Feature is Unavailable Now");
                    autoSave = false;
                    break;
                case 2:
                    autoScoreShow = true;
                    break;
                case 3:
                    Block.setGuideForPutting(true);
                    break;
                case -2:
                    autoScoreShow = false;
                    break;
                case -3:
                    Block.setGuideForPutting(false);
                    break;
                case 4:
                    inGameMenu = true;
                    break;
                case -4:
                    inGameMenu = false;
                    break;
                case 0:
                    mainMenu();
                    return;
            }
        }
    }

    /**
     * Get input for put in board
     */
    public static Block inputGetter() {
        char c = 0;
        int n = 0;
        while(true) {//getting input
            System.out.print("Enter Position : ");
            try {
                n = scan.nextInt();
                if (n < 1 || n > 8){
                    System.out.println("!!! OUT OF RANGE !!!");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("!!! WRONG INPUT !!!");
                scan.nextLine();
                continue;
            }
            try {
                c = scan.next().charAt(0);
                if (c < 65 || c > 72){
                    System.out.println("!!! WRONG INPUT !!!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
        return new Block(c, n);
    }

    //problem whit coping object
    public abstract void undo();

    //problem whit coping object
    public abstract void redo();

    //When I Learn to Work With Files
    public abstract void save();

    //When I Learn to Work With Files
    public abstract void load();

    //When I Learn to Work With Files
    public abstract void gamesHistory();


}

