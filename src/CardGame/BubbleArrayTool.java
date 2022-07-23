package CardGame;
/**
 * The BubbleArrayTool class is used to organize and manipulate a variety
 * of array types using bubble sort.
 * @author Sarah Simionescu
 * @version 1
 */
public class BubbleArrayTool implements ArrayTool {
    
    /**
     * Initializes the BubbleArrayTool.
     */
    public BubbleArrayTool() {
    }
    
    /**
     * Removes the card from the given index.
     * @param arr The array of cards to be manipulated.
     * @param index The index of the card to be deleted.
     * @return Returns a new array without the card at the given index.
     */
    @Override
    public Card[] delete(Card[] arr, int index) {
        Card[] newArr = new Card[arr.length - 1]; //creates a new deck with one less space (since we will be removing a card)
        int shift = 0; //when needed, shift the card down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each card in the current deck
        {
            if (i == index) //if we have reached the given index, do not add that card
            {
                shift = 1; //shift the following cards over
            }
            newArr[i] = arr[i + shift]; //add each card from the old deck and shift it over one space if need be
        }

        return newArr; //return the new deck
    }
    
    /**
     * Removes an integer at the given index.
     * @param arr The array of integers to be manipulated.
     * @param index The index of the integer to be deleted.
     * @return Returns a new array without the integer at the given index.
     */
    @Override
    public int[] deleteInt(int[] arr, int index) {
        int[] newArr = new int[arr.length - 1]; //creates a new array with one less space (since we will be removing a integer)
        int shift = 0; //when needed, shift the integers down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each integer in the current array
        {
            if (i == index) //if we have reached the given index, do not add that integer
            {
                shift = 1; //shift the following integers over
            }
            newArr[i] = arr[i + shift]; //add each integer from the old array and shift it over one space if need be
        }

        return newArr; //return the new array
    }
    
    /**
     * Removes a String at the given index.
     * @param arr The array of Strings to be manipulated.
     * @param index The index of the String to be deleted.
     * @return Returns a new array without the String at the given index.
     */
    @Override
    public String[] deleteString(String[] arr, int index) {
        String[] newArr = new String[arr.length - 1]; //creates a new array with one less space (since we will be removing a String)
        int shift = 0; //when needed, shift each String down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each String in the current array
        {
            if (i == index) //if we have reached the given index, do not add that String
            {
                shift = 1; //shift the following Strings over
            }
            newArr[i] = arr[i + shift]; //add each String from the old array and shift it over one space if need be
        }

        return newArr; //return the new array
    }
    
    /**
     * Adds a card at a given index.
     * @param arr The array of cards to be manipulated.
     * @param index The index of where the card will be added.
     * @param newCard The new Card being added.
     * @return Returns a new array with the newCard inserted at the given index.
     */
    @Override
    public Card[] insert(Card newCard, Card[] arr, int index) {
        Card[] newArr = new Card[arr.length + 1]; //creates new deck with one extra space (since we will be adding a card)
        int shift = 0; //when needed, shift the card down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each space in the new deck
        {
            if (i == index) //if we have reach the spot in which to place our new card
            {
                newArr[i] = newCard; //place new card in desired index
                shift = -1; //shift all the following cards over one space
            } else {
                newArr[i] = arr[i + shift]; //add the card from the old deck and shift it over one space if need be
            }
        }
        return newArr;

    }
    
    /**
     * Adds a given integer at a given index.
     * @param arr The array of integers to be manipulated.
     * @param index The index of where the integer will be added.
     * @param newi The new integer being added.
     * @return Returns a new array with the integer inserted at the given index.
     */
    @Override
    public int[] insertInt(int newi, int[] arr, int index) {
        int[] newArr = new int[arr.length + 1]; //creates new array with one extra space (since we will be adding an integer)
        int shift = 0; //when needed, shift the integers down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each space in the new array
        {
            if (i == index) //if we have reach the spot in which to place our new integer
            {
                newArr[i] = newi; //place new integer in desired index
                shift = -1; //shift all the following integer over one space
            } else {
                newArr[i] = arr[i + shift]; //add the integer from the old array and shift it over one space if need be
            }
        }
        return newArr;

    }
    
    /**
     * Adds a given String at a given index.
     * @param arr The array of Strings to be manipulated.
     * @param index The index of where the new String will be added.
     * @param newString The new String being added.
     * @return Returns a new array with the String inserted at the given index.
     */
    @Override
    public String[] insertString(String newString, String[] arr, int index) {
        String[] newArr = new String[arr.length + 1]; //creates new array with one extra space (since we will be adding a String)
        int shift = 0; //when needed, shift the Strings down 1 space
        for (int i = 0; i < newArr.length; i++) //go through each space in the new array
        {
            if (i == index) //if we have reach the spot in which to place our new String
            {
                newArr[i] = newString; //place new String in desired index
                shift = -1; //shift all the following Strings over one space
            } else {
                newArr[i] = arr[i + shift]; //add the String from the old array and shift it over one space if need be
            }
        }
        return newArr;

    }
    
    /**
     * Sorts the cards using the Bubble Sort method.
     * @param arr The array of cards to be sorted.
     * @return Returns the array of cards sorted by value and suit.
     */
    @Override
    public Card[] sort(Card[] arr) {
        for (int i = 0; i < arr.length - 1; i++) //sort each card in the array (except the last one - it will be sorted as cards swap with one another)
        {
            for (int j = 0; j < arr.length - i - 1; j++) //go through each card in the deck (do not chech the bigger values that have already been sorted)
            {
                if (arr[j].compareTo(arr[j + 1]) == 1) //check if the card has a larger value than the one infront of it
                {
                    Card n = arr[j]; //remember the first card
                    arr[j] = arr[j + 1]; //set the first card to the second card
                    arr[j + 1] = n; //set the second card to the first (stored in n)
                }
            }
        }
        return arr;
    }
    
    /**
     * Finds the index given a card in an array of Cards.
     * @param arr The array of cards to be checked.
     * @param v The value of the card being searched for.
     * @param s The suit of the card being searched for.
     * @return Returns the index where the given card has been found.
     */
    @Override
    public Integer find(Card[] arr, int v, char s) {

        for (int i = 0; i < arr.length; i++) //searches through each card in deck
        {
            if (arr[i].value == v && arr[i].suit == s) //if the specified card is found
            {
                return i; //return the index position
            }
        }

        return null;

    }
    
    /**
     * Shuffles an array of Cards.
     * @param arr The array of cards to be manipulated.
     * @return Returns the shuffled array of cards.
     */
    @Override
    public Card[] shuffle(Card[] arr) {
        for (int i = 0; i < arr.length; i++) //shuffle in relation to the size of the deck
        {
            int a = (int) (Math.random() * (arr.length - 1)); //selects to random cards from the deck
            int b = (int) (Math.random() * (arr.length - 1));
            Card n = new Card(arr[b].value, arr[b].suit, arr[b].x, arr[b].y, arr[b].imageIndex); //swap two random cards in the deck
            arr[b] = new Card(arr[a].value, arr[a].suit, arr[a].x, arr[a].y, arr[a].imageIndex);
            arr[a] = n;
        }
        return arr;
    }
    
    /**
     * Quickly finds the index given a card in a sorted array of Cards.
     * @param arr The array of cards to be checked.
     * @param low The lowest index in the (sub)array.
     * @param high The highest index in the (sub)array.
     * @param c The card being searched for.
     * @return Returns the index where the given card has been found.
     */
    @Override
    public Integer binaryFind(Card[] arr, int low, int high, Card c) {
        if (high >= low) {
            int pivot = ((high - low) / 2 + low); //find the middle 

            if (arr[pivot].compareTo(c) == 1)//if the pivot is larger than the value
            {
                return binaryFind(arr, low, pivot - 1, c); //check the lower half of the array

            } else if (arr[pivot].compareTo(c) == -1) //otherwise
            {
                return binaryFind(arr, pivot + 1, high, c); //chack the upper half of the array

            } else { //if they are equal

                return pivot; //return the index of the card

            }
        }

        // if the card is not present in the array
        return null;
    }
    
    /**
     * Finds the indexes of Card with a specified value.
     * @param arr The array of cards to be checked.
     * @param v The value of the cards being searched for.
     * @return Returns an array containing the indexes of the cards with the given value.
     */
    @Override
    public int[] findIndexes(Card[] arr, int v) {
        int[] foundIndex = new int[0]; //create an array to store the indexes
        for (int i = 0; i < arr.length; i++) { //go through each card in the array
            if (arr[i].value == v) { //if card is desired value
                foundIndex = this.insertInt(i, foundIndex, 0); //add its index to the array
            }
        }
        return foundIndex; //return all the indexes for the cards with the given value
    }
    
    /**
     * Finds the indexes of integers with a specified value.
     * @param arr The array of integers to be checked.
     * @param v The value of the integers being searched for.
     * @return Returns an array containing the indexes of the integers with the given value.
     */
    @Override
    public int[] findIndexesInt(int[] arr, int v) {
        int[] foundIndex = new int[0]; //create an array to store the indexes
        for (int i = 0; i < arr.length; i++) { //go through each integer in the array
            if (arr[i] == v) { //if the integer is the desired value
                foundIndex = this.insertInt(i, foundIndex, 0); //add its index to the array
            }
        }
        return foundIndex; //return all the indexes for the integers of the given value
    }

}
