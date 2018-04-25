
package clinicapplication;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zinet Kemal
 */
public class ReadingTest {

    /**
     * Test of setClinic method, of class Reading.
     */
    @Test
    public void testSetClinic() {
        String expected = "clinic 2";
        String actual;

        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "String type", "String id","String value", d1);
        rd.setClinic("clinic 2");
        actual = rd.getClinic();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setPatientID method, of class Reading.
     */
    @Test
    public void setPatientID() {
        String expected = "111";
        String actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "String type", "String id","String value", d1);
        actual = rd.getPatientID();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setRType method, of class Reading.
     */
    @Test
    public void setRType() {
        String expected = "type";
        String actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "type", "String id","String value", d1);
        actual = rd.getRType();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setRType method, of class Reading.
     */
    @Test
    public void setRId() {
        String expected = "Rid";
        String actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "type", "Rid","value", d1);
        actual = rd.getRId();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setRType method, of class Reading.
     */
    @Test
    public void setRValue() {
        String expected = "value";
        String actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "type", "String id","value", d1);
        actual = rd.getRValue();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setRType method, of class Reading.
     */
    @Test
    public void setRDate() {
        Date expected = new Date();
        Date actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "type", "String id","value", d1);
        actual = rd.getRDate();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of setRType method, of class Reading.
     */
    @Test
    public void TesttoString() {
        String expected = "";
        String actual;
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "type", "String id","value", d1);
        actual = rd.toString();
        assertEquals(actual, expected);
    }
}
