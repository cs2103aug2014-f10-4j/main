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
    DeleteCommandTest.class,
    DisplayCommandTest.class,
    EditCommandTest.class,
    MarkCommandTest.class,
    UnmarkCommandTest.class,
    UndoRedoTest.class,
})
public class ListIntegrationTestSuite {

}
