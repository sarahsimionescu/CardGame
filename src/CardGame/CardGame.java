package CardGame;
/**
 * The CardGame class contains the main method and runs two classes in parallel
 * threads separating game functions from user interface display functions.
 * @author Sarah Simionescu
 * @version 1
 */
public class CardGame{
   
     /**
     * Creates, initializes and runs two parallel threads separating game functions
     *  from user interface display functions.
     */
    public static void main(String[] args) {
        Display display = new Display();
        Game game = new Game();
        display.G = game;
        game.D = display;
        game.start();
        display.start();
    }
}
