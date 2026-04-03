package main.najah.test;
import main.najah.code.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("UserService Tests")
public class UserServiceSimpleTest {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        System.out.println("--> UserService setup complete.");
    }

    @Test
    @DisplayName("Test Valid Email")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("user@example.com"));
    }

    @Test
    @DisplayName("Test Invalid Email - Missing @")
    void testInvalidEmailNoAt() {
        assertFalse(userService.isValidEmail("userexample.com"));
    }

    @Test
    @DisplayName("Test Invalid Email - Missing Dot")
    void testInvalidEmailNoDot() {
        assertFalse(userService.isValidEmail("user@examplecom"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"test@", "noDot@", "user.com", "", " "})
    @DisplayName("Parameterized Test - Invalid Emails")
    void testParameterizedInvalidEmails(String email) {
        assertFalse(userService.isValidEmail(email));
    }

    @Test
    @DisplayName("Test Valid Login")
    void testValidLogin() {
        assertTrue(userService.authenticate("admin", "1234"));
    }

    @Test
    @DisplayName("Test Invalid Login")
    void testInvalidLogin() {
        assertFalse(userService.authenticate("user", "wrongpass"));
    }

    @Test
    @DisplayName("Test Authentication with Multiple Assertions")
    void testAuthMultipleAssertions() {
        assertAll(
                () -> assertTrue(userService.authenticate("admin", "1234")),
                () -> assertFalse(userService.authenticate("admin", "0000")),
                () -> assertFalse(userService.authenticate("user", "1234"))
        );
    }

    @Test
    @DisplayName("Timeout Test on Email Validation")
    void testEmailValidationTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            userService.isValidEmail("fast@check.com");
        });
    }
}