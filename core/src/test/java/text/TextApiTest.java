package text;


import api.FilterableApi;
import api.configurations.TextConfig;
import api.exceptions.FilterDisabledException;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
import metamodels.Role_;
import metamodels.User_;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Text API Tests")
public class TextApiTest {
    @Nested
    @DisplayName("Test with default api")
    class DefaultApiTest {
        @Test
        @DisplayName("Test disable equals method")
        public void testEquals() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.EQ));
        }

        @Test
        @DisplayName("Test disable not equals method")
        public void testNotEquals() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableNotEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.NEQ));
        }

        @Test
        @DisplayName("Test disable contains method")
        public void testContains() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableContainsAny);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.CONTAINS));
        }

        @Test
        @DisplayName("Test disable starts with method")
        public void testStartsWith() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableStartsWith);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.STARTS_WITH));
        }

        @Test
        @DisplayName("Test disable ends with method")
        public void testEndsWith() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableEndsWith);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.ENDS_WITH));
        }

        @Test
        @DisplayName("Test disable In method")
        public void testIn() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.IN));
        }

        @Test
        @DisplayName("Test disable Not In method")
        public void testNotIn() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableNotIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.NOT_IN));
        }

        @Test
        @DisplayName("Test disable contains any method")
        public void testContainsAny() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableContainsAny);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ANY));
        }

        @Test
        @DisplayName("Test disable contains all method")
        public void testContainsAll() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableContainsAll);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ALL));
        }

        @Test
        @DisplayName("Disable starts with collection")
        public void disableStartsWith() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableStartsWith);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.STARTS_WITH));
        }

        @Test
        @DisplayName("Disable ends with")
        public void disableEndsWith() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableEndsWith);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.ENDS_WITH));
        }

        @Test
        @DisplayName("Disable all operations")
        public void disableAllOperations() {
            var api = FilterableApi.<User>create().text().configure(TextConfig::disableAll);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.EQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.NEQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.CONTAINS));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.STARTS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.name, "John", TextOperation.ENDS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.NOT_IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ANY));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ALL));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.STARTS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.name, List.of("John", "Doe"), TextCollectionOperation.ENDS_WITH));
        }
    }

    @Nested
    @DisplayName("Test with relational api")
    class RelationalApiTest {
        @Test
        @DisplayName("Test disable equals method")
        public void testEquals() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.EQ));
        }

        @Test
        @DisplayName("Test disable not equals method")
        public void testNotEquals() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableNotEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.NEQ));
        }

        @Test
        @DisplayName("Test disable contains method")
        public void testContains() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableContainsAny);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.CONTAINS));
        }

        @Test
        @DisplayName("Test disable starts with method")
        public void testStartsWith() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableStartsWith);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.STARTS_WITH));
        }

        @Test
        @DisplayName("Test disable ends with method")
        public void testEndsWith() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableEndsWith);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.ENDS_WITH));
        }

        @Test
        @DisplayName("Test disable In method")
        public void testIn() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.IN));
        }

        @Test
        @DisplayName("Test disable Not In method")
        public void testNotIn() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableNotIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.NOT_IN));
        }

        @Test
        @DisplayName("Test disable contains any method")
        public void testContainsAny() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableContainsAny);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ANY));
        }

        @Test
        @DisplayName("Test disable contains all method")
        public void testContainsAll() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableContainsAll);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ALL));
        }

        @Test
        @DisplayName("Disable starts with collection")
        public void disableStartsWith() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableStartsWith);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.STARTS_WITH));
        }

        @Test
        @DisplayName("Disable ends with")
        public void disableEndsWith() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableEndsWith);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.ENDS_WITH));
        }

        @Test
        @DisplayName("Disable all operations")
        public void disableAllOperations() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .text().configure(TextConfig::disableAll);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.EQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.NEQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.CONTAINS));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.STARTS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.name, "John", TextOperation.ENDS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.NOT_IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ANY));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.CONTAINS_ALL));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.STARTS_WITH));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.name, List.of("John", "Doe"), TextCollectionOperation.ENDS_WITH));
        }
    }
}
