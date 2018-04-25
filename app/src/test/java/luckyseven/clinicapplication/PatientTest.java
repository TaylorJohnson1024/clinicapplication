
package clinicapplication;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Zinet Kemal
 */
public class PatientTest {

    /**
     * Test of getId method, of class Patient.
     */
    @Test
    public void testgetId() {
        int expected = 1;
        int actual;

        Patient p = new Patient(1, true);
        actual = p.getId();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of isInTrial method, of class Patient.
     */
    @Test
    public void testisInTrial() {
        boolean expected = true;
        boolean actual;

        Patient p = new Patient(1, true);
        actual = p.isInTrial();
        assertEquals(actual, expected);
    }
    
    /**
     * Test of isInTrial method, of class Patient.
     */
    @Test
    public void testisInTrial_false() {
        boolean expected = false;
        boolean actual;

        Patient p = new Patient(1, false);
        actual = p.isInTrial();
        assertEquals(actual, expected);
    }
    
    
     /**
     * Test of addReading method, of class Patient.
     */
    @Test
    public void testaddReading() {
        String expected = "Patient ID doesn't match";
        String actual;
        
        Reading rd; 
        Date d1 = new Date();
        rd = new Reading("111", "clinic", "String type", "String id","String value", d1);

        Patient p = new Patient(1, true);
        actual = p.addReading(rd);
        assertEquals(actual, expected);
    }
    
    
 }
