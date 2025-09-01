package io.github.josepolanco.filterable.api.queries.criteria;

import io.github.josepolanco.filterable.api.configurations.OperationRegistry;
import io.github.josepolanco.filterable.api.exceptions.FilterDisabledException;
import io.github.josepolanco.filterable.api.operations.FilterOperation;
import io.github.josepolanco.filterable.api.queries.utils.FilterSpecification;
import io.github.josepolanco.filterable.api.relations.RelationalApi;
import io.github.josepolanco.filterable.filters.CollectionFilter;
import io.github.josepolanco.filterable.filters.Filter;
import io.github.josepolanco.filterable.filters.RangeFilter;
import io.github.josepolanco.filterable.filters.operations.ComparableOperation;
import io.github.josepolanco.filterable.filters.operations.InOperation;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;
import java.util.function.Function;

/**
 * Manager for building filter specifications for comparable attributes in relational contexts.
 * <p> Their methods can throw {@link FilterDisabledException} if the operation is disabled in the registry. 
 *
 * @param <T> the type of the root entity to filter
 * @param <R> the type of the related entity
 * @param <Y> the type of the {@link Comparable} attribute in the related entity
 * @see ComparableOperation
 * @see InOperation
 * @see FilterSpecification
 * @see RelationalApi
 */
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

    /**
     * Adds a custom filter specification to the current specification,
     * if the provided specification is null, it will be ignored.
     *
     * @param specification the custom filter specification to add
     * @return the current RQueryComparableManager instance
     */
    @Override
    public RQueryComparableManager<T, R, Y> custom(FilterSpecification<T> specification) {
        if (specification == null) {
            return this;
        }
        this.specification = this.specification.and(specification);
        return this;
    }

    /**
     * Applies a filter based on the provided filter object and attribute,
     * if the filter, attribute, operation, or value is null, the method will have no effect.
     *
     * @param filter    the filter object containing the value and operation
     * @param attribute the attribute to filter on
     * @return the current {@link RQueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see Filter
     * @see ComparableOperation
     */
    @Override
    public RQueryComparableManager<T, R, Y> filter(Filter<Y, ComparableOperation> filter, SingularAttribute<R, Y> attribute) {
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
     * @return the current {@link RQueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see ComparableOperation
     */
    @Override
    public RQueryComparableManager<T, R, Y> filter(SingularAttribute<R, Y> attribute, Y value, ComparableOperation operation) {
        if (attribute == null || value == null || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = switch (operation) {
            case EQ -> builder.equalsOp(attribute, value, joinPath);
            case NEQ -> builder.notEqualsOp(attribute, value, joinPath);
            case GT -> comparableBuilder.greaterThanOp(attribute, value, joinPath);
            case GTE -> comparableBuilder.greaterThanOrEqualOp(attribute, value, joinPath);
            case LT -> comparableBuilder.lessThanOp(attribute, value, joinPath);
            case LTE -> comparableBuilder.lessThanOrEqualOp(attribute, value, joinPath);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    /**
     * Applies a filter based on the provided attribute, collection of values, and operation;
     * if the attribute, values, or operation is null, or if values is empty, the method will have no effect
     *
     * @param attribute the attribute to filter on
     * @param values    the collection of values to compare against
     * @param operation the in operation to apply (IN or NOT_IN)
     * @return the current {@link RQueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see InOperation
     */
    @Override
    public RQueryComparableManager<T, R, Y> filterIn(SingularAttribute<R, Y> attribute, Collection<Y> values, InOperation operation) {
        if (attribute == null || values == null || values.isEmpty() || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, Y> builder = CriteriaSingularBuilder.builder();
        FilterSpecification<T> spec = switch (operation) {
            case IN -> builder.inOp(attribute, values, joinPath);
            case NOT_IN -> builder.notInOp(attribute, values, joinPath);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    /**
     * Applies a filter based on the provided collection filter object and attribute,
     * if the filter, attribute, operation, or values are null or empty, the method will have no effect.
     *
     * @param filter    the collection filter object containing the values and operation
     * @param attribute the attribute to filter on
     * @return the current {@link RQueryComparableManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @see CollectionFilter
     * @see InOperation
     */
    @Override
    public RQueryComparableManager<T, R, Y> filterIn(CollectionFilter<Y, InOperation> filter, SingularAttribute<R, Y> attribute) {
        if (filter == null || attribute == null || filter.operation() == null || filter.values() == null || filter.values().isEmpty()) {
            return this;
        }
        return filterIn(attribute, filter.values(), filter.operation());
    }

    /**
     * Applies a "between" filter based on the provided attribute, lower bound, and upper bound;
     * if the attribute, lower bound, or upper bound is null, the method will have no effect.
     *
     * @param attribute  the attribute to filter on
     * @param lowerBound the lower bound of the range
     * @param upperBound the upper bound of the range
     * @return the current {@link RQueryComparableManager} instance
     */
    public RQueryComparableManager<T, R, Y> applyBetweenTo(
            SingularAttribute<R, Y> attribute,
            Y lowerBound,
            Y upperBound
    ) {
        if (attribute == null || lowerBound == null || upperBound == null) {
            return this;
        }
        CriteriaSingularComparableBuilder<T, Y> comparableBuilder = new CriteriaSingularComparableBuilder<>();
        FilterSpecification<T> spec = comparableBuilder.betweenOp(attribute, lowerBound, upperBound, joinPath);
        this.specification = this.specification.and(spec);
        return this;
    }

    /**
     * Applies a "between" filter based on the provided range filter object and attribute,
     * if the filter, attribute, start, or end is null, the method will have no effect.
     *
     * @param filter    the range filter object containing the start and end values
     * @param attribute the attribute to filter on
     * @return the current {@link RQueryComparableManager} instance
     * @see RangeFilter
     */
    public RQueryComparableManager<T, R, Y> applyBetweenTo(RangeFilter<Y> filter, SingularAttribute<R, Y> attribute) {
        if (filter == null || attribute == null || filter.start() == null || filter.end() == null) {
            return this;
        }
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
