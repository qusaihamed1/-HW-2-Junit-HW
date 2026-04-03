package main.najah.test;
import main.najah.code.Product;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Tests")
@Execution(ExecutionMode.CONCURRENT) // Enable parallel execution
public class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000.0);
        System.out.println("--> Product setup complete.");
    }

    @Test
    @DisplayName("Test Valid Discount Application")
    void testApplyValidDiscount() {
        product.applyDiscount(20);
        assertEquals(800.0, product.getFinalPrice(), 0.01);
    }

    @Test
    @DisplayName("Test Invalid Discount - Above 50")
    void testApplyInvalidDiscountAbove50() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(60));
    }

    @Test
    @DisplayName("Test Invalid Discount - Negative")
    void testApplyInvalidDiscountNegative() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-5));
    }

    @Test
    @DisplayName("Test Multiple Product Getters")
    void testProductGetters() {
        assertAll(
                () -> assertEquals("Laptop", product.getName()),
                () -> assertEquals(1000.0, product.getPrice(), 0.01),
                () -> assertEquals(0.0, product.getDiscount(), 0.01)
        );
    }

    @Test
    @DisplayName("Test Product Final Price Without Discount")
    void testFinalPriceWithoutDiscount() {
        assertEquals(1000.0, product.getFinalPrice(), 0.01);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 10, 25.5, 50})
    @DisplayName("Parameterized Test for Valid Discounts")
    void testParameterizedValidDiscount(double discount) {
        product.applyDiscount(discount);
        assertTrue(product.getFinalPrice() <= 1000.0);
    }
    @Test
    @DisplayName("Timeout Test on Final Price Calculation")
    void testFinalPriceTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            product.applyDiscount(10);
            product.getFinalPrice();
        });
    }
}