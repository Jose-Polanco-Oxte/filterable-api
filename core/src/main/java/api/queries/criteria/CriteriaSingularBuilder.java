package api.queries.criteria;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

/**
 * A builder class for creating JPA Criteria API specifications for singular attributes.
 *
 * @param <T> the entity type
 * @param <Y> the attribute type
 * @see FilterSpecification
 */
public class CriteriaSingularBuilder<T, Y> {

    private CriteriaSingularBuilder() {
    }

    public static <T, Y> CriteriaSingularBuilder<T, Y> builder() {
        return new CriteriaSingularBuilder<>();
    }

    /**
     * Equals operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "equals" operation
     */
    public FilterSpecification<T> equalsOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attribute), value);
    }

    /**
     * Equals operation with path resolver.
     *
     * @param attribute    the attribute to compare
     * @param value        the value to compare
     * @param pathResolver a function to resolve the path to the attribute
     * @return a FilterSpecification representing the "equals" operation
     */
    public <R> FilterSpecification<T> equalsOp(
            SingularAttribute<R, Y> attribute,
            Y value,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(pathResolver.apply(root).get(attribute), value);
    }

    /**
     * Not equals operation.
     *
     * @param attribute the attribute to compare
     * @param value     the value to compare
     * @return a FilterSpecification representing the "not equals" operation
     */
    public FilterSpecification<T> notEqualsOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(attribute), value);
    }

    /**
     * Not equals operation with path resolver.
     *
     * @param attribute    the attribute to compare
     * @param value        the value to compare
     * @param pathResolver a function to resolve the path to the attribute
     * @return a FilterSpecification representing the "not equals" operation
     */
    public <R> FilterSpecification<T> notEqualsOp(
            SingularAttribute<R, Y> attribute,
            Y value,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(pathResolver.apply(root).get(attribute), value);
    }

    /**
     * In operation.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "in" operation
     */
    public FilterSpecification<T> inOp(SingularAttribute<T, Y> attribute, Collection<Y> values) {
        return (root, query, criteriaBuilder) -> root.get(attribute).in(values);
    }

    /**
     * In operation with path resolver.
     *
     * @param attribute    the attribute to compare.
     * @param values       the collection of values to compare
     * @param pathResolver a function to resolve the path to the attribute
     * @return a FilterSpecification representing the "in" operation
     */
    public <R> FilterSpecification<T> inOp(
            SingularAttribute<R, Y> attribute,
            Collection<Y> values,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> pathResolver.apply(root).get(attribute).in(values);
    }

    /**
     * Not in operation.
     *
     * @param attribute the attribute to compare
     * @param values    the collection of values to compare
     * @return a FilterSpecification representing the "not in" operation
     */
    public FilterSpecification<T> notInOp(SingularAttribute<T, Y> attribute, Collection<Y> values) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.not(root.get(attribute).in(values));
    }

    /**
     * Not in operation with path resolver.
     *
     * @param attribute    the attribute to compare
     * @param values       the collection of values to compare
     * @param pathResolver a function to resolve the path to the attribute
     * @return a FilterSpecification representing the "not in" operation
     */
    public <R> FilterSpecification<T> notInOp(
            SingularAttribute<R, Y> attribute,
            Collection<Y> values,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.not(pathResolver.apply(root).get(attribute).in(values));
    }
}
