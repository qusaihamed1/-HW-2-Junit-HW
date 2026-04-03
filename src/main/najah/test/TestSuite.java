package main.najah.test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    ProductTest.class,
    CalculatorTest.class,
    RecipeBookTest.class,
    UserServiceSimpleTest.class
})
public class TestSuite {

}