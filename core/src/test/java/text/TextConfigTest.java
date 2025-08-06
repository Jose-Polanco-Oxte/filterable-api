package text;

import api.configurations.TextConfig;
import filters.operations.FilterOperation;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Text configuration tests")
public class TextConfigTest {

    private TextConfig<User> textConfig;

    @BeforeEach
    public void setUp() {
        textConfig = new TextConfig<>();
    }

    @Test
    @DisplayName("Disable Contains")
    public void disableContainsAny() {
        textConfig.disableContainsAny();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.CONTAINS));
    }

    @Test
    @DisplayName("Disable Not Contains")
    public void disableNotContains() {
        textConfig.disableNotContains();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_CONTAINS));
    }

    @Test
    @DisplayName("Disable Contains All")
    public void disableContainsAnyAll() {
        textConfig.disableContainsAll();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.CONTAINS_ALL));
    }

    @Test
    @DisplayName("Disable Starts With")
    public void disableStartsWith() {
        textConfig.disableStartsWith();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.STARTS_WITH));
    }

    @Test
    @DisplayName("Disable Ends With")
    public void disableEndsWith() {
        textConfig.disableEndsWith();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.ENDS_WITH));
    }

    @Test
    @DisplayName("Disable Equals")
    public void disableEquals() {
        textConfig.disableEquals();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.EQUALS));
    }

    @Test
    @DisplayName("Disable Not Equals")
    public void disableNotEquals() {
        textConfig.disableNotEquals();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_EQUALS));
    }

    @Test
    @DisplayName("Disable In")
    public void disableIn() {
        textConfig.disableIn();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.IN));
    }

    @Test
    @DisplayName("Disable Not In")
    public void disableNotIn() {
        textConfig.disableNotIn();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_IN));
    }

    @Test
    @DisplayName("Disable All Operations")
    public void disableAllOperations() {
        textConfig.disableAll();
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.CONTAINS));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_CONTAINS));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.CONTAINS_ALL));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.STARTS_WITH));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.ENDS_WITH));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.EQUALS));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_EQUALS));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.IN));
        assertTrue(textConfig.getOperationRegistry().isOperationDisabled(FilterOperation.NOT_IN));
    }
}
