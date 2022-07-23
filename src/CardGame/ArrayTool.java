package CardGame;
/**
 * The ArrayTool interface stores every function of each array tool to
 * organize and manipulate a variety of array types.
 * @author Sarah Simionescu
 * @version 1
 */
public interface ArrayTool {
    /**
     * Removes the card from the given index.
     * @param arr The array of cards to be manipulated.
     * @param index The index of the card to be deleted.
     * @return Returns a new array without the card at the given index.
     */
    public Card[] delete(Card arr[], int index);
    
    /**
     * Adds a card at a given index.
     * @param arr The array of cards to be manipulated.
     * @param index The index of where the card will be added.
     * @param newCard The new Card being added.
     * @return Returns a new array with the newCard inserted at the given index.
     */
    public Card[] insert(Card newCard, Card arr[], int index); //adds a card at the given index
    
    /**
     * Sorts the cards.
     * @param arr The array of cards to be sorted.
     * @return The array of cards sorted by value and suit.
     */
    public Card[] sort(Card arr[]);
    
    /**
     * Finds the index given a card in an array of Cards.
     * @param arr The array of cards to be checked.
     * @param v The value of the card being searched for.
     * @param s The suit of the card being searched for.
     * @return The index where the given card has been found.
     */
    public Integer find(Card arr[], int v, char s); //
    
    /**
     * Quickly finds the index given a card in a sorted array of Cards.
     * @param arr The array of cards to be checked.
     * @param low The lowest index in the (sub)array.
     * @param high The highest index in the (sub)array.
     * @param c The card being searched for.
     * @return The index where the given card has been found.
     */
    public Integer binaryFind(Card[] arr, int low, int high, Card c);
    
    /**
     * Shuffles an array of Cards.
     * @param arr The array of cards to be manipulated.
     * @return Returns the shuffled array of cards.
     */
    public Card[] shuffle(Card arr[]); //
    
    /**
     * Finds the indexes of Card with a specified value.
     * @param arr The array of cards to be checked.
     * @param v The value of the cards being searched for.
     * @return Returns an array containing the indexes of the cards with the given value.
     */
    public int[] findIndexes(Card[] arr, int v);
    
    /**
     * Finds the indexes of integers with a specified value.
     * @param arr The array of integers to be checked.
     * @param v The value of the integers being searched for.
     * @return Returns an array containing the indexes of the integers with the given value.
     */
    public int[] findIndexesInt(int[] arr, int v);
    
    /**
     * Adds a given integer at a given index.
     * @param arr The array of integers to be manipulated.
     * @param index The index of where the integer will be added.
     * @param newi The new integer being added.
     * @return Returns a new array with the integer inserted at the given index.
     */
    public int[] insertInt(int newi, int[] arr, int index);
    
    /**
     * Removes an integer at the given index.
     * @param arr The array of integers to be manipulated.
     * @param index The index of the integer to be deleted.
     * @return Returns a new array without the integer at the given index.
     */
    public int[] deleteInt(int[] arr, int index);
    
    /**
     * Adds a given String at a given index.
     * @param arr The array of Strings to be manipulated.
     * @param index The index of where the new String will be added.
     * @param newString The new String being added.
     * @return Returns a new array with the String inserted at the given index.
     */
    public String[] insertString(String newString, String[] arr, int index);
    
     /**
     * Removes a String at the given index.
     * @param arr The array of Strings to be manipulated.
     * @param index The index of the String to be deleted.
     * @return Returns a new array without the String at the given index.
     */
    public String[] deleteString(String[] arr, int index);

}
