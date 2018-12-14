import java.util.ArrayList;

/**
 * This class outputs all palindromes in the words file in the current directory.
 */
public class PalindromeFinder {
    public static void main(String[] args) {
        int minLength = 4;
        In in = new In("words.txt");
        Palindrome palindrome = new Palindrome();
        ArrayList<String> wordsList = new ArrayList<>();

        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() >= minLength && palindrome.isPalindrome(word, new OffByOne())) {
                wordsList.add(word);
            }
        }

        for (String s : wordsList) {
            System.out.println(s);
        }
    }
}
