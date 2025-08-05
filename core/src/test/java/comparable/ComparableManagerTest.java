package comparable;

import api.queries.criteria.QueryComparableManager;
import filters.CollectionFilter;
import filters.Filter;
import filters.RangeFilter;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
@DisplayName("Query Comparable Manager Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
public class ComparableManagerTest {

    private QueryComparableManager<User, Long> manager;
    // Mock objects for testing
    private final Root<User> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);
    private final Predicate predicate = mock(Predicate.class);

    @BeforeEach
    public void setUp() {
        manager = new QueryComparableManager<>();
    }

    @Test
    @DisplayName("Test custom method with null specification")
    public void testCustomWithNullSpecification() {
        manager.custom(null);
        assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
    }

    @Test
    @DisplayName("Test custom method with valid specification")
    public void testCustomWithValidSpecification() {
        when(criteriaBuilder.isTrue(root.get("someField"))).thenReturn(predicate);
        manager.custom((root, query, cb) -> cb.isTrue(root.get("someField")));
        assertNotNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
    }

    @Nested
    @DisplayName("Test filter record")
    class FilterRecordTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null filter and null attribute")
        public void methodWithNullFilterAndAttribute() {
            manager.filter(null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null filter and valid attribute")
        public void methodWithNullFilterAndValidAttribute() {
            manager.filter(null, User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and null attribute")
        public void methodWithValidFilterAndNullAttribute() {
            manager.filter(new Filter<>(3L, ComparableOperation.EQ), null);
        }

        @Test
        @DisplayName("Method with filter values null")
        public void methodWithFilterValuesNullAndValidAttribute() {
            manager.filter(new Filter<>(null, ComparableOperation.EQ), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filter(new Filter<>(null, null), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filter(new Filter<>(2L, null), User_.id);
        }
    }

    @Nested
    @DisplayName("Test filter method with SingularAttribute")
    class FilterMethodTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null value")
        public void methodWithNullAttributeAndValue() {
            manager.filter(null, null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid value")
        public void methodWithNullAttributeAndValidValue() {
            manager.filter(null, 3L, ComparableOperation.EQ);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and null value")
        public void methodWithValidAttributeAndNullValue() {
            manager.filter(User_.id, null, ComparableOperation.EQ);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test filterIn method with filter record")
    class FilterInMethodTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null filter")
        public void methodWithNullAttributeAndNullFilter() {
            manager.filterIn(null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid filter")
        public void methodWithNullAttributeAndValidFilter() {
            manager.filterIn(new CollectionFilter<>(List.of(1L, 2L), InOperation.IN), null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null filter and valid attribute")
        public void methodWithNullFilterAndValidAttribute() {
            manager.filterIn(null, User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with filter values null")
        public void methodWithFilterValuesNullAndValidAttribute() {
            manager.filterIn(new CollectionFilter<>(null, InOperation.IN), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filterIn(new CollectionFilter<>(List.of(), InOperation.IN), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filterIn(new CollectionFilter<>(List.of(1L, 2L), null), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and valid attribute")
        public void methodWithValidFilterAndNullAttribute() {
            when(root.get(User_.id)).thenReturn(mock());
            when(root.get(User_.id).in(List.of(1L, 2L))).thenReturn(predicate);
            manager.filterIn(new CollectionFilter<>(List.of(1L, 2L), InOperation.IN), User_.id);
            assertNotNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test filterIn method with SingularAttribute")
    class FilterInMethodWithSingularAttributeTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null values")
        public void methodWithNullAttributeAndNullValues() {
            manager.filterIn(null, null, InOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid values")
        public void methodWithNullAttributeAndValidValues() {
            manager.filterIn(null, List.of(1L, 2L), InOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and null values")
        public void methodWithValidAttributeAndNullValues() {
            manager.filterIn(User_.id, null, InOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and empty values")
        public void methodWithValidAttributeAndEmptyValues() {
            manager.filterIn(User_.id, List.of(), InOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and valid values")
        public void methodWithValidAttributeAndValidValues() {
            when(root.get(User_.id)).thenReturn(mock());
            when(root.get(User_.id).in(List.of(1L, 2L))).thenReturn(predicate);
            manager.filterIn(User_.id, List.of(1L, 2L), InOperation.IN);
            assertNotNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test applyBetweenTo method with filter record")
    class ApplyBetweenToMethodWithFilterRecordTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null filter and null attribute")
        public void methodWithNullFilterAndAttribute() {
            manager.applyBetweenTo(null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null filter and valid attribute")
        public void methodWithNullFilterAndValidAttribute() {
            manager.applyBetweenTo(null, User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and null attribute")
        public void methodWithValidFilterAndNullAttribute() {
            manager.applyBetweenTo(new RangeFilter<>(1L, 10L), null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with filter values null")
        public void methodWithFilterValuesNullAndValidAttribute() {
            manager.applyBetweenTo(new RangeFilter<>(null, null), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.applyBetweenTo(new RangeFilter<>(1L, null), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.applyBetweenTo(new RangeFilter<>(null, 10L), User_.id);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and valid attribute")
        public void methodWithValidFilterAndValidAttribute() {
            when(criteriaBuilder.between(root.get(User_.id), 1L, 10L)).thenReturn(predicate);
            manager.applyBetweenTo(new RangeFilter<>(1L, 10L), User_.id);
            assertNotNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test applyBetweenTo method with SingularAttribute")
    class ApplyBetweenToMethodTests {

        @BeforeEach
        public void setUp() {
            manager = new QueryComparableManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null start and end")
        public void methodWithNullAttributeAndNullStartAndEnd() {
            manager.applyBetweenTo(null, null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid start and end")
        public void methodWithNullAttributeAndValidStartAndEnd() {
            manager.applyBetweenTo(null, 1L, 10L);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and null start and end")
        public void methodWithValidAttributeAndNullStartAndEnd() {
            manager.applyBetweenTo(User_.id, null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and valid start and end")
        public void methodWithValidAttributeAndValidStartAndEnd() {
            when(criteriaBuilder.between(root.get(User_.id), 1L, 10L)).thenReturn(predicate);
            manager.applyBetweenTo(User_.id, 1L, 10L);
            assertNotNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }
    }
}