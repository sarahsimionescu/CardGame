package CardGame;
/**
 * The BubbleArrayTool class is used to organize and manipulate a variety
 * of array types using quick sort.
 * @author Sarah Simionescu
 * @version 1
 */
public class QuickArrayTool extends BubbleArrayTool {
    
    /**
     * Initializes the QuickArrayTool.
     */
    public QuickArrayTool() {
    }

    /**
     * Sorts the cards by calling the Quick Sort method.
     * @param arr The array of cards to be sorted.
     * @return Returns the array of cards sorted by value and suit.
     */
    @Override
    public Card[] sort(Card[] arr) {
        return quickSort(arr, 0, arr.length - 1);
    }
    
    /**
     * Sorts the cards by using the Quick Sort method.
     * @param arr The (sub)array of cards to be sorted.
     * @param low The lowest index in the (sub)array
     * @param high The highest index in the (sub)array
     * @return Returns the array of cards sorted by value and suit.
     */
    public Card[] quickSort(Card[] arr, int low, int high) {
        int i = low; //remember the range for your array or sub array
        int j = high;

        Card pivot = arr[low + (high - low) / 2];  // calculate pivot number (take the number in the middle)
        while (i <= j) { //countinue as long as i and j have not crossed

            while (arr[i].compareTo(pivot) == -1) { //continue until i is larger than the pivot, at that point, stop
                i++;
            }
            while (arr[j].compareTo(pivot) == 1) { //continue until j is smaller than the pviot, at that point, stop
                j--;
            }
            if (i <= j) { //if i  and j have not crossed..
                Card n = arr[j]; //remember the first card
                arr[j] = arr[i]; //set the first card to the second card
                arr[i] = n; //set the second card to the first (stored in n)
                //move both values inwards towards eachother
                i++;
                j--;
            }
        }
        //after i and j have crossed divide the array into sub arrays and call quicksort method upon them until the array is fully sorted
        if (low < j) {
            quickSort(arr, low, j);
        }
        if (i < high) {
            quickSort(arr, i, high);
        }
        return arr;
    }

}
