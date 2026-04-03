package main.najah.test;
import static org.junit.jupiter.api.Assertions.*;

import main.najah.code.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Calculator Tests")
public class CalculatorTest {

    Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("===> Starting Calculator Tests...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("===> Finished Calculator Tests.");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("--> Set up for a test case");
    }

    @AfterEach
    void tearDown() {
        System.out.println("--> Tear down after a test case");
    }

    @Test
    @Order(1)
    @DisplayName("Test Add Method With Multiple Inputs")
    void testAddMultipleInputs() {
        int result = calc.add(1, 2, 3, 4);
        assertEquals(10, result);
    }

    @Test
    @Order(2)
    @DisplayName("Test Divide Method With Valid Input")
    void testDivide() {
        int result = calc.divide(10, 2);
        assertEquals(5, result);
    }

    @Test
    @Order(3)
    @DisplayName("Test Divide By Zero - Exception Expected")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
    }

    @Test
    @Order(4)
    @DisplayName("Test Factorial Valid Input with Multiple Assertions")
    void testFactorialValid() {
        int result = calc.factorial(5);
        assertAll(
                () -> assertTrue(result > 0),
                () -> assertEquals(120, result)
        );
    }

    @Test
    @Order(5)
    @DisplayName("Test Factorial With Negative Number - Exception Expected")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-3));
    }

    @ParameterizedTest
    @Order(6)
    @DisplayName("Parameterized Test for Addition")
    @ValueSource(ints = {1, 2, 3, 4, 5})
    void testAddSingleInput(int num) {
        assertEquals(num, calc.add(num));
    }

    @Test
    @Order(7)
    @DisplayName("Timeout Test for Add Method")
    void testAddTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            calc.add(1, 2, 3, 4, 5);
        });
    }

    @Test
    @Order(8)
    @Disabled("Fix later: this test is intentionally failing")
    @DisplayName("Disabled Failing Test")
    void testFailing() {
        fail("This test is disabled and should be fixed later.");
    }
}