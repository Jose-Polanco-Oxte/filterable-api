package api.queries.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface FilterSpecification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    private static <T> Predicate toPredicateOrNull(FilterSpecification<T> spec, Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return spec == null ? null : spec.toPredicate(root, query, criteriaBuilder);
    }

    default FilterSpecification<T> and(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {

            Predicate thisPredicate = toPredicateOrNull(this, root, query, criteriaBuilder);
            Predicate otherPredicate = toPredicateOrNull(other, root, query, criteriaBuilder);

            if (thisPredicate == null) return otherPredicate;

            return otherPredicate == null ? thisPredicate : criteriaBuilder.and(thisPredicate, otherPredicate);
        };
    }

    default FilterSpecification<T> or(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {

            Predicate thisPredicate = toPredicateOrNull(this, root, query, criteriaBuilder);
            Predicate otherPredicate = toPredicateOrNull(other, root, query, criteriaBuilder);

            if (thisPredicate == null) return otherPredicate;

            return otherPredicate == null ? thisPredicate : criteriaBuilder.or(thisPredicate, otherPredicate);
        };
    }

    static <T> FilterSpecification<T> none() {
        return (root, query, criteriaBuilder) -> null;
    }
}
