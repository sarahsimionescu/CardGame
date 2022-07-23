package CardGame;
/**
 * The PlayerUser interface manages all the actions of the bot when playing Go Fish.
 * @author Sarah Simionescu
 * @version 1
 */
public class PlayerBot extends PlayerUser {
    /** Accesses main CardGame to access and alter information in other classes*/
    protected Game G;
    /** The hand of cards the player is currently holding*/
    public Card[] hand;
    /** How many books the player has made*/
    public int books;
    /** How many values the bot is allowed to remember based on difficulty selected by the user*/
    public int memoryCap;
    /** Values that the user has previously asked for, asking for these values gives the bot a higher chance of taking cards and making books*/
    public int[] memory;
    /** Name of the bot based on difficulty selected by the user*/
    public String name;
    
    /**
     * Initializes the player and its hand of cards, gives it a placeholder name,
     * resets its memory and books made to zero.
     */
    public PlayerBot() {
        books = 0;
        hand = new Card[0];
        memoryCap = 0;
        memory = new int[0];
        name = "bot";
    }
    
    /**
     * Adds a value the player asked for into memory and deletes an old memory if there is not enough space.
     * @param v The card the player most recently asked for.
     */
    public void addMemory(int v) {
        while (memory.length > memoryCap) { //if the bot is remembers more values than it should
            memory = G.arrayTool[G.sortMethod].deleteInt(memory, memory.length - 1); //delete older memories
        }
        memory = G.arrayTool[G.sortMethod].insertInt(v, memory, 0); //add a new memory
    }
    
    /**
     * Randomly selects a value to ask for from deck, and based on the
     * difficulty, the bot may be inclined to ask a value from its memory when
     * possible, giving it a higher chance of taking cards and making a book.
     * @return Returns a value to ask for.
     */
    public int chooseValue() {
        G.organizeHands();
        int v = this.hand[(int) (Math.random() * this.hand.length)].value; //select a random backup value from deck
        if ((int) (Math.random() * 17) < memoryCap && memory.length > 0) //randomly choose to try and ask a card from memory (the larger the memory capacity, the more likely to use memory)
        {
            int[] availableValues = new int[0];//count the number of values from memory that are available within the bot's hand to ask for
            for (int i = 0; i < memory.length; i++) { //go through each value in memory
                if (G.arrayTool[G.sortMethod].findIndexes(this.hand, memory[i]).length > 0) { //if there are cards with that value, present in the bot's deck
                    availableValues = G.arrayTool[G.sortMethod].insertInt(memory[i], availableValues, 0); //add that value to a list of available values to choose from
                }
            }
            if (availableValues.length > 0) { //if there are values from memory available to ask for
                v = availableValues[(int) (Math.random() * availableValues.length)]; //select for a card with a value from memory
            }
        }
        return v;
    }

    /**
     * The player takes their turn and asks the other player for a card value 
     * from their hand, and if they receive that card, they take their turn again.
     * @param v is equal to zero and not used by the bot
     */
    @Override
    public void ask(int v) {
        if (G.D.gameMode != 8) { //make sure the game is not over
            v = chooseValue(); //select a value to ask for
            int foundIndex[] = G.arrayTool[G.sortMethod].findIndexes(G.user.hand, v);//how many cards the user has of selected value
            if (foundIndex.length > 0) {//if the user has the card that was asked for
                G.botSpeak("Do you have any " + trueValue(v) + "s?");
                for (int j = 0; j < foundIndex.length; j++) { //take every card with that value from user's deck, and add it to bot's
                    this.hand = G.arrayTool[G.sortMethod].insert(G.user.hand[foundIndex[j]], this.hand, 0);
                    this.hand[0].isFlipped = false;
                    G.user.hand = G.arrayTool[G.sortMethod].delete(G.user.hand, foundIndex[j]);
                }
                G.botSpeak("I took your " + numberToWord(foundIndex.length) + " " + trueValue(v) + sOrNoS(foundIndex.length));
                deleteFromMemory(v);
                G.botTurn();//it is the bot's turn again
            } else {
                G.botSpeak("Do you have any " + trueValue(v) + "s?");
                G.botSpeak("No? I will go fish.");
                this.goFish(v);//the bot goes fish
            }
        }
    }
    
    /**
     * The player takes a card from the deck, and if they pick up the card they
     * just asked for, they take their turn again.
     * @param v The card the player most recently asked for.
     */
    @Override
    public void goFish(int v) {
        if (G.D.gameMode != 8) {//make sure the game is not over
            if (G.gameDeck != null && G.gameDeck.length > 0) {//if there are cards available in the deck
                this.hand = G.arrayTool[G.sortMethod].insert(G.gameDeck[0], this.hand, 0);//add a card from the deck to user's hand
                this.hand[0].isFlipped = false;
                G.gameDeck = G.arrayTool[G.sortMethod].delete(G.gameDeck, 0); //delete that card from the deck
                if (this.hand[0].value == v) {//if the value of the card that was picked up equal to the card the bot initally asked for
                    G.botSpeak("I picked up" + aOrAn(v) + trueValue(v) + "!");
                    G.botTurn(); //it is the bot's turn again
                } else {
                    G.userTurn();//the user takes their turn
                }
            } else {
                G.gameOver();//if there are no available cards, the game is over
            }
        }
    }
    
    
    /**
     * The player checks their deck to see if they have enough cards of the same
     * value to create a book, and if they do, they make a book.
     */
    @Override
    public void checkBook() {
        for (int i = 2; i < 15; i++) {//check how many cards there are of each value
            int foundIndex[] = G.arrayTool[G.sortMethod].findIndexes(this.hand, i);
            if (foundIndex.length == 4) {//if there are four cards of the same value
                for (int j = 0; j < foundIndex.length; j++) {
                    this.hand = G.arrayTool[G.sortMethod].delete(this.hand, foundIndex[j]);//remove them from the hand to create a book
                }
                this.books++; //add the number of books the bot has
                G.botSpeak("I made a book of " + trueValue(i) + "s.");
            }
        }

    }
    
    /**
     * The player deletes from memory all integers of a specific value.
     * @param i The value which much be deleted from memory.
     */
    public void deleteFromMemory(int i) {
        while (G.arrayTool[G.sortMethod].findIndexesInt(memory, i).length > 0) { //while there are still indexes in memory holding the given value
            memory = G.arrayTool[G.sortMethod].deleteInt(memory, G.arrayTool[G.sortMethod].findIndexesInt(memory, i)[0]); //delete one
        }
    }

}
