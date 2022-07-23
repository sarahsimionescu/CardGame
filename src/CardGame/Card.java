package CardGame;
/**
 * The Card class stores the position, value, suit of a card, describes its
 * image and can compare itself to another card.
 * @author Sarah Simionescu
 * @version 1
 */
public class Card implements Comparable<Card> { //class card references the "Comparable", a built in interface
    /** x and y position of card*/
    public int x, y;
    /** Whether or not the card is visible on the screen*/
    public boolean isVisible;
    /** number value on card (jacks=11, queens=12, kings=13, aces=12)*/
    public int value;
    /** letter representing suit of card (hearts = H, clubs = C, spades = S, diamonds = D)*/
    public char suit;
    /** index relating to an image of a card stored in the CardGame class*/
    public int imageIndex;
    /** whether or not the card is face up or down*/
    public boolean isFlipped;
    
    /**
     * Initializes the card.
     * @param posX X position of the card.
     * @param posY Y position of the card.
     * @param v Number value on card (jacks=11, queens=12, kings=13, aces=12).
     * @param s Letter representing suit of card (hearts = H, clubs = C, spades = S, diamonds = D).
     * @param i Index relating to an image of a card stored in the CardGame class.
     */
    public Card(int v, char s, int posX, int posY, int i) {
        x = posX;
        y = posY;
        isVisible = true;
        isFlipped = true;
        value = v;
        suit = s;
        imageIndex = i;
    }
    
    /**
     * Compares this card to another card.
     * @param o The card to be compared to.
     * @return Returns whether or not the card would be organized in front or
     * behind the given card based on it's value and suit.
     */
    @Override
    public int compareTo(Card o) { //a function installed from the comparable interface to compare "this" card to another card
        if (this.value > o.value) { //if value of this card is larger than the value of the other card
            return 1; //return positive
        } else if (this.value < o.value) { //if the value of this card is less than the value of the other card
            return -1; //return negative
        } else if ((int) this.suit > (int) o.suit) {
            return 1;
        } else if ((int) this.suit < (int) o.suit) {
            return -1;
        } else { //if the value of both cards are equal to eachother
            return 0; //return neutral
        }

    }
}
