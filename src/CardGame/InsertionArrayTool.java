package CardGame;
/**
 * The BubbleArrayTool class is used to organize and manipulate a variety
 * of array types using insertion sort.
 * @author Sarah Simionescu
 * @version 1
 */
public class InsertionArrayTool extends BubbleArrayTool {
    
    /**
     * Initializes the InsertionArrayTool.
     */
    public InsertionArrayTool() {

    }
    
    /**
     * Sorts the cards by calling the Insertion Sort method.
     * @param arr The array of cards to be sorted.
     * @return Returns the array of cards sorted by value and suit.
     */
    @Override
    public Card[] sort(Card[] arr) {
        int i;
        int j;
        Card key;
        for (i = 1; i < arr.length; i++) //go through each key value starting at the second spot in the array
        {
            key = arr[i];  //remember your key value
            j = i - 1;  //remember the number below your key value

            while (j >= 0 && arr[j].compareTo(key) == 1) //as long as the key value is larger than the one below it, swap it backwards until it is sorted
            {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        //each value will be sorted into the sub array behind it until the entire array is sorted
        return arr;
    }

}
