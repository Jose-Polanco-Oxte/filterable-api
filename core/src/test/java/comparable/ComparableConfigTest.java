package comparable;

import api.configurations.ComparableConfig;
import api.operations.FilterOperation;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comparable configuration tests")
public class ComparableConfigTest {

    private ComparableConfig<User, Integer> comparableConfig;

    @BeforeEach
    public void setUp() {
        comparableConfig = new ComparableConfig<>();
    }

    @Test
    @DisplayName("Disable Greater Than")
    public void disableGreaterThan() {
        comparableConfig.disableGt();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.GREATER_THAN));
    }

    @Test
    @DisplayName("Disable Greater Than or Equal")
    public void disableGreaterThanOrEqual() {
        comparableConfig.disableGte();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.GREATER_THAN_OR_EQUAL));
    }

    @Test
    @DisplayName("Disable Less Than")
    public void disableLessThan() {
        comparableConfig.disableLt();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.LESS_THAN));
    }

    @Test
    @DisplayName("Disable Less Than or Equal")
    public void disableLessThanOrEqual() {
        comparableConfig.disableLte();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.LESS_THAN_OR_EQUAL));
    }

    @Test
    @DisplayName("Disable Equals")
    public void disableEquals() {
        comparableConfig.disableEquals();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.EQUALS));
    }

    @Test
    @DisplayName("Disable Not Equals")
    public void disableNotEquals() {
        comparableConfig.disableNotEquals();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_EQUALS));
    }

    @Test
    @DisplayName("Disable In")
    public void disableIn() {
        comparableConfig.disableIn();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.IN));
    }

    @Test
    @DisplayName("Disable Not In")
    public void disableNotIn() {
        comparableConfig.disableNotIn();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_IN));
    }

    @Test
    @DisplayName("Disable All Operations")
    public void disableAllOperations() {
        comparableConfig.disableAll();
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.GREATER_THAN));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.GREATER_THAN));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.LESS_THAN));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.LESS_THAN_OR_EQUAL));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.EQUALS));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_EQUALS));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.IN));
        assertTrue(comparableConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_IN));
    }
}
