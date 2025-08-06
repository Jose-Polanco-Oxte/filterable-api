package text;

import api.queries.criteria.QueryTextManager;
import filters.CollectionFilter;
import filters.Filter;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@DisplayName("Query text manager Tests")
@MockitoSettings(strictness = Strictness.LENIENT)
public class TextManagerTest {

    private QueryTextManager<User> manager;
    // Mock objects for testing
    private final Root<User> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);
    private final Predicate predicate = mock(Predicate.class);

    @BeforeEach
    public void setUp() {
        manager = new QueryTextManager<>();
        when(root.get(User_.name)).thenReturn(mock());
    }

    @Test
    @DisplayName("Test custom filter with null specification")
    public void testCustomFilterWithNullSpecification() {
        manager.custom(null);
        assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
    }

    @Test
    @DisplayName("Test custom filter with valid specification")
    public void testCustomFilterWithValidSpecification() {
        when(criteriaBuilder.equal(root.get("name"), root.get("name"))).thenReturn(predicate);
        manager.custom((root1, query1, cb) ->  cb.equal(root1.get("name"), root1.get("name")));
        Predicate result = manager.let().build().toPredicate(root, query, criteriaBuilder);
        assertNotNull(result);
        assertEquals(predicate, result);
    }

    @Nested
    @DisplayName("Filter record tests")
    class FilterRecordTests {
        @BeforeEach
        public void setUp() {
            manager = new QueryTextManager<>();
        }

        @Test
        @DisplayName("Method with null filter and null attribute")
        public void testFilterWithNullFilterAndAttribute() {
            manager.filter(null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null filter and valid attribute")
        public void testFilterWithNullFilterAndValidAttribute() {
            manager.filter(null, User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and null attribute")
        public void testFilterWithValidFilterAndNullAttribute() {
            manager.filter(new Filter<>("test", TextOperation.EQ), null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with filter values null")
        public void testFilterWithFilterValuesNull() {
            manager.filter(new Filter<>(null, TextOperation.EQ), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filter(new Filter<>(null, null), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filter(new Filter<>("test", null), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid filter and valid attribute")
        public void testFilterWithValidFilterAndValidAttribute() {
            when(criteriaBuilder.equal(root.get(User_.name), "test")).thenReturn(predicate);
            manager.filter(new Filter<>("test", TextOperation.EQ), User_.name);
            Predicate result = manager.let().build().toPredicate(root, query, criteriaBuilder);
            assertNotNull(result);
            assertEquals(predicate, result);
        }
    }

    @Nested
    @DisplayName("Filter method with singular attribute tests")
    class FilterMethodWithSingularAttributeTests {
        @BeforeEach
        public void setUp() {
            manager = new QueryTextManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null value")
        public void testFilterWithNullAttributeAndValue() {
            manager.filter(null, null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid value")
        public void testFilterWithNullAttributeAndValidValue() {
            manager.filter(User_.name, null, TextOperation.EQ);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and null value")
        public void testFilterWithValidAttributeAndNullValue() {
            manager.filter(null, "test", TextOperation.EQ);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute, valid value and null operation")
        public void testFilterWithValidAttributeAndValueAndNullOperation() {
            manager.filter(User_.name, "test", null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute, valid value and valid operation")
        public void testFilterWithValidAttributeAndValueAndOperation() {
            when(criteriaBuilder.equal(root.get(User_.name), "test")).thenReturn(predicate);
            manager.filter(User_.name, "test", TextOperation.EQ);
            Predicate result = manager.let().build().toPredicate(root, query, criteriaBuilder);
            assertNotNull(result);
            assertEquals(predicate, result);
        }
    }

    @Nested
    @DisplayName("Filter in method tests with record")
    class FilterInMethodTests {
        @BeforeEach
        public void setUp() {
            manager = new QueryTextManager<>();
        }

        @Test
        @DisplayName("Method with null record and null attribute")
        public void testFilterInWithNullRecordAndAttribute() {
            manager.filterIn(null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null record and valid attribute")
        public void testFilterInWithNullRecordAndValidAttribute() {
            manager.filterIn(null, User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid record and null attribute")
        public void testFilterInWithValidRecordAndNullAttribute() {
            manager.filterIn(new CollectionFilter<>(List.of("test1", "test2"), TextCollectionOperation.IN), null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with filter values null")
        public void testFilterInWithValidRecordAndNullOperation() {
            manager.filterIn(new CollectionFilter<>(List.of("test1", "test2"), null), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filterIn(new CollectionFilter<>(null, TextCollectionOperation.IN), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            setUp();
            manager.filterIn(new CollectionFilter<>(null, null), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
            manager.filterIn(new CollectionFilter<>(List.of(), TextCollectionOperation.IN), User_.name);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid record and valid attribute")
        public void testFilterInWithValidRecordAndValidAttribute() {
            when(root.get(User_.name).in(List.of("test1", "test2"))).thenReturn(predicate);
            manager.filterIn(new CollectionFilter<>(List.of("test1", "test2"), TextCollectionOperation.IN), User_.name);
            Predicate result = manager.let().build().toPredicate(root, query, criteriaBuilder);
            assertNotNull(result);
            assertEquals(predicate, result);
        }
    }

    @Nested
    @DisplayName("Filter in method tests with singular attribute")
    class FilterInMethodWithSingularAttributeTests {
        @BeforeEach
        public void setUp() {
            manager = new QueryTextManager<>();
        }

        @Test
        @DisplayName("Method with null attribute and null values and operation")
        public void testFilterInWithNullAttributeAndValuesAndOperation() {
            manager.filterIn(null, null, null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and null values")
        public void testFilterInWithNullAttributeAndValues() {
            manager.filterIn(null, null, TextCollectionOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with null attribute and valid values")
        public void testFilterInWithNullAttributeAndValidValues() {
            manager.filterIn(null, List.of("test1", "test2"), TextCollectionOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and null values")
        public void testFilterInWithValidAttributeAndNullValues() {
            manager.filterIn(User_.name, null, TextCollectionOperation.IN);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute and valid values but null operation")
        public void testFilterInWithValidAttributeAndValuesAndNullOperation() {
            manager.filterIn(User_.name, List.of("test1", "test2"), null);
            assertNull(manager.let().build().toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Method with valid attribute, valid values and valid operation")
        public void testFilterInWithValidAttributeAndValuesAndOperation() {
            when(root.get(User_.name).in(List.of("test1", "test2"))).thenReturn(predicate);
            manager.filterIn(User_.name, List.of("test1", "test2"), TextCollectionOperation.IN);
            Predicate result = manager.let().build().toPredicate(root, query, criteriaBuilder);
            assertNotNull(result);
            assertEquals(predicate, result);
        }
    }
}
