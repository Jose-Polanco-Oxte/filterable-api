package api.queries.criteria;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Builder for creating filter specifications for string attributes.
 *
 * @param <T> the type of the entity to filter
 * @see FilterSpecification
 */
public class CriteriaSingularStringBuilder<T> {

    public CriteriaSingularStringBuilder() {
    }

    public static <T> CriteriaSingularStringBuilder<T> create() {
        return new CriteriaSingularStringBuilder<>();
    }

    /**
     * Contains operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "contains" operation
     */
    public FilterSpecification<T> containsOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(attribute), "%" + value + "%");
    }

    /**
     * Contains operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "contains" operation with join
     */
    public <R> FilterSpecification<T> containsOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), "%" + value + "%");
        };
    }

    /**
     * Not contains operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "not contains" operation
     */
    public FilterSpecification<T> notContainsOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notLike(root.get(attribute), "%" + value + "%");
    }

    /**
     * Not contains operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "not contains" operation with join
     */
    public <R> FilterSpecification<T> notContainsOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.notLike(from.get(attribute), "%" + value + "%");
        };
    }

    /**
     * Contains all operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "contains all" operation
     */
    public FilterSpecification<T> startsWithOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(attribute), value + "%");
    }

    /**
     * Contains operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "contains" operation with join
     */
    public <R> FilterSpecification<T> startsWithOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), value + "%");
        };
    }

    /**
     * Ends with operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "ends with" operation
     */
    public FilterSpecification<T> endsWithOp(SingularAttribute<T, String> attribute, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(attribute), "%" + value);
    }

    /**
     * Ends with operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "ends with" operation with join
     */
    public <R> FilterSpecification<T> endsWithOp(SingularAttribute<R, String> attribute, String value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            return criteriaBuilder.like(from.get(attribute), "%" + value);
        };
    }

    // Operations with string collections

    /**
     * Contains operation for a collection of values.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "contains" operation for the collection
     */
    public FilterSpecification<T> containsOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Contains operation for a collection of values with join path.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "contains" operation for the collection with join
     */
    public <R> FilterSpecification<T> containsOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Not contains operation for a collection of values.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "not contains" operation for the collection
     */
    public FilterSpecification<T> notContainsOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.notLike(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Not contains operation for a collection of values with join path.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "not contains" operation for the collection with join
     */
    public <R> FilterSpecification<T> notContainsOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.notLike(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Contains all operation for a collection of values.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "contains all" operation for the collection
     */
    public FilterSpecification<T> containsAllOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Contains all operation for a collection of values with join path.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "contains all" operation for the collection with join
     */
    public <R> FilterSpecification<T> containsAllOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Contains operation for a collection of values.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "contains" operation for the collection
     */
    public FilterSpecification<T> startsWithOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Contains operation for a collection of values with join path.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "contains" operation for the collection with join
     */
    public <R> FilterSpecification<T> startsWithOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), value + "%"));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Ends with operation for a collection of values.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "ends with" operation for the collection
     */
    public FilterSpecification<T> endsWithOp(SingularAttribute<T, String> attribute, Collection<String> values) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(root.get(attribute), "%" + value));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    /**
     * Ends with operation for a collection of values with join path.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "ends with" operation for the collection with join
     */
    public <R> FilterSpecification<T> endsWithOp(SingularAttribute<R, String> attribute, Collection<String> values, Function<Root<T>, From<?, R>> joinPath) {
        if (values == null || values.isEmpty()) {
            return FilterSpecification.none();
        }
        return (root, query, criteriaBuilder) -> {
            From<?, R> from = joinPath.apply(root);
            List<Predicate> predicates = new ArrayList<>();
            for (String value : values) {
                predicates.add(criteriaBuilder.like(from.get(attribute), "%" + value));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
