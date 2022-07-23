package CardGame;
import java.awt.Color;
/**
 * The Button class describes the appearance of a button to be displayed onto the
 * user interface.
 * @author Sarah Simionescu
 * @version 1
 */
public class Button {
    /** x position of the button*/
    int xpos;
    /** y position of the button*/
    int ypos;
    /** size of the button*/
    int size;
    /** width of the button*/
    int width = 0;
    /** height of the button*/
    int height;
    /** text written on the button*/
    String text;
    /** color of the button*/
    Color buttonColor;
    /** color of the text on the button*/
    Color textColor;
    
    /**
     * Initializes the button and determines the color of its text to contrast
     * the color of the button.
     * @param x X position of the button.
     * @param y Y position of the button.
     * @param t Text written on the button.
     * @param s Size of the text on the button.
     * @param c Color of the button.
     */
    public Button(int x, int y, int s, String t, Color c) {

        xpos = x;
        ypos = y;
        text = t;
        size = s;

        buttonColor = c;
        if (buttonColor.getAlpha() < 112) { //if the background color is light

            textColor = Color.BLACK; //set the text color to black
        } else { //else the background color is dark
            textColor = Color.white; //set the text color to white
        }

    }

}
