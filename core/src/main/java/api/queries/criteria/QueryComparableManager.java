package api.queries.criteria;

import api.FilterableApi;
import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.queries.utils.FilterSpecification;
import filters.CollectionFilter;
import filters.Filter;
import filters.RangeFilter;
import filters.operations.ComparableOperation;
import filters.operations.FilterOperation;
import filters.operations.InOperation;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

public class QueryComparableManager<T, Y extends Comparable<? super Y>> extends SpecQuery<T, Y, ComparableOperation, InOperation> {
    private OperationRegistry operationRegistry;
    private FilterSpecification<T> specification;

    public QueryComparableManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
    }

    @Override
    public QueryComparableManager<T, Y> custom(FilterSpecification<T> specification) {
        if (specification == null) return this;
        this.specification = this.specification.and(specification);
        return this;
    }

    @Override
    public QueryComparableManager<T, Y> filter(Filter<Y, ComparableOperation> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.value() == null) {
            return this;
        }
        return filter(attribute, filter.value(), filter.operation());
    }

    @Override
    public QueryComparableManager<T, Y> filter(SingularAttribute<T, Y> attribute, Y value, ComparableOperation operation) {
        if (attribute == null || value == null || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = switch (operation) {
            case EQ -> builder.equalsOp(attribute, value);
            case NEQ -> builder.notEqualsOp(attribute, value);
            case GT -> comparableBuilder.greaterThanOp(attribute, value);
            case GTE -> comparableBuilder.greaterThanOrEqualOp(attribute, value);
            case LT -> comparableBuilder.lessThanOp(attribute, value);
            case LTE -> comparableBuilder.lessThanOrEqualOp(attribute, value);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public QueryComparableManager<T, Y> filterIn(SingularAttribute<T, Y> attribute, Collection<Y> values, InOperation operation) {
        if (attribute == null || values == null || values.isEmpty() || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        FilterSpecification<T> spec = switch (operation) {
            case IN -> builder.inOp(attribute, values);
            case NOT_IN -> builder.notInOp(attribute, values);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public QueryComparableManager<T, Y> filterIn(CollectionFilter<Y, InOperation> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.values() == null || filter.values().isEmpty()) {
            return this;
        }
        return filterIn(attribute, filter.values(), filter.operation());
    }

    public QueryComparableManager<T, Y> applyBetweenTo(SingularAttribute<T, Y> attribute, Y start, Y end) {
        if (attribute == null || start == null || end == null) {
            return this;
        }
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = comparableBuilder.betweenOp(attribute, start, end);
        this.specification = this.specification.and(spec);
        return this;
    }

    public QueryComparableManager<T, Y> applyBetweenTo(RangeFilter<Y> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.start() == null || filter.end() == null) {
            return this;
        }
        return applyBetweenTo(attribute, filter.start(), filter.end());
    }

    @Override
    public FilterableApi<T> let() {
        return FilterableApi.initialSpec(specification);
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

    @Override
    protected void setRegistry(OperationRegistry registry) {
        if (registry != null) {
            this.operationRegistry = registry;
        }
    }

    @Override
    protected void setSpecification(FilterSpecification<T> specification) {
        if (specification != null) {
            this.specification = specification;
        }
    }
}
