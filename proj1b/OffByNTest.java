import org.junit.Test;

import static org.junit.Assert.*;

public class OffByNTest {

    @Test
    public void equalChars() {
        OffByN offByN = new OffByN(5);
        assertTrue(offByN.equalChars('a', 'f'));

    }
}