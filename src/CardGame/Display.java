package CardGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * The Display class manages graphics and and displays the user interface on its
 * own thread parallel to the Game class.
 * @author Sarah Simionescu
 * @version 1
 */
public class Display extends Thread {
    /**
     * Has the display finished initializing?
     */
    boolean initialized = false;
    /**
     * Accesses main Game class to display user interface
     */
    protected Game G;
    /**
     * Whether or not the display is being updated
     */
    boolean run = true;
    /**
     * Image displaying the graphics of the user interface
     */
    protected Image dbImage;
    /**
     * Metrics used when aligning and printing text
     */
    public FontMetrics metrics;
    private Graphics dbg;
    /**
     * An array of images to store the graphics of all playing cards
     */
    public Image[] cardGraphic;
    /**
     * A place holder Image Icon used to download and resize images
     */
    public ImageIcon myIcon;
    /**
     * A class responding to user input from the mouse
     */
    public Mouse mouse;
    /**
     * specifies which screen of the game to display
     */
    int gameMode;
    /**
     * The indexes of which messages are currently being displayed to the user
     */
    public int[] botSpeechDisplay;
    
    /**
     * Initializes the user interface in parallel thread to the game's functions.
     */
    @Override
    public void run() {
        DisplayScreen displayScreen = new DisplayScreen();
    }

    /**
     * The DisplayScreen class supports the Display class by painting graphics
     * that can only be done through JFrame.
     *
     * @author Sarah Simionescu
     * @version 1
     */
    public class DisplayScreen extends JFrame {
        
        /**
         * Initializes all needed components for user interface, then states
         * within a variable that initialization has been completed.
         */
        public DisplayScreen() {
            init();
            initialized = true;
        }

        /**
         * Initializes a class to react to user input from the mouse, and a
         * window on the screen in which the graphics will be displayed.
         */
        public void init() {
            run = false;
            loadGraphics(); //loads graphics of a standard deck
            mouse = new Mouse();
            mouse.G = G;
            mouse.D = G.D;
            addMouseMotionListener(mouse);
            addMouseListener(mouse);
            addMouseWheelListener(mouse);
            windowManager();
        }

        /**
         * Initializes a window on the screen in which the graphics will be
         * displayed.
         */
        public void windowManager() {
            //JFrame f = new JFrame();
            setTitle("Engine");
            setVisible(true);
            setResizable(false);
            setSize(1024, 768);
            setBackground(new Color(50, 155, 50));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        /**
         * Initializes an image where graphics will be created and displayed.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        @Override
        public void paint(Graphics g) {
            dbImage = createImage(getWidth(), getHeight());
            dbg = dbImage.getGraphics();
            paintComponent(dbg);
            g.drawImage(dbImage, 0, 0, this);
        }
        
        /**
         * Paints all graphics for each game screen onto the user interface.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintComponent(Graphics g) {
            if (run == true) {
                if (gameMode == 0 || gameMode == 1) { //main menu and sorting tests menu
                    paintMenu(g);
                    paintDecor(g);
                } else if (gameMode == 2) { //sorting tests results
                    for (int i = 0; i < G.testResults.length; i++) {
                        paintText(String.valueOf(((double) G.testResults[i]) / 1000) + "s", i * 250 + 250 + 75, 700, 40, g);
                        if (G.testResults[i] == 0) {
                            paintText("*less than 1 millisecond*", i * 250 + 250 + 75, 730, 20, g);
                        }
                        int max = 0;
                        for (int j = 0; j < G.testResults.length; j++) {
                            if (G.testResults[j] > max) {
                                max = (int) G.testResults[j];
                            }
                        }
                        int h = map((int) G.testResults[i], 0, max, 0, 250);
                        g.setColor(new Color(85, 172, 194));
                        g.fillRect(i * 250 + 250, 600 - h, 150, h);
                        G.decor[i + 1].ypos = 370 - h;
                        if (i == 2) {
                            G.decor[i + 1].ypos += 35;
                        }
                    }
                    paintMenu(g);
                    paintText("Bubble Sort", 0 * 250 + 250 + 75, 650, 30, g);
                    paintText("Insertion Sort", 1 * 250 + 250 + 75, 650, 30, g);
                    paintText("Quick Sort", 2 * 250 + 250 + 75, 650, 30, g);

                    paintDecor(g);
                } else if (gameMode == 3) { //sorting method menu
                    paintMenu(g);
                    paintDecor(g);
                    paintText("Select Sorting Method", 520, 390, 50, g);
                } else if (gameMode == 4) { //game difficulty menu
                    paintMenu(g);
                    paintDecor(g);
                    paintText("Select Game Difficulty", 520, 390, 50, g);
                } else if (gameMode == 5 || gameMode == 6 || gameMode == 7) { //go fish game screens
                    paintDecor(g);
                    paintBotSpeech(g);
                    paintScores(g);
                    paintHand(G.user.hand, 65, 1024 - 65, 576 - 100 + 50, g);
                    paintHand(G.bot.hand, 512, 1024 - 65, 192 - 100 - 50, g);
                    paintDeck(g);
                } else if (gameMode == 8) { //game over
                    paintDecor(g);
                    paintBotSpeech(g);
                    paintScores(g);
                    paintMenu(g);
                } 
            }else //animated loading screen
            {
                paintText("Loading" + loadingDots(), 520, 390, 50, g);
            }
            repaint();
        }
        
        /**
         * Creates an animated string of dots when printed continuously, to
         * animate the loading screen.
         * 
         * @return Returns a string of dots based on how many milliseconds have
         * passed.
         */
        public String loadingDots() {
            int n = (int) (System.currentTimeMillis() / 1000);
            n = n%3;
            String dot = ".";
            for(int i = 0; i < n; i++)
            {
                dot += ".";
            }
            return dot;
        }

        /**
         * Draws the bot's messages.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintBotSpeech(Graphics g) {
            for (int i = 0; i < botSpeechDisplay.length; i++) {
                paintText(G.botSpeech[botSpeechDisplay[i]], 300, i * -20 + 210, 20, g);
            }
            if (G.botSpeech.length - botSpeechDisplay.length > 0) {
                paintText("*scroll to read more*", 360, 240, 15, g);
            }

        }

        /**
         * Draws the scores.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintScores(Graphics g) {
            paintText("You have " + G.user.books + " books", 340, 450 + 10, 20, g);
            paintText(G.bot.name + " has " + G.bot.books + " books", 340, 450 - 10, 20, g);
        }

        /**
         * Draws the game deck.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintDeck(Graphics g) {
            if (G.gameDeck.length > 0) {
                paintCard(G.deck, g);
                if (gameMode == 7 && checkCard(G.deck) == true) {
                    g.drawRect(G.deck.x, G.deck.y, 130, 200);
                }
            }
        }

        /**
         * Draws cards of each player.
         *
         * @param c The hand or deck cards to be displayed.
         * @param min The x position of the first card.
         * @param max The x position of the last card.
         * @param y The y position of the cards.
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintHand(Card[] c, int min, int max, int y, Graphics g) {

            for (int i = 0; i < c.length; i++) {
                c[i].x = map(i, 0, c.length, min, max) - 20;
                c[i].y = y;
                c[i].isVisible = true;
                paintCard(c[i], g);
            }
            if (gameMode == 5 && c == G.user.hand) {
                boolean highlight = false;
                for (int i = c.length - 1; i > -1; i--) {
                    g.setColor(Color.blue);
                    if (checkCard(c[i]) == true && highlight == false) {
                        g.drawRect(c[i].x, c[i].y, 130, 200);
                        highlight = true;
                    } else {
                    }

                }
            }
        }

        /**
         * Draws the menu.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintMenu(Graphics g) {
            for (int i = 0; i < G.menu.length; i++) {
                paintButton(G.menu[i], g);
            }
        }

        /**
         * Draws the extra graphics to beautify user interface.
         *
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintDecor(Graphics g) {
            for (int i = 0; i < G.decor.length; i++) {
                g.drawImage(G.decor[i].image, G.decor[i].xpos, G.decor[i].ypos, this);
            }
        }

        /**
         * Draws strings of text to be displayed.
         *
         * @param text The String of text to be displayed.
         * @param xpos The x position of the text.
         * @param ypos The y position of the text.
         * @param size The size of the text.
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintText(String text, int xpos, int ypos, int size, Graphics g) {
            g.setFont(new Font("bold", Font.PLAIN, size));
            metrics = g.getFontMetrics(new Font("bold", Font.PLAIN, size));
            int width = metrics.stringWidth(text);
            g.setColor(Color.white);
            g.drawString(text, (xpos - width / 2) + (width - metrics.stringWidth(text)) / 2, ypos);
        }

        /**
         * Draws a button.
         *
         * @param b The button to be displayed.
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintButton(Button b, Graphics g) {
            g.setFont(new Font("bold", Font.PLAIN, b.size));
            metrics = g.getFontMetrics(new Font("bold", Font.PLAIN, b.size));
            b.width = metrics.stringWidth(b.text) + metrics.getAscent();
            b.height = ((metrics.getHeight()) / 2) + metrics.getAscent();
            if (checkButton(b) == true) {
                g.setColor(b.buttonColor.brighter());
            } else {
                g.setColor(b.buttonColor);
            }
            g.fillRect(b.xpos - b.width / 2, b.ypos - b.height / 2, b.width, b.height);
            g.setColor(Color.white);
            g.drawRect(b.xpos - b.width / 2, b.ypos - b.height / 2, b.width, b.height);
            g.setColor(b.textColor);
            g.drawString(b.text, (b.xpos - b.width / 2) + (b.width - metrics.stringWidth(b.text)) / 2, b.ypos - b.height / 2 + ((b.height - metrics.getHeight()) / 2) + metrics.getAscent());
        }

        /**
         * Draws a card.
         *
         * @param c The card to be displayed.
         * @param g The graphics tool used to draw and display the user
         * interface.
         */
        public void paintCard(Card c, Graphics g) {
            if (c.isVisible == true) { //checks if card should currently be visable on screen
                if (c.isFlipped == true) { //check if front or back of card is visable
                    g.drawImage(cardGraphic[c.imageIndex], c.x, c.y, this);//draws given card at it's position
                } else {
                    g.drawImage(cardGraphic[52], c.x, c.y, this);//draws the back of card at it's position
                }
            }
        }

        /**
         * Checks if the position of the mouse is currently on a given card.
         *
         * @param c The card currently displayed on the user interface.
         * @return Returns true or false stating if the mouse is currently on
         * the card.
         */
        public boolean checkCard(Card c) {
            if (mouse.x > c.x && mouse.x < c.x + 130 && mouse.y > c.y && mouse.y < c.y + 200) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Checks if the position of the mouse is currently on a given button.
         *
         * @param b The button currently displayed on the user interface.
         * @return Returns true or false stating if the mouse is currently on
         * the button.
         */
        public boolean checkButton(Button b) {
            if (mouse.x < b.xpos + b.width / 2 && mouse.x > b.xpos - b.width / 2 && mouse.y < b.ypos + b.height / 2 && mouse.y > b.ypos - b.height / 2) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * Maps a position from one range into another range.
         *
         * @param x The initial position.
         * @param imin The minimum of the initial range.
         * @param imax The minimum of the initial range.
         * @param fmin The minimum of the new range.
         * @param fmax The maximum of the new range.
         * @return Returns the position of x within the new range.
         */
        public int map(int x, int imin, int imax, int fmin, int fmax) {
            if (imax - imin == 0) {
                return 0;
            } else {
                double irange = imax - imin;
                double frange = fmax - fmin;
                double answer = x * ((frange) / (irange)) + fmin;
                return (int) answer;
            }
        }

        /**
         * Downloads and stores images for each card.
         */
        public void loadGraphics() {
            cardGraphic = new Image[53];
            int j = 2;
            for (int i = 0; i < 36; i = i + 4) {
                myIcon = new ImageIcon("PNG/" + j + "C" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + j + "D" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 1] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + j + "H" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 2] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + j + "S" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 3] = myIcon.getImage();
                j++;
            }
            String[] Faces = new String[4];
            Faces[0] = "J";
            Faces[1] = "Q";
            Faces[2] = "K";
            Faces[3] = "A";
            j = 0;
            for (int i = 36; i < 52; i = i + 4) {
                myIcon = new ImageIcon("PNG/" + Faces[j] + "C" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + Faces[j] + "D" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 1] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + Faces[j] + "H" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 2] = myIcon.getImage();
                myIcon = new ImageIcon("PNG/" + Faces[j] + "S" + ".png");
                myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
                cardGraphic[i + 3] = myIcon.getImage();
                j++;
            }
            myIcon = new ImageIcon("PNG/blue_back.png");
            myIcon = new ImageIcon(getScaledImage(myIcon.getImage(), 130, 200));
            cardGraphic[52] = myIcon.getImage();

        }

        /**
         * Resizes an image.
         *
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
}
