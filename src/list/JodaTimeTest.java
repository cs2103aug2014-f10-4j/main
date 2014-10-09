package list;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Learning the behavior of JodaTime library.
 * 
 * @author Andhieka Putra
 */
public class JodaTimeTest {

	@Test
	public void testComparingTwoDates() throws Exception {
		DateTime test = new DateTime(2014, 5, 3, 0, 0);
		Thread.sleep(1057);
		DateTime test2 = new DateTime(2013, 5, 3, 0, 0);
		assertEquals(test,  test2);
	}

}
