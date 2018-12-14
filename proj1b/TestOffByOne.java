import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator interface and OffByOne class. **/

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars(){
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'A'));
        assertFalse(offByOne.equalChars('A', 'A'));
        assertFalse(offByOne.equalChars('Z', 'A'));
        assertFalse(offByOne.equalChars('Z', 'a'));
        assertFalse(offByOne.equalChars('a', 'B'));

        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('(', ')'));
        assertTrue(offByOne.equalChars('=', '>'));
        assertTrue(offByOne.equalChars('@', 'A'));

        assertFalse(offByOne.equalChars('&', ')'));
        assertFalse(offByOne.equalChars('!', '?'));

    }


}
