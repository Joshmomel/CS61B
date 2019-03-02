import org.junit.Test;
import static org.junit.Assert.*;


public class FlikTest {

    @Test
    public void numberTest(){
        boolean exp = true;
        boolean testSet = Flik.isSameNumber(127, 127);
        assertEquals(exp, testSet);
    }

}
