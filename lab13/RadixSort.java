import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        Queue<String>[] buckets = new Queue[256];
        for (int i = 0; i < 256; i++)
            buckets[i] = new LinkedList();

        boolean sorted = false;
        int lengthInc = 0;

        String[] sortedArr = new String[asciis.length];
        System.arraycopy(asciis, 0, sortedArr, 0, asciis.length);

        while (!sorted) {
            sorted = true;

            for (String item : sortedArr) {
                int index = item.length() - lengthInc - 1;
                if (index >= 0) {
                    sorted = false;
                    int ofASCII = (int) item.charAt(index);
                    buckets[ofASCII].add(item);
                } else {
                    buckets[(int) item.charAt(0)].add(item);
                }


            }

            lengthInc++;
            int index = 0;

            for (Queue<String> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    sortedArr[index] = bucket.remove();
                    index++;
                }
            }

            //System.out.println();

        }

        //System.out.println("");

        return sortedArr;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index, int max) {
        // Optional LSD helper method for required LSD radix sort
        int[] count = new int[127];
        for (String s : asciis) {
            int p = max - index - 1;
            if (p >= s.length()) {
                count[0] += 1;
            }
            if (p < s.length()) {
                p = Math.abs(p);
                int position = s.charAt(p);
                count[position] += 1;
            }
        }
        int[] starts = new int[127];
        int pointer = 0;
        for (int i = 0; i < 127; i++) {
            starts[i] = pointer;
            pointer += count[i];
        }
        //sort
        String[] sorted = new String[asciis.length];
        for (String s : asciis) {
            int p = max - index - 1;
            if (p < s.length()) {
                int item = s.charAt(Math.abs(p));
                int place = starts[item];
                sorted[place] = s;
                starts[item] += 1;
            }
            if (p >= s.length()) {
                int place = starts[0];
                sorted[place] = s;
                starts[0] += 1;
            }
        }

        for (int i = 0; i < asciis.length; i++) {
            asciis[i] = sorted[i];
        }

    }


    public static void main(String[] args) {
        String[] test = new String[]{"cat", "elephant", "ball", "fuck", "apple", "giant", "dick"};
        String[] sorted = RadixSort.sort(test);
        for (String s : sorted) {
            System.out.println(s);
        }
    }
}
