
package CardGame;
/**
 * The Player interface provides all the methods for the Player classes to
 * manage all the actions of a player when playing Go Fish.
 * @author Sarah Simionescu
 * @version 1
 */
public interface Player {  
    /**
     * The player takes a card from the deck, and if they pick up the card they
     * just asked for, they take their turn again.
     * @param v The card the player most recently asked for.
     */
    public void goFish(int v);
    
    /**
     * The player takes their turn and asks the other player for a card value 
     * from their hand, and if they receive that card, they take their turn again.
     * @param v The value of the card the player is asking for, given from the user input.
     */
    public void ask(int v);
    
    /**
     * The player checks their deck to see if they have enough cards of the same
     * value to create a book, and if they do, they make a book.
     */
    public void checkBook();
    
    /**
     * When communicating with the user, the bot says "a" or "an" before stating
     * the value on the card, based on whether or not that value is eight (because it
     * starts with a vowel).
     * @param v The value of the card the bot is describing.
     * @return Returns an " a " or " an " depending on whether or not the given value is eight
     */
    public String aOrAn(int v);
    
    /**
     * When communicating with the user, the bot describes the number of each card
     * given in words.
     * @param i The number of cards the bot is describing.
     * @return Returns the word for than number.
     */
    public String numberToWord(int i);
    
    /**
     * When communicating with the user, adds an s when describing more than one
     * card being given.
     * @param v The number of cards the bot is describing.
     * @return Returns a "s" depending on whether or not the bot is describing multiple or a single card.
     */
    public String sOrNoS(int v);
    
    /**
     * When communicating with the user, the bot describes the value of each card
     * in words when the cards value is larger than 10 (Queen, Jack, King, Ace).
     * @param v The value of the cards or card the bot is describing.
     * @return Returns the a String of the common name for that card.
     */
    public String trueValue(int v);
    
}
