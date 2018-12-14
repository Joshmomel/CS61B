import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void isPalindrome(){
        assertFalse(palindrome.isPalindrome("car"));
        assertTrue(palindrome.isPalindrome("cac"));
        assertTrue(palindrome.isPalindrome("ccccc"));
        assertFalse(palindrome.isPalindrome("cacss"));
        assertFalse(palindrome.isPalindrome("cAC"));
        assertFalse(palindrome.isPalindrome("angina"));
    }

    @Test
    public void testisPalindrome(){
        assertFalse(palindrome.isPalindrome("car", new OffByOne()));
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("a", new OffByOne()));




    }

}
