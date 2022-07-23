package CardGame;
/**
 * The PlayerUser interface manages all the actions of the user when playing Go Fish.
 * @author Sarah Simionescu
 * @version 1
 */
public class PlayerUser implements Player {
    /** Accesses main CardGame to access and alter information in other classes*/
    protected Game G;
    /** The hand of cards the player is currently holding*/
    public Card[] hand;
    /** How many books the player has made*/
    public int books;
    
    /**
     * Initializes the player and its hand of cards, resets books made to zero.
     */
    public PlayerUser() {
        books = 0;
        hand = new Card[0];
    }
    
    /**
     * The player takes a card from the deck, and if they pick up the card they
     * just asked for, they take their turn again.
     * @param v The card the player most recently asked for.
     */
    @Override
    public void goFish(int v) {
        if (G.D.gameMode != 8) { //make sure the game is not over
            if (G.gameDeck != null && G.gameDeck.length > 0) { //if there are cards available in the deck
                this.hand = G.arrayTool[G.sortMethod].insert(G.gameDeck[0], this.hand, 0); //add a card from the deck to user's hand
                G.gameDeck = G.arrayTool[G.sortMethod].delete(G.gameDeck, 0); //delete that card from the deck
                this.hand[0].isFlipped = true;
                if (this.hand[0].value == v) { //if the value of the card that was picked up equal to the card the user initally asked for
                    G.botSpeak("You picked up" + aOrAn(v) + trueValue(v) + "!");
                    G.userTurn(); //it is the user's turn again
                } else { //otherwise
                    G.botSpeak("You picked up a card.");
                    G.botTurn(); //the bot takes their turn
                }
            } else {
                G.gameOver(); //if there are no available cards, the game is over
            }
        }
    }
    
    /**
     * When communicating with the user, the bot says "a" or "an" before stating
     * the value on the card, based on whether or not that value is eight (because it
     * starts with a vowel).
     * @param v The value of the card the bot is describing.
     * @return Returns an " a " or " an " depending on whether or not the given value is eight
     */
    @Override
    public String aOrAn(int v) {
        if (v == 8) { //if the number is 8, use "an" to be gramatically correct
            return " an ";
        } else {
            return " a ";
        }
    }
    
    /**
     * The player takes their turn and asks the other player for a card value 
     * from their hand, and if they receive that card, they take their turn again.
     * @param v The value of the card the player is asking for, given from the user input.
     */
    @Override
    public void ask(int v) {
        if (G.D.gameMode != 8) { //make sure the game is not over
            int foundIndex[] = G.arrayTool[G.sortMethod].findIndexes(G.bot.hand, v); //how many cards the bot has of selected value
            if (foundIndex.length > 0) { //if the bot has the card that was asked for
                for (int j = 0; j < foundIndex.length; j++) { //take every card with that value from bot's deck, and add it to user's
                    this.hand = G.arrayTool[G.sortMethod].insert(G.bot.hand[foundIndex[j]], this.hand, 0);
                    this.hand[0].isFlipped = true;
                    G.bot.hand = G.arrayTool[G.sortMethod].delete(G.bot.hand, foundIndex[j]);
                }
                G.botSpeak("I gave you " + numberToWord(foundIndex.length) + " " + trueValue(v) + sOrNoS(foundIndex.length));
                G.userTurn(); //it is the user's turn again
            } else { //else the bot does not have any cards of that value
                if (v > 0) {
                    G.bot.addMemory(v); //the bot remembers the value the user has in their hand
                }
                G.botSpeak("I don't have any " + trueValue(v) + "s.");
                G.goFish(v); //the user goes fish
            }
        }
    }
    
    /**
     * When communicating with the user, adds an s when describing more than one
     * card being given.
     * @param i The number of cards the bot is describing.
     * @return Returns a "s" depending on whether or not the bot is describing multiple or a single card.
     */
    @Override
    public String numberToWord(int i) {
        if (i == 1) {
            return "one";
        } else if (i == 2) {
            return "two";
        } else if (i == 3) {
            return "three";
        } else {
            return "zero";
        }
    }
    
    /**
     * The player checks their deck to see if they have enough cards of the same
     * value to create a book, and if they do, they make a book.
     */
    @Override
    public void checkBook() {
        for (int i = 2; i < 15; i++) { //check how many cards there are of each value
            int foundIndex[] = G.arrayTool[G.sortMethod].findIndexes(this.hand, i);
            if (foundIndex.length == 4) { //if there are four cards of the same value
                for (int j = 0; j < foundIndex.length; j++) {
                    this.hand = G.arrayTool[G.sortMethod].delete(this.hand, foundIndex[j]); //remove them from the hand to create a book
                }
                G.bot.deleteFromMemory(i); //delete any cards from bot memory that have been made into books
                this.books++; //add the number of books the user has
                G.botSpeak("You made a book of " + trueValue(i) + "s.");
            }

        }

    }
    
    /**
     * When communicating with the user, adds an s when describing more than one
     * card being given.
     * @param v The number of cards the bot is describing.
     * @return Returns a "s" depending on whether or not the bot is describing multiple or a single card.
     */
    @Override
    public String sOrNoS(int v) {
        if (v == 1) {
            return ".";
        } else {//else if there is more than one of that card, add an s to be gramatically correct
            return "s.";
        }
    }
    
    /**
     * When communicating with the user, the bot describes the value of each card
     * in words when the cards value is larger than 10 (Queen, Jack, King, Ace).
     * @param v The value of the cards or card the bot is describing.
     * @return Returns the a String of the common name for that card.
     */
    @Override
    public String trueValue(int v) {
        if (v >= 2 && v <= 10) {
            return String.valueOf(v);
        } else if (v == 11) {
            return "Jack";
        } else if (v == 12) {
            return "Queen";
        } else if (v == 13) {
            return "King";
        } else if (v == 14) {
            return "Ace";
        } else {
            return "null";
        }
    }

}
