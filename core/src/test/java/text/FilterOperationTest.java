package text;

import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.operations.TextCollectionOperation;
import io.github.josepolanco.filterable.filters.operations.TextOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import metamodels.User_;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
@DisplayName("Filter operations tests")
public class FilterOperationTest {

    private final Root<User> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);
    private final Predicate expectedPredicate = mock(Predicate.class);
    private FilterableApi<User> api;

    @Nested
    @DisplayName("Text operations")
    class TextOperations {
        @BeforeEach
        public void setUp() {
            api = FilterableApi.create();
        }

        @Test
        @DisplayName("Contains operation")
        public void containsOperation() {
            when(root.get(User_.name)).thenReturn(mock());
            when(criteriaBuilder.like(root.get(User_.name), "%John%")).thenReturn(expectedPredicate);
            FilterSpecification<User> spec = api.text().configure()
                    .filter(User_.name, "John", TextOperation.CONTAINS)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Not contains operation")
        public void notContainsOperation() {
            when(root.get(User_.name)).thenReturn(mock());
            when(criteriaBuilder.notLike(root.get(User_.name), "%John%")).thenReturn(expectedPredicate);
            FilterSpecification<User> spec = api.text().configure()
                    .filter(User_.name, "John", TextOperation.NOT_CONTAINS)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Starts with operation")
        public void startsWithOperation() {
            when(root.get(User_.name)).thenReturn(mock());
            when(criteriaBuilder.like(root.get(User_.name), "John%")).thenReturn(expectedPredicate);
            FilterSpecification<User> spec = api.text().configure()
                    .filter(User_.name, "John", TextOperation.STARTS_WITH)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Ends with operation")
        public void endsWithOperation() {
            when(root.get(User_.name)).thenReturn(mock());
            when(criteriaBuilder.like(root.get(User_.name), "%John")).thenReturn(expectedPredicate);
            FilterSpecification<User> spec = api.text().configure()
                    .filter(User_.name, "John", TextOperation.ENDS_WITH)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }
    }

// This test suite is commented out because it requires additional setup for collection operations.

    @Nested
    @DisplayName("Text collection operations")
    class TextCollectionOperations {
        @BeforeEach
        public void setUp() {
            api = FilterableApi.create();
            when(root.get(User_.name)).thenReturn(mock());
            when(criteriaBuilder.like(root.get(User_.name), "")).thenReturn(mock());
            when(criteriaBuilder.or(new Predicate[2])).thenReturn(expectedPredicate);
            when(criteriaBuilder.and(new Predicate[2])).thenReturn(expectedPredicate);
            when(criteriaBuilder.not(any())).thenReturn(expectedPredicate);
            when(root.get(User_.name).in(anyList())).thenReturn(expectedPredicate);
        }

        @Test
        @DisplayName("Contains all operation")
        public void containsAllOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.CONTAINS_ALL)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Contains any operation")
        public void containsAnyOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.CONTAINS_ANY)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Not contains operation")
        public void notContainsOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.NOT_CONTAINS)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Starts with operation")
        public void startsWithOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.STARTS_WITH)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Ends with operation")
        public void endsWithOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.ENDS_WITH)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("In operation")
        public void inOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.IN)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }

        @Test
        @DisplayName("Not in operation")
        public void notInOperation() {
            FilterSpecification<User> spec = api.text().configure()
                    .filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.NOT_IN)
                    .let().build();
            assertNotNull(spec);
            Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
            assertNotNull(predicate);
            assertEquals(expectedPredicate, predicate);
        }
    }
}
