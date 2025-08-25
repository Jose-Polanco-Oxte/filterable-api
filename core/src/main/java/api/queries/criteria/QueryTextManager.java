package api.queries.criteria;

import api.FilterableApi;
import api.configurations.OperationRegistry;
import api.exceptions.FilterDisabledException;
import api.operations.FilterOperation;
import api.queries.utils.FilterSpecification;
import filters.CollectionFilter;
import filters.Filter;
import filters.operations.TextCollectionOperation;
import filters.operations.TextOperation;
import jakarta.persistence.metamodel.SingularAttribute;

import java.util.Collection;

/**
 * Manager for building filter specifications for text attributes.
 * <p> Their methods can throw {@link FilterDisabledException} if the operation is disabled in the registry. </p>
 *
 * @param <T> the type of the entity to filter
 * @see TextOperation
 * @see TextCollectionOperation
 */
public class QueryTextManager<T> extends SpecQuery<T, String, TextOperation, TextCollectionOperation> {
    private OperationRegistry operationRegistry;
    private FilterSpecification<T> specification;

    public QueryTextManager() {
        this.operationRegistry = new OperationRegistry();
        this.specification = FilterSpecification.none();
    }

    /**
     * Adds a custom filter specification to the current specification.
     *
     * @param specification the custom filter specification to add
     * @return the current QueryTextManager instance
     * @apiNote if the provided specification is null, it will be ignored
     */
    @Override
    public QueryTextManager<T> custom(FilterSpecification<T> specification) {
        if (specification == null) return this;
        this.specification = this.specification.and(specification);
        return this;
    }

    /**
     * Applies a filter based on the provided filter object and attribute.
     *
     * @param filter    the filter object containing the value and operation
     * @param attribute the attribute to filter on
     * @return the current {@link QueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the filter, attribute, operation, or value is null, the method will have no effect
     */
    @Override
    public QueryTextManager<T> filter(Filter<String, TextOperation> filter, SingularAttribute<T, String> attribute) {
        if (filter == null || attribute == null || filter.value() == null || filter.operation() == null) {
            return this;
        }
        return filter(attribute, filter.value(), filter.operation());
    }

    /**
     * Applies a filter based on the provided value, operation, and attribute.
     *
     * @param attribute the attribute to filter on
     * @param value     the value to filter by
     * @param operation the text operation to apply
     * @return the current {@link QueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the attribute, value, or operation is null, the method will have no effect
     */
    @Override
    public QueryTextManager<T> filter(SingularAttribute<T, String> attribute, String value, TextOperation operation) {
        if (attribute == null || value == null || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularBuilder<T, String> builder = CriteriaSingularBuilder.builder();
        CriteriaSingularStringBuilder<T> stringBuilder = CriteriaSingularStringBuilder.create();
        FilterSpecification<T> spec = switch (operation) {
            case EQ -> builder.equalsOp(attribute, value);
            case NEQ -> builder.notEqualsOp(attribute, value);
            case CONTAINS -> stringBuilder.containsOp(attribute, value);
            case NOT_CONTAINS -> stringBuilder.notContainsOp(attribute, value);
            case STARTS_WITH -> stringBuilder.startsWithOp(attribute, value);
            case ENDS_WITH -> stringBuilder.endsWithOp(attribute, value);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    /**
     * Applies a collection-based filter based on the provided filter object and attribute.
     *
     * @param filter    the collection filter object containing the values and operation
     * @param attribute the attribute to filter on
     * @return the current {@link QueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the filter, attribute, operation, or values are null or empty, the method will have no effect
     */
    @Override
    public QueryTextManager<T> filterIn(CollectionFilter<String, TextCollectionOperation> filter, SingularAttribute<T, String> attribute) {
        if (filter == null || attribute == null || filter.values() == null || filter.operation() == null || filter.values().isEmpty()) {
            return this;
        }
        return filterIn(attribute, filter.values(), filter.operation());
    }

    /**
     * Applies a collection-based filter based on the provided attribute, values, and operation.
     *
     * @param attribute the attribute to filter on
     * @param values    the collection of values to compare against
     * @param operation the text collection operation to apply
     * @return the current {@link QueryTextManager} instance
     * @throws FilterDisabledException if the operation is disabled in the registry
     * @apiNote if the attribute, values, or operation is null, or if the values collection is empty, the method will have no effect
     */
    @Override
    public QueryTextManager<T> filterIn(SingularAttribute<T, String> attribute, Collection<String> values, TextCollectionOperation operation) {
        if (attribute == null || values == null || values.isEmpty() || operation == null) {
            return this;
        }
        checkAvailability(operation);
        CriteriaSingularStringBuilder<T> builder = CriteriaSingularStringBuilder.create();
        CriteriaSingularBuilder<T, String> singular = CriteriaSingularBuilder.builder();
        FilterSpecification<T> spec = switch (operation) {
            case IN -> singular.inOp(attribute, values);
            case NOT_IN -> singular.notInOp(attribute, values);
            case CONTAINS_ANY -> builder.containsOp(attribute, values);
            case NOT_CONTAINS -> builder.notContainsOp(attribute, values);
            case CONTAINS_ALL -> builder.containsAllOp(attribute, values);
            case STARTS_WITH -> builder.startsWithOp(attribute, values);
            case ENDS_WITH -> builder.endsWithOp(attribute, values);
        };
        this.specification = this.specification.and(spec);
        return this;
    }

    @Override
    public FilterableApi<T> let() {
        return FilterableApi.initialSpec(specification);
    }

    private void checkAvailability(FilterOperation operation) {
        if (operationRegistry.isOperationDisabled(operation)) {
            throw new FilterDisabledException(operation);
        }
    }

    private void checkAvailability(TextOperation operation) {
        checkAvailability(FilterOperation.fromTextOperation(operation));
    }

    private void checkAvailability(TextCollectionOperation operation) {
        checkAvailability(FilterOperation.fromTextCollectionOperation(operation));
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
