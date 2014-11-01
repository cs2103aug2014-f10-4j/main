package list;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.utils.Span;

public class JChronicTest {

	@Test
	public void test() throws Exception {
		Span time;
		time = parseNow("5pm on Friday");
		System.out.println(time.toString());
	}
	
	public Span parseNow(String string) {
		return parseNow(string, new Options());
	}
	
	private Span parseNow(String string, Options options) {
		return Chronic.parse(string, options);
	}

}
