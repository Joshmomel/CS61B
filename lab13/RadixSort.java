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
        // TODO: Implement LSD Sort
        //get the max length
        int max = 0;
        for (String ascii : asciis) {
            if (ascii.length() > max) {
                max = ascii.length();
            }
        }

        for (int i = 0; i < max; i++) {
            sortHelperLSD(asciis, i, max);
        }


        return asciis;
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
        String[] test = new String[]{"cat", "apple", "appld", "cs", "come", "be", "ted"};
        String[] sorted = RadixSort.sort(test);
        for (String s : sorted) {
            System.out.println(s);
        }
    }
}
