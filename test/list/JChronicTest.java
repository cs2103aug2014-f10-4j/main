package list;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mdimension.jchronic.Chronic;
import com.mdimension.jchronic.Options;
import com.mdimension.jchronic.tags.Pointer;
import com.mdimension.jchronic.utils.Span;

public class JChronicTest {

	@Test
	public void test() throws Exception {
		Span time;
		
		time = parseNow("25-12-2014 5pm");
		System.out.println(time.toString());
		
		time = Chronic.parse("saturday");
		System.out.println(time.toString());
	}
	
	public Span parseNow(String string) {
		return parseNow(string, new Options(Pointer.PointerType.FUTURE));
	}
	
	private Span parseNow(String string, Options options) {
		return Chronic.parse(string);
	}

}
