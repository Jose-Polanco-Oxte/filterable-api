package api.queries.criteria;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

public class CriteriaSingularBuilder<T, Y> {

    private CriteriaSingularBuilder() {}

    public static <T, Y> CriteriaSingularBuilder<T, Y> builder() {
        return new CriteriaSingularBuilder<>();
    }

    public FilterSpecification<T> equalsOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> equalsOp(
            SingularAttribute<R, Y> attribute,
            Y value,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(pathResolver.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> notEqualsOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> notEqualsOp(
            SingularAttribute<R, Y> attribute,
            Y value,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(pathResolver.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> inOp(SingularAttribute<T, Y> attribute, Collection<Y> values) {
        return (root, query, criteriaBuilder) -> root.get(attribute).in(values);
    }

    public <R> FilterSpecification<T> inOp(
            SingularAttribute<R, Y> attribute,
            Collection<Y> values,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> pathResolver.apply(root).get(attribute).in(values);
    }

    public FilterSpecification<T> notInOp(SingularAttribute<T, Y> attribute, Collection<Y> values) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.not(root.get(attribute).in(values));
    }

    public <R> FilterSpecification<T> notInOp(
            SingularAttribute<R, Y> attribute,
            Collection<Y> values,
            Function<Root<T>, From<?, R>> pathResolver
    ) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.not(pathResolver.apply(root).get(attribute).in(values));
    }
}
