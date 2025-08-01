import api.FilterableApi;
import api.queries.utils.FilterSpecification;
import filters.operations.ComparableOperation;
import filters.operations.InOperation;
import jakarta.persistence.criteria.*;
import metamodels.User_;
import models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Comparable Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
public class ComparableTest {

    private final Root<User> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);
    private final Predicate expectedPredicate = mock(Predicate.class);
    private final Predicate otherPredicate = mock(Predicate.class); // Mock for the other predicate

    @Test
    @DisplayName("ComparableOperations")
    public void comparableOperations() {
        /* Build the specification with FilterableApi */

        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filter(User_.age, 20, ComparableOperation.GT)
                .let().build();

        Path<Integer> agePath = mock(Path.class);

        when(root.get(User_.age)).thenReturn(agePath);
        when(criteriaBuilder.greaterThan(agePath, 20)).thenReturn(expectedPredicate);

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        System.out.println("Result Predicate: " + result);
        System.out.println("Expected Predicate: " + expectedPredicate);
        assertNotNull(result);
        assertEquals(expectedPredicate, result);
        verify(criteriaBuilder).greaterThan(agePath, 20);
        verify(root).get(User_.age);
        verifyNoMoreInteractions(criteriaBuilder, root, query, agePath);
        // Ensure no other interactions with the mocks
        verifyNoInteractions(expectedPredicate);
    }

    @Test
    @DisplayName("ComparableOperations with null value")
    public void comparableOperationsWithNullValue() {
        // Build the specification with FilterableApi
        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filter(User_.age, null, ComparableOperation.GT)
                .let().build();

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        assertNull(result);
        verifyNoInteractions(criteriaBuilder, root, query);
    }

    @Test
    @DisplayName("ComparableOperations with null operation")
    public void comparableOperationsWithNullOperation() {
        // Build the specification with FilterableApi
        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filter(User_.age, 20, null) // Null operation
                .let().build();

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        assertNull(result);
        verifyNoInteractions(criteriaBuilder, root, query);
    }

    @Test
    @DisplayName("ComparableOperations with empty collection")
    public void comparableOperationsWithEmptyCollection() {
        // Build the specification with FilterableApi
        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filterIn(User_.age, List.of(), InOperation.IN) // Empty collection
                .let().build();

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        assertNull(result);
        verifyNoInteractions(criteriaBuilder, root, query);
    }

    @Test
    @DisplayName("ComparableOperations with non-empty collection")
    public void comparableOperationsWithNonEmptyCollection() {
        // Build the specification with FilterableApi
        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filterIn(User_.age, List.of(20, 30), InOperation.IN) // Non-empty collection
                .let().build();

        Path<Integer> agePath = mock(Path.class);

        when(root.get(User_.age)).thenReturn(agePath);
        when(agePath.in(List.of(20, 30))).thenReturn(expectedPredicate);

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        assertEquals(expectedPredicate, result);
        verify(root).get(User_.age);
        verify(agePath).in(List.of(20, 30));
        verifyNoMoreInteractions(criteriaBuilder, root, query, agePath);
        // Ensure no other interactions with the mocks
        verifyNoInteractions(expectedPredicate);
    }

    @Test
    @DisplayName("ComparableOperations with null collection")
    public void comparableOperationsWithNullCollection() {
        // Build the specification with FilterableApi
        FilterableApi<User> api = FilterableApi.create();
        FilterSpecification<User> specification = api
                .<Integer>comparable().configure()
                .filterIn(User_.age, null, InOperation.IN) // Null collection
                .let().build();

        // Call the toPredicate method to get the Predicate
        Predicate result = specification.toPredicate(root, query, criteriaBuilder);

        // Assertions to verify the behavior
        assertNull(result);
        verifyNoInteractions(criteriaBuilder, root, query);
    }
}
