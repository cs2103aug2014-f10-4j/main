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
    //AddCommandTest.class,
    CommandParserTest.class,
    ControllerTest.class,
    ConverterTest.class,
    DateTest.class,
    //DeleteCommandTest.class,
    //DisplayCommandTest.class,
    //EditCommandTest.class,
    JodaTimeTest.class,
    //MarkCommandTest.class,
    TaskTest.class,
    //UnmarkCommandTest.class,
    UndoRedoTest.class
})
public class ListTestSuite {
    
}