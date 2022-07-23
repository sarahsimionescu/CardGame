package CardGame;
import java.awt.Color;
/**
 * The CardGame class manages all classes and functions of the game and sorting
 * tests, and initiates the user interface for each game screen.
 * @author Sarah Simionescu
 * @version 1
 */
public class Game extends Thread {
    
    /**
     * Accesses Display class to manage user interface
     */
    protected Display D;
    /**
     * An array storing the main deck of cards for Go Fish
     */
    public Card[] gameDeck;
    /**
     * A card displayed to represent the gameDeck and store the value of the
     * card more recently asked for
     */
    public Card deck;
    /**
     * A class managing all the actions of the user in Go Fish
     */
    public PlayerUser user;
    /**
     * A class managing all the actions of the bot in Go Fish
     */
    public PlayerBot bot;
    /**
     * An array list of each type of Array Tool, each uses a different sorting
     * method
     */
    public ArrayTool[] arrayTool;
    /**
     * An array of buttons to be displayed for he user interface
     */
    public Button[] menu;
    /**
     * An array of additional images to beautify the user interface
     */
    public Decor decor[];
    /**
     * A string of messages from the bot to the user
     */
    public String[] botSpeech;
    /**
     * The time it took for each sorting method to sort a specified deck of
     * cards
     */
    public long testResults[];
    /**
     * The sorting method chosen by the user, to be used when playing Go Fish
     */
    public int sortMethod;
    
    /**
     * Launches the Game on its own thread parallel to the display component,
     * creates each type of array tool, and launches main menu after display has
     * finished initializing.
     */
    @Override
    public void run() {
        arrayTool = new ArrayTool[3]; //creates an array of each type of sorting method
        arrayTool[0] = new BubbleArrayTool();
        arrayTool[1] = new InsertionArrayTool();
        arrayTool[2] = new QuickArrayTool();
        
        while (D.initialized == false) { //wait until the display class is initialized, and ready to display graphics
            pause(50);
        }
        mainMenu(); //initiates main menu
    }
    
     /**
     * Pauses current thread for a given amount of time.
     *
     * @param v Number of milliseconds for pause.
     */
    public void pause(long v) {
        try {
            Thread.sleep(v);
        } catch (InterruptedException e) {
        }
    }
    
     /**
     * Waits for user to click the mouse, then responds to user input accordingly
     * based on which game screen in currently being displayed.
     *
     * @param g The game screen currently being displayed.
     */
    public void userInput(int g) {
        System.out.println(g);
        D.gameMode = g;
        D.run = true;
        D.mouse.mouseClicked = false;
        if (g == 0) {
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            
            if (checkMenu() == 0) { //main menu
                sortMethodMenu();
            } else if (checkMenu() == 1) {
                sortTestMenu();
            }
        } else if (g == 1) { //sorting tests menu
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            
            if (checkMenu() == 3) { 
                mainMenu();
            } else {
                sortTest(52 * (int) Math.pow(10, checkMenu() * 2));
            }

        } else if (g == 2) { //sorting tests results
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            
            sortTestMenu();
        } else if (g == 3) { //sorting method menu
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            
            if (checkMenu() == 3) {
                mainMenu();
            } else {
                sortMethod = checkMenu();
                difficultyMenu();
            }
        } else if (g == 4) { //game difficulty menu
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            
            if (checkMenu() == 3) {
                sortMethodMenu();
            } else {
                bot.memoryCap = checkMenu() * 8 + 1;
                if (checkMenu() == 0) {
                    bot.name = "Goldfish";
                } else if (checkMenu() == 1) {
                    bot.name = "Flounder";
                } else if (checkMenu() == 2) {
                    bot.name = "Dolphin";
                }
                newGame();
            }
        } else if (g == 5) { //user turn to pick a card to ask for

            while (D.mouse.mouseClicked == false || checkHand(user.hand) == null) {
                pause(50);
            }
            
            clearBotSpeak();
            user.ask(user.hand[checkHand(user.hand)].value);
        } else if (g == 6) { //bot turn
            
        } else if (g == 7) { //user go fish
            while (D.mouse.mouseClicked == false || checkCard(deck) == false) {
                pause(50);
            }
            
            clearBotSpeak();
            user.goFish(deck.value);
        } else if (g == 8) { //game over
            while (D.mouse.mouseClicked == false || checkMenu() == null) {
                pause(50);
            }
            mainMenu();
        }
    }

    /**
     * Initializes all the buttons and graphics needed to display the main menu.
     */
    public void mainMenu() {
        D.run = false;
        menu = new Button[2]; //initializes buttons for the main menu
        menu[0] = new Button(512, 500, 40, "New Game", new Color(85, 172, 194));
        menu[1] = new Button(512, 600, 40, "Test Sorting Methods", new Color(85, 172, 194));
        decor = new Decor[1]; //loads go fish title
        decor[0] = new Decor(512, 250, "gofishtitle");
        userInput(0);
    }

    /**
     * Initializes all the buttons and graphics needed to display the sorting
     * tests menu.
     */
    public void sortTestMenu() {
        D.run = false;
        menu = new Button[4]; //initializes buttons for different tests
        menu[0] = new Button(512, 300, 40, "52 cards", new Color(85, 172, 194));
        menu[1] = new Button(512, 400, 40, "5200 cards", new Color(85, 172, 194));
        menu[2] = new Button(512, 500, 40, "520 000 cards", new Color(85, 172, 194));
        menu[3] = new Button(100, 700, 40, "Back", new Color(85, 172, 194)); //initializes back button
        decor = new Decor[1]; //loads test sorting methods graphic
        decor[0] = new Decor(512, 140, "testsortingmethods");
        userInput(1);
    }

    /**
     * Conducts a sorting test, then initializes all the buttons, graphics and
     * information needed to display the sorting test results.
     *
     * @param n The number of cards to be sorted in the test.
     */
    public void sortTest(int n) {
        D.run = false;
        Card originalDeck[] = Deck(); //loads a standard deck of cards
        menu = new Button[1];
        menu[0] = new Button(100, 700, 40, "Back", new Color(85, 172, 194)); //initializes back button

        decor = new Decor[4]; //displays title and fishies
        decor[0] = new Decor(512, 140, "testsortingmethods");
        decor[1] = new Decor(327, 520, "bubblefish");
        decor[1].resize(300, 300);
        decor[2] = new Decor(250 + 327, 520, "selectionfish");
        decor[2].resize(300, 300);
        decor[3] = new Decor(500 + 327, 520 + 35, "quickfish");
        decor[3].resize(300, 300);

        Card[][] testDeck = new Card[3][n]; //creates three identical test decks to be sorted by each type of sorting method
        for (int i = 0; i < n; i++) { //filling those decks with random cards from the standard deck
            int c = (int) (Math.random() * 52);
            for (int j = 0; j < 3; j++) {
                testDeck[j][i] = originalDeck[c];
            }
        }
        testResults = new long[3]; //creates space to store each time it took to complete each test
        for (int i = 0; i < arrayTool.length; i++) { //tests each sorting method and records time it took to complete each test
            long timer = System.currentTimeMillis();
            arrayTool[i].sort(testDeck[i]);
            testResults[i] = (System.currentTimeMillis() - timer);
        }
        userInput(2);
    }

    /**
     * Initializes all the buttons and graphics needed to display the sorting
     * method selection menu and initializes the classes in charge of managing
     * player actions.
     */
    public void sortMethodMenu() {
        D.run = false;
        user = new PlayerUser();
        user.G = this;
        bot = new PlayerBot();
        bot.G = this;
        menu = new Button[4]; //initializes buttons for each type of sorting method
        menu[0] = new Button(512, 450, 40, "Bubble Sort", new Color(85, 172, 194));
        menu[1] = new Button(512, 550, 40, "Insertion Sort", new Color(85, 172, 194));
        menu[2] = new Button(512, 650, 40, "Quick Sort", new Color(85, 172, 194));
        menu[3] = new Button(100, 700, 40, "Back", new Color(85, 172, 194)); //initializes back button

        decor = new Decor[1]; //loads new game title
        decor[0] = new Decor(512, 175, "newgame");
        decor[0].resize((int) (1200 * 0.6), (int) (670 * 0.6));
        
        userInput(3);
        
    }

    /**
     * Initializes all the buttons and graphics needed to display the difficulty
     * selection menu.
     */
    public void difficultyMenu() {
        D.run = false;
        menu = new Button[4]; //initializes buttons for each difficulty level
        menu[0] = new Button(512, 450, 40, "Goldfish", new Color(85, 172, 194));
        menu[1] = new Button(512, 550, 40, "Flounder", new Color(85, 172, 194));
        menu[2] = new Button(512, 650, 40, "Dolphin", new Color(85, 172, 194));
        menu[3] = new Button(100, 700, 40, "Back", new Color(85, 172, 194));//initializes back button

        decor = new Decor[1];//loads new game title
        decor[0] = new Decor(512, 175, "newgame");
        decor[0].resize((int) (1200 * 0.6), (int) (670 * 0.6));
        
        userInput(4);
        
    }

    /**
     * Initializes, shuffles and deals cards from the game deck to each player
     * initializes the messaging system, chooses at random which player goes
     * first and initializes all the buttons and graphics needed to display the
     * game.
     */
    public void newGame() {
        D.run = false;
        gameDeck = Deck(); //loads a standard deck of cards to be used as the game deck
        botSpeech = new String[0]; //resets bot messaging system
        D.botSpeechDisplay = new int[0];
        arrayTool[sortMethod].shuffle(gameDeck); //sorts the game deck
        for (int i = 0; i < gameDeck.length; i++) { //sets all the cards in the game deck to be flipped and invisable
            gameDeck[i].isVisible = false;
            gameDeck[i].isFlipped = false;
        }
        deck = new Card(2, 'H', 512 - 65, 384 - 100, 52); //creates a card to represent the game deck on the user interface
        for (int i = 0; i < 5; i++) { //deal 5 cards to the user
            user.hand = arrayTool[sortMethod].insert(gameDeck[0], user.hand, 0);
            user.hand[0].isFlipped = true;
            gameDeck = arrayTool[sortMethod].delete(gameDeck, 0);
        }
        for (int i = 0; i < 5; i++) { //deal 5 cards to the bot
            bot.hand = arrayTool[sortMethod].insert(gameDeck[0], bot.hand, 0);
            gameDeck = arrayTool[sortMethod].delete(gameDeck, 0);
        }
        decor = new Decor[2]; 
        decor[0] = new Decor(150, 384, bot.name); //load a photo of the bot (goldfish, flounder or dolphin)
        decor[0].resize(250, 250);
        decor[1] = new Decor(300, 150, "speechbubble"); //loads a photo of the speech bubble
        decor[1].resize(300, 300);
        int firstPlayer = (int) (Math.random() * 2); //chooses a random player to go first
        if (firstPlayer == 0) {
            userTurn();
        } else {
            botTurn();
        }
    }

    /**
     * Manages cards and begins the user's turn, allowing the user to select a
     * card from their deck to ask for.
     */
    public void userTurn() {
        if (D.gameMode != 8) {
            organizeHands();
            while (gameDeck != null && gameDeck.length > 0 && user.hand.length == 0) {
                user.hand = arrayTool[sortMethod].insert(gameDeck[0], user.hand, 0);
                gameDeck = arrayTool[sortMethod].delete(gameDeck, 0);
                user.hand[0].isFlipped = true;
                botSpeak("You ran out of cards.");
                botSpeak("You got a card from the deck.");
                organizeHands();
            }
            if (D.gameMode != 8) {
                botSpeak("Select a card to ask for.");
                userInput(5);  
            }
        }
    }

    /**
     * Manages cards and begins the bot's turn.
     */
    public void botTurn() {
        if (D.gameMode != 8) { 
            userInput(6);
            organizeHands();
            while (gameDeck != null && gameDeck.length > 0 && bot.hand.length == 0) {
                bot.hand = arrayTool[sortMethod].insert(gameDeck[0], bot.hand, 0);
                bot.hand[0].isFlipped = false;
                gameDeck = arrayTool[sortMethod].delete(gameDeck, 0);
                botSpeak("I ran out of cards.");
                botSpeak("I got a card from the deck.");
                organizeHands();
            }
            if (D.gameMode != 8) {
                bot.ask(0);
            }
        }
    }
    
     /**
     * Clears the bot's messages.
     */
    public void clearBotSpeak() {
        botSpeech = new String[0];  
    }
    
     /**
     * Creates and stores messages from the bot to the user.
     * @param speech The message from the bot to the user.
     */
    public void botSpeak(String speech) {
        botSpeech = arrayTool[sortMethod].insertString(speech, botSpeech, 0);
        if (botSpeech.length < 7) {
            D.botSpeechDisplay = new int[botSpeech.length];
            for (int i = 0; i < botSpeech.length; i++) {
                D.botSpeechDisplay[i] = i;
            }
        } else {
            D.botSpeechDisplay = new int[7];
            for (int i = 0; i < 7; i++) {
                D.botSpeechDisplay[i] = i;
            }
        }
        organizeHands();
        pause(1000);
    }
    
    /**
     * Stores the card more recently asked for, then asks the user to click the
     * deck to go fish.
     * @param v The value of the card most recently asked for.
     */
    public void goFish(int v) {
        if (D.gameMode != 8) {
            deck.value = v; //secretly store v as the value deck
            botSpeak("Click the deck to go fish!");
            userInput(7);
        }
    }

    /**
     * Initializes all the buttons, graphics and messages needed to display the
     * game over screen.
     */
    public void gameOver() {
        D.run = false;
        menu = new Button[1];
        menu[0] = new Button(512, 600, 40, "Play Again", new Color(85, 172, 194));
        userInput(8);
        if (bot.books < user.books) {
            botSpeak("You won!");
            botSpeak("You finished with " + user.books + " books.");
            botSpeak("I finsihed with " + bot.books + " books.");
            botSpeak("Congratulations!");
        } else if (bot.books == user.books) {
            botSpeak("It's a tie!");
            botSpeak("You finished with " + user.books + " books.");
            botSpeak("I finsihed with " + bot.books + " books.");
            botSpeak("Good game!");
        } else {
            botSpeak("You lost!");
            botSpeak("You finished with " + user.books + " books.");
            botSpeak("I finished with " + bot.books + " books.");
            botSpeak("Better luck next time!");
        }
    }

    /**
     * Sorts the cards for each user, checks if they have created any books,
     * and checks to see if the deck is empty and the game has ended.
     */
    public void organizeHands() {
        if (user.hand.length != 0) {
            user.hand = arrayTool[sortMethod].sort(user.hand);
            user.checkBook();
        }

        if (bot.hand.length != 0) {
            bot.hand = arrayTool[sortMethod].sort(bot.hand);
            bot.checkBook();
        }
        if (gameDeck.length == 0 && bot.hand.length == 0 && user.hand.length == 0 && bot.books + user.books == 13) {
            gameOver();
        }
    }
    
    /**
     * Checks which card is the mouse currently positioned on.
     * @param c The hand currently displayed on the user interface.
     * @return Returns the index of the card currently selected.
     */
    public Integer checkHand(Card[] c) {
        for (int i = c.length - 1; i > -1; i--) {
            if (checkCard(c[i]) == true) {
                return i;
            }
        }
        return null;
    }

    /**
     * Checks if the position of the mouse is currently on a given card.
     * @param c The card currently displayed on the user interface.
     * @return Returns true or false stating if the mouse is currently on the card.
     */
    public boolean checkCard(Card c) {
        if (D.mouse.x > c.x && D.mouse.x < c.x + 130 && D.mouse.y > c.y && D.mouse.y < c.y + 200) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks if the position of the mouse is currently on a given button.
     * @param b The button currently displayed on the user interface.
     * @return Returns true or false stating if the mouse is currently on the button.
     */
    public boolean checkButton(Button b) {
        if (D.mouse.x < b.xpos + b.width / 2 && D.mouse.x > b.xpos - b.width / 2 && D.mouse.y < b.ypos + b.height / 2 && D.mouse.y > b.ypos - b.height / 2) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Checks which button the mouse currently positioned on.
     * @return Returns the index of the button currently selected.
     */
    public Integer checkMenu() {
        for (int i = 0; i < menu.length; i++) {
            if (checkButton(menu[i])) {
                return i;
            }
        }
        return null;
    }
    
    /**
     * Creates a standard deck of cards.
     * @return Returns an array of cards representing a standard deck of 52 cards.
     */
    public Card[] Deck() {
        Card[] Deck = new Card[52];
        int j = 2;
        for (int i = 0; i < 36; i = i + 4) {
            Deck[i] = new Card(j, 'C', 0, 0, i);
            Deck[i + 1] = new Card(j, 'D', 0, 0, i + 1);
            Deck[i + 2] = new Card(j, 'H', 0, 0, i + 2);
            Deck[i + 3] = new Card(j, 'S', 0, 0, i + 3);
            j++;
        }
        j = 0;
        for (int i = 36; i < 52; i = i + 4) {
            Deck[i] = new Card(j + 11, 'C', 0, 0, i);
            Deck[i + 1] = new Card(j + 11, 'D', 0, 0, i + 1);
            Deck[i + 2] = new Card(j + 11, 'H', 0, 0, i + 2);
            Deck[i + 3] = new Card(j + 11, 'S', 0, 0, i + 3);
            j++;
        }
        return Deck;
    }

}
