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
    AddCommandTest.class,
    ControllerTest.class,
    ConverterTest.class,
    DateTest.class,
    DeleteCommandTest.class,
    DisplayCommandTest.class,
    EditCommandTest.class,
    JodaTimeTest.class,
    ParserTest.class,
    TaskTest.class
})
public class ListTestSuite {
    
}