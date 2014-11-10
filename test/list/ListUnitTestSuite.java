package list;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * A test suite for running all test cases.
 * Please add new Tests here.
 * @author andhieka
 */
@RunWith(Suite.class)
@SuiteClasses({
    CommandParserTest.class,
    ConverterTest.class,
    DateTest.class,
    JodaTimeTest.class,
    JChronicTest.class,
    TaskTest.class
})
public class ListUnitTestSuite {    
}