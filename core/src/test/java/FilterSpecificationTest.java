import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("Filter Specification Tests")
public class FilterSpecificationTest {

    // Mock objects for testing
    @SuppressWarnings("unchecked")
    private final Root<String> root = mock(Root.class);
    private final CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
    private final CriteriaQuery<?> query = mock(CriteriaQuery.class);

    @Nested
    @DisplayName("Test FilterSpecification 'and' method")
    class AndMethodTests {
        @Test
        @DisplayName("Test 'and' with two specifications")
        public void testAndWithTwoSpecifications() {
            FilterSpecification<String> spec1 = (root, query, cb) -> cb.equal(root.get("field1"), "value1");
            FilterSpecification<String> spec2 = (root, query, cb) -> cb.equal(root.get("field2"), "value2");

            FilterSpecification<String> combinedSpec = spec1.and(spec2);
            assertNotNull(combinedSpec);
            assertDoesNotThrow(() -> combinedSpec.toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Test 'and' with second null specification")
        public void testAndWithSecondNullSpecification() {
            FilterSpecification<String> spec1 = (root, query, cb) -> cb.equal(root.get("field1"), "value1");
            FilterSpecification<String> spec2 = null;

            FilterSpecification<String> combinedSpec = spec1.and(spec2);
            assertNotNull(combinedSpec);
            assertDoesNotThrow(() -> combinedSpec.toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test FilterSpecification 'or' method")
    class OrMethodTests {
        @Test
        @DisplayName("Test 'or' with two specifications")
        public void testOrWithTwoSpecifications() {
            FilterSpecification<String> spec1 = (root, query, cb) -> cb.equal(root.get("field1"), "value1");
            FilterSpecification<String> spec2 = (root, query, cb) -> cb.equal(root.get("field2"), "value2");

            FilterSpecification<String> combinedSpec = spec1.or(spec2);
            assertNotNull(combinedSpec);
            assertDoesNotThrow(() -> combinedSpec.toPredicate(root, query, criteriaBuilder));
        }

        @Test
        @DisplayName("Test 'or' with second null specification")
        public void testOrWithSecondNullSpecification() {
            FilterSpecification<String> spec1 = (root, query, cb) -> cb.equal(root.get("field1"), "value1");
            FilterSpecification<String> spec2 = null;

            FilterSpecification<String> combinedSpec = spec1.or(spec2);
            assertNotNull(combinedSpec);
            assertDoesNotThrow(() -> combinedSpec.toPredicate(root, query, criteriaBuilder));
        }
    }

    @Nested
    @DisplayName("Test FilterSpecification 'none' method")
    class NoneMethodTests {
        @Test
        @DisplayName("Test 'none' returns a specification that produces null predicate")
        public void testNoneReturnsNullPredicate() {
            FilterSpecification<String> noneSpec = FilterSpecification.none();
            Predicate result = noneSpec.toPredicate(root, query, criteriaBuilder);
            assertNull(result, "Expected null predicate from 'none' specification");
        }
    }
}
