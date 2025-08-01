package api.queries.criteria;

import api.queries.utils.FilterSpecification;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.function.Function;

public class CriteriaSingularComparableBuilder<T, Y extends Comparable<? super Y>> {

    public static <T, Y extends Comparable<? super Y>> CriteriaSingularComparableBuilder<T, Y> create() {
        return new CriteriaSingularComparableBuilder<>();
    }

    public FilterSpecification<T> greaterThanOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> greaterThanOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(joinPath.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> greaterThanOrEqualOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> greaterThanOrEqualOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(joinPath.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> lessThanOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> lessThanOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(joinPath.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> lessThanOrEqualOp(SingularAttribute<T, Y> attribute, Y value) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(attribute), value);
    }

    public <R> FilterSpecification<T> lessThanOrEqualOp(SingularAttribute<R, Y> attribute, Y value, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(joinPath.apply(root).get(attribute), value);
    }

    public FilterSpecification<T> betweenOp(SingularAttribute<T, Y> attribute, Y start, Y end) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(attribute), start, end);
    }

    public <R> FilterSpecification<T> betweenOp(SingularAttribute<R, Y> attribute, Y start, Y end, Function<Root<T>, From<?, R>> joinPath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(joinPath.apply(root).get(attribute), start, end);
    }
}
