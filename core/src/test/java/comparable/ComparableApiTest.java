package comparable;

import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.configurations.ComparableConfig;
import io.github.josepolanco.filterable.api.exceptions.FilterDisabledException;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.InOperation;
import metamodels.Role_;
import metamodels.User_;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comparable API Tests")
public class ComparableApiTest {
    @Nested
    @DisplayName("Test with default api")
    class DefaultApiTests {
        @Test
        @DisplayName("Test disable equals method")
        public void testEquals() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.EQ));
        }

        @Test
        @DisplayName("Test disable not equals method")
        public void testNotEquals() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableNotEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.NEQ));
        }

        @Test
        @DisplayName("Test disable greater than method")
        public void testGreaterThan() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableGt);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.GT));
        }

        @Test
        @DisplayName("Test disable less than method")
        public void testLessThan() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableLt);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.LT));
        }

        @Test
        @DisplayName("Test disable greater than or equal method")
        public void testGreaterThanOrEqual() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableGte);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.GTE));
        }

        @Test
        @DisplayName("Test disable less than or equal method")
        public void testLessThanOrEqual() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableLte);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.LTE));
        }

        @Test
        @DisplayName("Test disable in method")
        public void testIn() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.id, List.of(1L, 2L), InOperation.IN));
        }

        @Test
        @DisplayName("Test disable not in method")
        public void testNotIn() {
            var api = FilterableApi.<User>create().<Long>comparable().configure(ComparableConfig::disableNotIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.id, List.of(1L, 2L), InOperation.NOT_IN));
        }

        @Test
        @DisplayName("Disable all comparable operations")
        public void testDisableAllComparableOperations() {
            var api = FilterableApi.<User>create().<Long>comparable()
                    .configure(ComparableConfig::disableAll);
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.EQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.NEQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.GT));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.LT));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.GTE));
            assertThrows(FilterDisabledException.class, () -> api.filter(User_.id, 1L, ComparableOperation.LTE));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.id, List.of(1L), InOperation.IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(User_.id, List.of(1L), InOperation.NOT_IN));
        }
    }

    @Nested
    @DisplayName("Test with relational api")
    class RelationalApiTests {
        @Test
        @DisplayName("Test disable equals method")
        public void testEquals() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.EQ));
        }

        @Test
        @DisplayName("Test disable not equals method")
        public void testNotEquals() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableNotEquals);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.NEQ));
        }

        @Test
        @DisplayName("Test disable greater than method")
        public void testGreaterThan() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableGt);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.GT));
        }

        @Test
        @DisplayName("Test disable less than method")
        public void testLessThan() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableLt);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.LT));
        }

        @Test
        @DisplayName("Test disable greater than or equal method")
        public void testGreaterThanOrEqual() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableGte);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.GTE));
        }

        @Test
        @DisplayName("Test disable less than or equal method")
        public void testLessThanOrEqual() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableLte);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.LTE));
        }

        @Test
        @DisplayName("Test disable in method")
        public void testIn() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.id, List.of(1L, 2L), InOperation.IN));
        }

        @Test
        @DisplayName("Test disable not in method")
        public void testNotIn() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableNotIn);
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.id, List.of(1L, 2L), InOperation.NOT_IN));
        }

        @Test
        @DisplayName("Disable all comparable operations")
        public void testDisableAllComparableOperations() {
            var api = FilterableApi.<User>create().relational().join(User_.roles).buildPath()
                    .<Long>comparable().configure(ComparableConfig::disableAll);
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.EQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.NEQ));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.GT));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.LT));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.GTE));
            assertThrows(FilterDisabledException.class, () -> api.filter(Role_.id, 1L, ComparableOperation.LTE));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.id, List.of(1L), InOperation.IN));
            assertThrows(FilterDisabledException.class, () -> api.filterIn(Role_.id, List.of(1L), InOperation.NOT_IN));
        }
    }
}
