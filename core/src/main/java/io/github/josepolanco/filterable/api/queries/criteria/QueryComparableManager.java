package io.github.josepolanco.filterable.api.queries.criteria;

import io.github.josepolanco.filterable.api.FilterableApi;
import io.github.josepolanco.filterable.api.configurations.OperationRegistry;
import io.github.josepolanco.filterable.api.exceptions.FilterDisabledException;
import io.github.josepolanco.filterable.api.operations.FilterOperation;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.filters.CollectionFilter;
import io.github.josepolanco.filterable.filters.Filter;
import io.github.josepolanco.filterable.filters.RangeFilter;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.InOperation;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

/**
 * Manager for building filter specifications for comparable attributes.
 * <p> Their methods can throw {@link FilterDisabledException} if the operation is disabled in the registry. 
 *
 * @param <T> the type of the entity to filter
 * @param <Y> the type of the {@link Comparable} attribute
 * @see FilterableApi
 * @see ComparableOperation
 * @see InOperation
 * @see FilterSpecification
 */
public class QueryComparableManager<T, Y extends Comparable<? super Y>> extends SpecQuery<T, Y, ComparableOperation, InOperation> {

    private OperationRegistry operationRegistry;

    private FilterSpecification<T> specification;

    public QueryComparableManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
    }

    /**
     * Adds a custom filter specification to the current specification,
     * if the provided specification is null, it will be ignored.
     *
     * @param specification the custom filter specification to add
     * @return the current QueryComparableManager instance
     */
    @Override
    public QueryComparableManager<T, Y> custom(FilterSpecification<T> specification) {
        if (specification == null) return this;
        this.specification = this.specification.and(specification);
        return this;
    }

    /**
     * Applies a filter based on the provided filter object and attribute;
     * if the filter, attribute, operation, or value is null, the method will have no effect.
     *
     * @param filter    the filter object containing the value and operation
     * @param attribute the attribute to filter on
     * @return the current {@link QueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see Filter
     * @see ComparableOperation
     */
    @Override
    public QueryComparableManager<T, Y> filter(Filter<Y, ComparableOperation> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.value() == null) {
            return this;
        }
        return filter(attribute, filter.value(), filter.operation());
    }

    /**
     * Applies a filter based on the provided attribute, value, and operation;
     * if the attribute, value, or operation is null, the method will have no effect.
     *
     * @param attribute the attribute to filter on
     * @param value     the value to compare against
     * @param operation the comparison operation to apply
     * @return the current {@link QueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see ComparableOperation
     */
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

    /**
     * Applies an "in" or "not in" filter based on the provided attribute, values, and operation;
     * if the attribute, values, or operation is null, or if the values collection is empty, the method will have no effect.
     *
     * @param attribute the attribute to filter on
     * @param values    the collection of values to compare against
     * @param operation the "in" or "not in" operation to apply
     * @return the current {@link QueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see InOperation
     * @see SingularAttribute
     */
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

    /**
     * Applies a collection filter based on the provided filter object and attribute;
     * if the filter, attribute, operation, or values are null or empty, the method will have no effect.
     *
     * @param filter    the collection filter object containing the values and operation
     * @param attribute the attribute to filter on
     * @return the current {@link QueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see CollectionFilter
     * @see InOperation
     */
    @Override
    public QueryComparableManager<T, Y> filterIn(CollectionFilter<Y, InOperation> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.values() == null || filter.values().isEmpty()) {
            return this;
        }
        return filterIn(attribute, filter.values(), filter.operation());
    }

    /**
     * Applies a "between" filter based on the provided attribute, start value, and end value;
     * if the attribute, start, or end is null, the method will have no effect.
     *
     * @param attribute the attribute to filter on
     * @param start     the start value of the range
     * @param end       the end value of the range
     * @return the current {@link QueryComparableManager} instance
     */
    public QueryComparableManager<T, Y> filterBetween(SingularAttribute<T, Y> attribute, Y start, Y end) {
        if (attribute == null || start == null || end == null) {
            return this;
        }
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = comparableBuilder.betweenOp(attribute, start, end);
        this.specification = this.specification.and(spec);
        return this;
    }

    /**
     * Applies a "between" filter based on the provided range filter object and attribute,
     * if the filter, attribute, start, or end is null, the method will have no effect.
     *
     * @param filter    the range filter object containing the start and end values
     * @param attribute the attribute to filter on
     * @return the current {@link QueryComparableManager} instance
     * @see RangeFilter
     */
    public QueryComparableManager<T, Y> filterBetween(RangeFilter<Y> filter, SingularAttribute<T, Y> attribute) {
        if (filter == null || attribute == null || filter.start() == null || filter.end() == null) {
            return this;
        }
        return filterBetween(attribute, filter.start(), filter.end());
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
