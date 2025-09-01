package io.github.josepolanco.filterable.api.queries.criteria;

import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.function.Function;

/**
 * A builder class for creating JPA Criteria API specifications for singular attributes of comparable types.
 *
 * @param <T> the type of the entity
 * @param <Y> the type of the comparable attribute
 * @see FilterSpecification
 */
public class CriteriaSingularComparableBuilder<T, Y extends Comparable<? super Y>> {

    public static <T, Y extends Comparable<? super Y>> CriteriaSingularComparableBuilder<T, Y> create() {
        return new CriteriaSingularComparableBuilder<>();
    }

    /**
     * Greater than operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "greater than" operation
     */
    public FilterSpecification<T> greaterThanOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute), value);
    }

    /**
     * Greater than operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "greater than" operation with join
     */
    public <R> FilterSpecification<T> greaterThanOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(joinPath.apply(root).get(attribute), value);
    }

    /**
     * Greater than or equal operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "greater than or equal" operation
     */
    public FilterSpecification<T> greaterThanOrEqualOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * Greater than or equal operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "greater than or equal" operation with join
     */
    public <R> FilterSpecification<T> greaterThanOrEqualOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(joinPath.apply(root).get(attribute), value);
    }

    /**
     * Less than operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "less than" operation
     */
    public FilterSpecification<T> lessThanOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute), value);
    }

    /**
     * Less than operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "less than" operation with join
     */
    public <R> FilterSpecification<T> lessThanOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(joinPath.apply(root).get(attribute), value);
    }

    /**
     * Less than or equal operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "less than or equal" operation
     */
    public FilterSpecification<T> lessThanOrEqualOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute), value);
    }

    /**
     * Less than or equal operation with join path.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "less than or equal" operation with join
     */
    public <R> FilterSpecification<T> lessThanOrEqualOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(joinPath.apply(root).get(attribute), value);
    }

    /**
     * Between operation.
     *
     * @param attribute the attribute to compare
     * @param start     the start value
     * @param end       the end value
     * @return a FilterSpecification representing the "between" operation
     */
    public FilterSpecification<T> betweenOp(SingularAttribute<T, Y> attribute, Y start, Y end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(attribute), start, end);
    }

    /**
     * Between operation with join path.
     *
     * @param attribute the attribute to compare
     * @param start     the start value
     * @param end       the end value
     * @param joinPath  the function to obtain the join path
     * @param <R>       the type of the joined entity
     * @return a FilterSpecification representing the "between" operation with join
     */
    public <R> FilterSpecification<T> betweenOp(SingularAttribute<R, Y> attribute, Y start, Y end, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(joinPath.apply(root).get(attribute), start, end);
    }
}
