package CardGame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
/**
 * The Decor class describes the appearance of graphics to be displayed onto the
 * user interface to beautify the user interface.
 * @author Sarah Simionescu
 * @version 1
 */
public class Decor {
    /** name of the .png file of the graphic stored in the source files of the program*/
    String fileName;
    /** x position of the graphic*/
    int xpos;
    /** y position of the graphic*/
    int ypos;
    /** width of the graphic*/
    int sizeWidth;
    /** height of the graphic*/
    int sizeHeight;
    /** Icon where the graphic will be downloaded*/
    ImageIcon myIcon;
    /** Image where the graphic will be stored*/
    Image image;
    
    /**
     * Initializes the decor, downloads and stores the graphic, saves the width
     * and height of the graphic, adjusts the x and y position to center the graphic.
     * @param x X position of the button.
     * @param y Y position of the button.
     * @param f Name of the .png file of the graphic stored in the source files of the program.
     */
    public Decor(int x, int y, String f) {
        myIcon = new ImageIcon("PNG/" + f + ".png");
        image = myIcon.getImage();
        sizeWidth = myIcon.getIconWidth();
        sizeHeight = myIcon.getIconHeight();
        xpos = x - sizeWidth / 2;
        ypos = y - sizeHeight / 2;
    }
    
    /**
     * Resizes the graphic and re-centers the image to its position.
     * @param sw New width of graphic.
     * @param sh New height of graphic.
     */
    public void resize(int sw, int sh) {
        myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), sw, sh)); //set the icon to the resized image
        image = myIcon.getImage(); //save the resized image
        xpos += sizeWidth / 2; //recenter the new image
        ypos += sizeHeight / 2;
        sizeWidth = myIcon.getIconWidth();
        sizeHeight = myIcon.getIconHeight();
        xpos -= sizeWidth / 2;
        ypos -= sizeHeight / 2;
    }
    
    /**
     * Resizes an image.
     * @param srcImg Image to be resized.
     * @param w New width of image.
     * @param h New height of image.
     * @return Returns the resized image.
     */
    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

}
