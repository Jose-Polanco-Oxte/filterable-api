package api.queries.criteria;

import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.queries.utils.FilterSpecification;
import api.relations.RelationalApi;
import filters.CollectionFilter;
import filters.Filter;
import filters.RangeFilter;
import filters.operations.ComparableOperation;
import filters.operations.FilterOperation;
import filters.operations.InOperation;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

public class RQueryComparableManager<T, R, Y extends Comparable<? super Y>> extends SpecRelationQuery<T, R, Y, ComparableOperation, InOperation> {
    private OperationRegistry operationRegistry;
    private FilterSpecification<T> specification;
    private Function<Root<T>, From<?, R>> joinPath;

    public RQueryComparableManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
        this.joinPath = null; // Default to the root
    }

    public void setRegistry(OperationRegistry registry) {
        if (registry != null) {
            this.operationRegistry = registry;
        }
    }

    public void setSpecification(FilterSpecification<T> specification) {
        if (specification != null) {
            this.specification = specification;
        }
    }

    public void setJoinPath(Function<Root<T>, From<?, R>> joinPath) {
        if (joinPath != null) {
            this.joinPath = joinPath;
        }
    }

    @Override
    public RQueryComparableManager<T, R, Y> where(FilterSpecification<T> specification) {
        if (specification == null) {
            return this;
        }
        this.specification = this.specification.and(specification);
        return this;
    }

    @Override
    public RQueryComparableManager<T, R, Y> filter(Filter<Y, ComparableOperation> filter, SingularAttribute<R, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.value() == null) {
            return this;
        }
        checkAvailability(filter.operation());
        return filter(attribute, filter.value(), filter.operation());
    }

    public RQueryComparableManager<T, R, Y> filter(SingularAttribute<R, Y> attribute, Y value, ComparableOperation operation) {
        if (attribute == null || value == null || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = (root, query, criteriaBuilder) -> switch (operation) {
            case EQ -> builder.equalsOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
            case NEQ -> builder.notEqualsOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
            case GT -> comparableBuilder.greaterThanOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
            case GTE -> comparableBuilder.greaterThanOrEqualOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
            case LT -> comparableBuilder.lessThanOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
            case LTE -> comparableBuilder.lessThanOrEqualOp(attribute, value, joinPath).toPredicate(root, query, criteriaBuilder);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public RQueryComparableManager<T, R, Y> filterIn(SingularAttribute<R, Y> attribute, Collection<Y> values, InOperation operation) {
        if (attribute == null || values == null || values.isEmpty() || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        FilterSpecification<T> spec = (root, query, criteriaBuilder) -> switch (operation) {
            case IN -> builder.inOp(attribute, values, joinPath).toPredicate(root, query, criteriaBuilder);
            case NOT_IN -> builder.notInOp(attribute, values, joinPath).toPredicate(root, query, criteriaBuilder);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public RQueryComparableManager<T, R, Y> filterIn(CollectionFilter<Y, InOperation> filter, SingularAttribute<R, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.values() == null || filter.values().isEmpty()) {
            return this;
        }
        checkAvailability(filter.operation());
        return filterIn(attribute, filter.values(), filter.operation());
    }

    public RQueryComparableManager<T, R, Y> applyBetweenTo(
            SingularAttribute<R, Y> attribute,
            Y lowerBound,
            Y upperBound
    ) {
        if (attribute == null || lowerBound == null || upperBound == null) {
            return this;
        }
        checkAvailability(FilterOperation.BETWEEN);
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = (root, query, criteriaBuilder) ->
                comparableBuilder.betweenOp(attribute, lowerBound, upperBound, joinPath).toPredicate(root, query, criteriaBuilder);
        this.specification = this.specification.and(spec);
        return this;
    }

    public RQueryComparableManager<T, R, Y> applyBetweenTo(RangeFilter<Y> filter, SingularAttribute<R, Y> attribute) {
        if (filter == null || attribute == null || filter.start() == null || filter.end() == null) {
            return this;
        }
        checkAvailability(FilterOperation.BETWEEN);
        return applyBetweenTo(attribute, filter.start(), filter.end());
    }

    @Override
    public RelationalApi<T, R> let() {
        return RelationalApi.of(this.joinPath, this.specification);
    }

    private void checkAvailability(ComparableOperation operation) {
        checkAvailability(FilterOperation.fromComparableOperation(operation));
    }

    private void checkAvailability(InOperation operation) {
        checkAvailability(FilterOperation.fromInOperation(operation));
    }

    private void checkAvailability(FilterOperation operation) {
        if (operationRegistry.isOperationDisabled(operation)) {
            throw new FilterDisabledException(operation);
        }
    }
}
