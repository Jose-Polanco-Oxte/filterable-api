package api.queries.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface FilterSpecification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    default FilterSpecification<T> and(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {
            Predicate thisPredicate = this.toPredicate(root, query, criteriaBuilder);
            Predicate otherPredicate = other.toPredicate(root, query, criteriaBuilder);
            if (thisPredicate == null) return otherPredicate;
            if (otherPredicate == null) return thisPredicate;
            System.out.println("Creating with " + thisPredicate + " and " + otherPredicate);
            var p = criteriaBuilder.and(thisPredicate, otherPredicate);
            System.out.println("Created: " + p);
            return p;
        };
    }

    default FilterSpecification<T> or(FilterSpecification<T> other) {
        return (root, query, criteriaBuilder) -> {
            Predicate thisPredicate = this.toPredicate(root, query, criteriaBuilder);
            Predicate otherPredicate = other.toPredicate(root, query, criteriaBuilder);
            if (thisPredicate == null) return otherPredicate;
            if (otherPredicate == null) return thisPredicate;
            return criteriaBuilder.or(thisPredicate, otherPredicate);
        };
    }

    static <T> FilterSpecification<T> and(List<FilterSpecification<T>> specifications) {
        return (root, query, criteriaBuilder) -> {
            if (specifications == null || specifications.isEmpty()) {
                return null;
            }
            FilterSpecification<T> spec = none();
            for (FilterSpecification<T> specification : specifications) {
                spec = spec.and(specification);
            }
            System.out.println("Creating AND specification with " + specifications.size() + " specifications" + "Specifications: " + specifications);
            var predicate = spec.toPredicate(root, query, criteriaBuilder);
            System.out.println("Created AND predicate: " + predicate);
            return predicate;
        };
    }

    static <T> FilterSpecification<T> or(FilterSpecification<T>... specifications) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = new Predicate[specifications.length];
            for (int i = 0; i < specifications.length; i++) {
                predicates[i] = specifications[i].toPredicate(root, query, criteriaBuilder);
            }
            return criteriaBuilder.or(predicates);
        };
    }

    static <T> FilterSpecification<T> or(Predicate... predicates) {
        return (root, query, criteriaBuilder) -> {
            if (predicates == null || predicates.length == 0) {
                return null;
            }
            return criteriaBuilder.or(predicates);
        };
    }

    static <T> FilterSpecification<T> none() {
        return (root, query, criteriaBuilder) -> null;
    }

    static <T> FilterSpecification<T> alwaysTrue() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    static <T> FilterSpecification<T> alwaysFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
    }
}
