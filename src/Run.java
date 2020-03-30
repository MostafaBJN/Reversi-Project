import java.util.Scanner;

/**
 *
 */
public class Run {
    public static void main(String[] args) {
        Game game = new Game();
        game.gettingReady();
        int player;
        for (int i = 0; true; i++) {
            player = i%2 + 1;
            if (player == 1) {
                System.out.println("Its White Turn");
            }
            else {
                System.out.println("Its Black Turn");
            }
            game.getGameBoard().print();
            game.putter(inputGetter(),i%2+1);
            //if(Contr){

            //}
            //else if(){

            //}

        }
    }

    public static Block inputGetter(){
        Scanner scan = new Scanner(System.in);
        char c = 0;
        int n = 0;
        while(true) {//getting input
            System.out.print("Enter Position : ");
            try {
                c = scan.next().charAt(0);
                if (c < 65 || c > 72){
                    System.out.println("!!! OUT OF RANGE !!!");
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

