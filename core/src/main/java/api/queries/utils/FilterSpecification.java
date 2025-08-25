package api.queries.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * A functional interface for building filter specifications for JPA criteria queries.
 * <p> It provides methods to combine specifications using logical AND and OR operations. 
 *
 * @param <T> the type of the entity to filter
 * @see jakarta.persistence.criteria.CriteriaBuilder
 * @see jakarta.persistence.criteria.CriteriaQuery
 * @see jakarta.persistence.criteria.Predicate
 * @see jakarta.persistence.criteria.Root
 */
@FunctionalInterface
public interface FilterSpecification<T> {
    /**
     * Helper method to convert a FilterSpecification to a Predicate, returning null if the specification is null.
     *
     * @param spec            the FilterSpecification to convert
     * @param root            the root type in the from clause
     * @param query           the criteria query
     * @param criteriaBuilder the criteria builder
     * @param <T>             the type of the entity
     * @return the resulting Predicate, or null if the specification is null
     */
    private static <T> Predicate toPredicateOrNull(FilterSpecification<T> spec, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return spec == null ? null : spec.toPredicate(root, query, criteriaBuilder);
    }

    /**
     * Returns a FilterSpecification that does not apply any filtering (always true),
     * this specification can be used as a neutral element in logical operations.
     *
     * @param <T> the type of the entity
     * @return a FilterSpecification that does not filter any results
     */
    static <T> FilterSpecification<T> none() {
        return (root, query, criteriaBuilder) -> null;
    }

    /**
     * Converts this FilterSpecification to a Predicate.
     *
     * @param root            the root type in the from clause
     * @param query           the criteria query
     * @param criteriaBuilder the criteria builder
     * @return the resulting Predicate
     */
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);

    /**
     * Combines this FilterSpecification with another using a logical AND operation,
     * if either specification is null, the result will be the other specification.
     *
     * @param other the other FilterSpecification to combine with
     * @return a new FilterSpecification representing the logical AND of this and the other specification
     */
    default FilterSpecification<T> and(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {

            Predicate thisPredicate = toPredicateOrNull(this, root, query, criteriaBuilder);
            Predicate otherPredicate = toPredicateOrNull(other, root, query, criteriaBuilder);

            if (thisPredicate == null) return otherPredicate;

            return otherPredicate == null ? thisPredicate : criteriaBuilder.and(thisPredicate, otherPredicate);
        };
    }

    /**
     * Combines this FilterSpecification with another using a logical OR operation,
     * if either specification is null, the result will be the other specification.
     *
     * @param other the other FilterSpecification to combine with
     * @return a new FilterSpecification representing the logical OR of this and the other specification
     */
    default FilterSpecification<T> or(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {

            Predicate thisPredicate = toPredicateOrNull(this, root, query, criteriaBuilder);
            Predicate otherPredicate = toPredicateOrNull(other, root, query, criteriaBuilder);

            if (thisPredicate == null) return otherPredicate;

            return otherPredicate == null ? thisPredicate : criteriaBuilder.or(thisPredicate, otherPredicate);
        };
    }
}
