import java.util.Scanner;

/**
 *
 */
public abstract class Run {
    private static Scanner scan = new Scanner(System.in);
    private static Game game;
    private static int time;
    private static boolean redoAvailable = true;
    private static boolean autoSave;
    private static boolean autoScoreShow;
    private static boolean resumeble;
    private static boolean systemPlayer = true;
    private static boolean undoed;

    public static void main(String[] args) {
        resumeble = false;
        autoScoreShow = true;
        autoSave = true;
        mainMenu();
    }

    public static void playGame(){
        //White = 0 , Black = 1
        int turn;
        int systemColor = 0;
        if(systemPlayer) {
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
        undoed = false;
        int earlyEnd = 0;
        for (time = 0; true; time++) {
            System.out.println("time = " + time);
            if(!undoed) {
                game.getBoards().add(game.getGameBoardNow());
                game.setGameBoardNow(new Board(game.getGameBoardNow()));
                //game.getBoards().get(0).print();
                undoed = false;
            }
            game.getBoards().get(0).print();
            System.out.println("HYYH");
            int player = ((time + turn) % 2) + 1;
            game.findAvailableBlocks(player);
            if (game.endCheck()) {
                game.getGameBoardNow().print();
                break;
            }
            if (game.pass(player)) {
                System.out.println("Pass");
                earlyEnd++;
                if (earlyEnd == 2) {
                    game.getGameBoardNow().print();
                    System.out.println("There is NO Possible Place to Put");
                    break;
                }
                continue;
            } else {
                earlyEnd = 0;
            }
            if (autoScoreShow)
                game.showScores();
            game.getGameBoardNow().print();
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
                gameMenu(player);
                //while (!game.putter(inputGetter(), player)) ;
            }
            game.removeAvailableBlocks();
        }
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
    }

    public static int dynamicGameMenuShow(){
        int i = 0;
        System.out.println("1)Put");
        System.out.println("2)Undo");
        if (!redoAvailable) {
            System.out.println((3) + ")Redo");
            i++;
        }
        if (!autoScoreShow) {
            System.out.println((3 + i) + ")Show Score");
            i++;
        }
        if (!autoSave) {
            System.out.println((3 + i) + ")Save");
            i++;
        }
        System.out.println((3 + i) + ")Main Menu");
        //get input for in-game choice
        while (true) {
            try {
                int choice = scan.nextInt();
                //for being out of menu
                if (choice < 1 || choice > (3 + i)) {
                    System.out.println("!!! It's Unavailable !!!");
                    continue;
                }
                //for Main Menu
                if (choice > (2 + i))
                    choice = 6;
                    //for Redo
                else if (!autoScoreShow && ((!redoAvailable && choice == 4) || (redoAvailable && choice == 3)))
                    choice = 4;
                    //for Save
                else if (!autoSave && (choice == 2 + i))
                    choice = 5;
                return choice;
            } catch (Exception e) {
                scan.nextLine();
                System.out.println("!!! WRONG INPUT !!!");
            }
        }
    }

    /**
     *
     */
    public static void gameMenu(int player) {
        int choice = dynamicGameMenuShow();
        switch (choice) {
            case 1:
                while(!game.putter(inputGetter(), player));
                break;
            case 2:
                undo();
                break;
            case 3:
                //redo();
                break;
            case 4:
                game.showScores();
                break;
            case 5:
                //save();
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
                if ((choice < 1 || choice > 3) || (choice == 1 && !resumeble)) {
                    System.out.println("!!! It's Unavailable !!!");
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
                if(resumeble)
                    resume();
                else
                    System.out.println("You have not started a game yet");
                break;
            case 2:
                game = new Game();
                systemPlayer = true;
                playGame();
                break;
            case 3:
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

    public static void resume(){

    }

    public static void options(){
        int choice = 0;
        System.out.println("***OPTIONS***");
        System.out.println("1)Auto Save On");
        System.out.println("-1)Auto Save Off");
        System.out.println("2)Auto Score Show On");
        System.out.println("-2)Auto Score Show Off");
        System.out.println("3)Guide for Putting On");
        System.out.println("-3)Guide for Putting Off");
        System.out.println("0)Back to Main Menu");
        while (true) {
            try {
                choice = scan.nextInt();
                //for being out of menu
                if ((choice < -4 || choice > 4 )) {
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
            case -1:
                System.out.println("This Feature is Unavailable Now");
                autoSave = true;
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
            case 0:
                return;
        }
    }

    public static void undo(){
        if(time == 0){
            System.out.println("It is First Move");
            time--;
            return;
        }
        time-=2;
        game.setGameBoardNow(game.getBoards().get(time + 1));
        game.getBoards().get(0).print();
        undoed = true;
    }

    public abstract void redo();

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

    //When I Learn to Work With Files
    public abstract void save();

    //When I Learn to Work With Files
    public abstract void load();

    //When I Learn to Work With Files
    public abstract void gamesHistory();


}

