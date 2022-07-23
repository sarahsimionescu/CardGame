package CardGame;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.Math;

/**
 * The Mouse class is responsible for responding to user input from the mouse.
 *
 * @author Sarah Simionescu
 * @version 1
 */
public class Mouse extends MouseAdapter {

    /**
     * Accesses Display class to manipulate user interface
     */
    protected Display D;
    /**
     * Accesses main Game class to access and alter information in other classes
     */
    protected Game G;
    /**
     * Current x position of Mouse
     */
    public double x;
    /**
     * Current y position of Mouse
     */
    public double y;
    /**
     * Has the mouse been clicked in the last 70 milliseconds or less
     */
    public boolean mouseClicked = false;

    /**
     * Scrolls through messages from the bot when the scroll wheel is turned.
     *
     * @param e Listens to when the scroll wheel is moved by the user.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) { 
        if (D.gameMode > 4 && D.gameMode < 9) {
            if (G.botSpeech.length - D.botSpeechDisplay.length > 0) {
                if (D.botSpeechDisplay[D.botSpeechDisplay.length - 1] < G.botSpeech.length - 1 && e.getWheelRotation() == -1) {
                    for (int i = 0; i < D.botSpeechDisplay.length; i++) {
                        D.botSpeechDisplay[i] += 1;
                    }
                } else if (D.botSpeechDisplay[0] > 0 && e.getWheelRotation() == 1) {
                    for (int i = 0; i < D.botSpeechDisplay.length; i++) {
                        D.botSpeechDisplay[i] -= 1;
                    }

                }
            }
        }
    }

    /**
     * Updates the current position of the mouse.
     *
     * @param e Listens to when the mouse is moved by the user.
     */
    @Override
    public void mouseMoved(MouseEvent e) { 
        if (mouseClicked == false) {
            x = e.getX(); //change the position of the card to wherever the  mouse has just clicked
            y = e.getY();
        }
    }

    /**
     * Responds to user input from clicking cards and buttons.
     *
     * @param e Listens to when the mouse clicked (released) by the user.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (D.run == true) {
            mouseClicked = true;
        }
        try {Thread.sleep(70);} catch (InterruptedException z) {}
        mouseClicked = false;
    }

}
