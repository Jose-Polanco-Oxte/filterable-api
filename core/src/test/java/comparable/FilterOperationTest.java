package comparable;

import api.FilterableApi;
import api.queries.utils.FilterSpecification;
import filters.operations.ComparableOperation;
import filters.operations.InOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import metamodels.User_;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@DisplayName("Filter operations tests")
public class FilterOperationTest {

    private final Root<User> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);
    private final Predicate expectedPredicate = mock(Predicate.class);
    private FilterableApi<User> api;

    @BeforeEach
    public void setUp() {
        api = FilterableApi.create();
    }

    @Test
    @DisplayName("Greater than operation")
    public void greaterThanOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(criteriaBuilder.greaterThan(root.get(User_.id), 10L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 10L, ComparableOperation.GT)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Less than operation")
    public void lessThanOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(criteriaBuilder.lessThan(root.get(User_.id), 20L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 20L, ComparableOperation.LT)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Greater than or equal operation")
    public void greaterThanOrEqualOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(criteriaBuilder.greaterThanOrEqualTo(root.get(User_.id), 30L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 30L, ComparableOperation.GTE)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Less than or equal operation")
    public void lessThanOrEqualOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(criteriaBuilder.lessThanOrEqualTo(root.get(User_.id), 40L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 40L, ComparableOperation.LTE)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("In Operation")
    public void inOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(root.get(User_.id).in(anyCollection())).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filterIn(User_.id, List.of(50L, 20L), InOperation.IN)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Not In Operation")
    public void notInOperation() {
        when(root.get(User_.id)).thenReturn(mock());
        when(root.get(User_.id).in(anyCollection())).thenReturn(mock());
        when(criteriaBuilder.not(root.get(User_.id).in(anyCollection()))).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filterIn(User_.id, List.of(50L, 20L), InOperation.NOT_IN)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Equals operation")
    public void equalsOperation() {
        when(root.get(User_.name)).thenReturn(mock());
        when(criteriaBuilder.equal(root.get(User_.id), 2L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 2L, ComparableOperation.EQ)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }

    @Test
    @DisplayName("Not equals operation")
    public void notEqualsOperation() {
        when(root.get(User_.name)).thenReturn(mock());
        when(criteriaBuilder.notEqual(root.get(User_.id), 3L)).thenReturn(expectedPredicate);
        FilterSpecification<User> spec = api.<Long>comparable().configure()
                .filter(User_.id, 3L, ComparableOperation.NEQ)
                .let().build();
        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, criteriaBuilder);
        assertNotNull(predicate);
        assertEquals(expectedPredicate, predicate);
    }
}
